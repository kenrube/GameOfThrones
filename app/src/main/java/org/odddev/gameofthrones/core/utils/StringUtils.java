package org.odddev.gameofthrones.core.utils;

import java.util.List;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class StringUtils {

    public static String concat(List<String> strings) {
        String result = "";
        for (int i = 0; i < strings.size(); i++) {
            result += strings.get(i) + "\n";
        }
        return result;
    }
}
