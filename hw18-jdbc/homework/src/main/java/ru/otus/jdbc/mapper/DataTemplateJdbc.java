package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;
    private Object object;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    private Object[] getInitArguments(ResultSet rs) throws IllegalAccessException, SQLException {
        Object[] initArguments = new Object[entityClassMetaData.getConstructor().getParameters().length];
        for (int i = 0; i < entityClassMetaData.getConstructor().getParameters().length; i++) {
            var prt = entityClassMetaData.getConstructor().getParameterTypes()[i];
            var fl = entityClassMetaData.getAllFields().get(i);
            if (prt == fl.getType() && prt == Long.class) {
                initArguments[i] = (rs.getLong(fl.getName()));
            }
            if (prt == fl.getType() && prt == String.class) {
                ResultSetMetaData metaData = rs.getMetaData();
                //Проверка на наличие column именем равным fl.getName
                for (int im = 1; im <= metaData.getColumnCount(); im++) {
                    if (fl.getName().equals(metaData.getColumnName(im))) {
                        initArguments[i] = (rs.getString(fl.getName()));
                    }
                }
            }
        }
        return initArguments;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    Object[] initArguments = getInitArguments(rs);
                    return entityClassMetaData.getConstructor().newInstance(initArguments);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, "select * from client", Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    Object[] initArguments = getInitArguments(rs);
                    clientList.add(entityClassMetaData.getConstructor().newInstance(initArguments));
                }
                return clientList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        this.object = object;
        try {
            List<Object> params = entityClassMetaData.getFieldsWithoutId().stream().map(field -> {
                try {
                    return field.get(this.object);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    //хардкод id
                    List.of(entityClassMetaData.getFieldsWithoutId().get(0).get(object), entityClassMetaData.getIdField().get(object)));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
