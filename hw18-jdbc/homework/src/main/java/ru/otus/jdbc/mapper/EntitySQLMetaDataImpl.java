package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        Field fieldName = (Field) entityClassMetaData.getFieldsWithoutId().get(0);
        var id = entityClassMetaData.getIdField().getName();
        var name = fieldName.getName();
        return "SELECT " + id + ", " + name + " FROM " + entityClassMetaData.getName() + " WHERE " + id + " = ?";
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getInsertSql() {
        List<Field> fieldsWithoutId = (List<Field>) entityClassMetaData.getFieldsWithoutId();
        if (fieldsWithoutId.size() > 1) {
            var fieldsNameString = fieldsWithoutId.stream().map(Field::getName)
//                    .map(s -> "(" + s + ") values (?)")

                    .collect(Collectors.joining(", "));
            var jocker = fieldsWithoutId.stream().map(field -> "?").collect(Collectors.joining(", "));
            return "insert into " + entityClassMetaData.getName() + "(" + fieldsNameString + ") values (" + jocker + ")";
        }
        return "INSERT INTO " + entityClassMetaData.getName() + "(name) VALUES (?)";
    }

    @Override
    public String getUpdateSql() {
        Field fieldName = (Field) entityClassMetaData.getFieldsWithoutId().get(0);
        var id = entityClassMetaData.getIdField().getName();
        var name = fieldName.getName();
        return "UPDATE " + entityClassMetaData.getName() + " set " + name + " = ? where " + id + " = ?";
    }
}
