package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{
    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "select * from " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "select id, name from " + entityClassMetaData.getName() + " where id  = ?";
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getInsertSql() {
        List<Field> fieldList = (List<Field>) entityClassMetaData.getFieldsWithoutId();
        if (fieldList.size() > 1) {
            var fieldsNameString = fieldList.stream().map(Field::getName)
                    .map(s -> "(" + s + ") values (?)")
                    .collect(Collectors.joining(", "));
            return "insert into " + entityClassMetaData.getName() + fieldsNameString;
        }
        return "insert into " + entityClassMetaData.getName() + "(name) values (?)";
    }

    @Override
    public String getUpdateSql() {
        return "update " + entityClassMetaData.getName() + " set name = ? where id = ?";
    }
}
