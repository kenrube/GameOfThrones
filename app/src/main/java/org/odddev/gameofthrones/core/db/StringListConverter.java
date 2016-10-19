package org.odddev.gameofthrones.core.db;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.requery.Converter;
import io.requery.Nullable;

/**
 * @author Jakub Szymion
 * @since 01.06.2016
 */

public class StringListConverter implements Converter<List<String>, String> {

    private static final String SEPARATOR = "\0007";

    @SuppressWarnings("unchecked")
    @Override
    public Class<List<String>> getMappedType() {
        return (Class) List.class;
    }

    @Override
    public Class<String> getPersistedType() {
        return String.class;
    }

    @Nullable
    @Override
    public Integer getPersistedSize() {
        return null;
    }

    @Override
    public String convertToPersisted(List<String> value) {
        if (value == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int size = value.size();
        int index = 0;
        for (String s : value) {
            ++index;
            sb.append(s);
            if (index < size) {
                sb.append(SEPARATOR);
            }
        }

        return sb.toString();
    }

    @Override
    public List<String> convertToMapped(Class<? extends List<String>> type, String value) {
        if (value == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(value.split(SEPARATOR));
    }
}