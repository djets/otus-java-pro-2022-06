package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl implements EntityClassMetaData{
    private final Class<?> entityClass;
    public EntityClassMetaDataImpl(Class<?> entityClass){
        this.entityClass = entityClass;
    }
    @Override
    public String getName() {
        return entityClass.getName();
    }

    @Override
    public Constructor getConstructor() {
        try {
            Constructor<?> constructorClass = entityClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(entityClass.getDeclaredFields()).filter(f-> f.isAnnotationPresent(idField.class)).toList().get(0);
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(entityClass.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(entityClass.getDeclaredFields()).filter(f-> !f.isAnnotationPresent(idField.class)).toList();
    }
}
