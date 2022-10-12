package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private T t;

    public EntityClassMetaDataImpl(T t) {
        this.t = t;
    }

    @Override
    public String getName() {
        return t.getClass().getSimpleName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Constructor<T> getConstructor() {
        try {
            Constructor<T>[] allConstructor = (Constructor<T>[]) t.getClass().getDeclaredConstructors();
            for (Constructor<T> ctor : allConstructor) {
                Class<?>[] pType = ctor.getParameterTypes();
                if (pType.length > 1) {
                    return (Constructor<T>) ctor;
                }
            }
            return (Constructor<T>) t.getClass().getDeclaredConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(t.getClass().getDeclaredFields()).filter(f -> f.isAnnotationPresent(Id.class)).peek(f1 -> f1.setAccessible(true)).toList().get(0);
    }

    @Override
    public List<Field> getAllFields() {
        return (List<Field>) Arrays.stream(t.getClass().getDeclaredFields()).peek(f1 -> f1.setAccessible(true)).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fieldList = new ArrayList<>();
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Id.class)) {
                fieldList.add(field);
            }
        }
        return fieldList;
    }
}
