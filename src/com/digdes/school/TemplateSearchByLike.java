package com.digdes.school;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateSearchByLike {


    public static boolean like(String requestValue, String template) {


        // проверяем шаблон и выполняем соответствующую операцию
        if (template.equals(requestValue)) {
            return true;
        } else if (template.contains("%")) {
            // %строка%
            String regex = template.replace("%", ".*");
            return requestValue.matches(regex);
        } else if (template.startsWith(requestValue) && requestValue.length() < template.length()) {
            // строка%
            String regex = "^" + requestValue + ".*";
            return requestValue.matches(regex);
        } else if (template.endsWith(requestValue) && requestValue.length() < template.length()) {
            // %строка
            String regex = ".*" + requestValue + "$";
            return requestValue.matches(regex);
        } else if (template.isEmpty()) {
            // пустой шаблон
            return true;
        }
        return false;

    }

    public static boolean ilike(String requestValue, String template) {
        requestValue = requestValue.toLowerCase();
        template = template.toLowerCase();

        return like(requestValue, template);
    }





}
