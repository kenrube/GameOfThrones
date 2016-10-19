package org.odddev.gameofthrones.core.db;

import java.util.ArrayList;
import java.util.List;

import io.requery.Converter;

/**
 * @author kenrube
 * @date 18.10.16
 */

public class IntegerListConverter implements Converter<List<Integer>, String> {

    @SuppressWarnings("unchecked")
    @Override
    public Class<List<Integer>> getMappedType() {
        return (Class) List.class;
    }

    @Override
    public Class<String> getPersistedType() {
        return String.class;
    }

    @Override
    public Integer getPersistedSize() {
        return null;
    }

    @Override
    public String convertToPersisted(List<Integer> value) {
        if (value == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Object integer : value) {
            if (index > 0) {
                sb.append(",");
            }
            sb.append(integer);
            index++;
        }
        return sb.toString();
    }

    @Override
    public List<Integer> convertToMapped(Class<? extends List<Integer>> type,
                                              String value) {
        List<Integer> list = new ArrayList<>();
        if (value != null) {
            String[] parts = value.split(",");
            for (String part : parts) {
                if (part.length() > 0) {
                    list.add(Integer.parseInt(part.trim()));
                }
            }
        }
        return list;
    }
}