package com.digdes.school;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateSearchByLike {


    public static boolean like(String requestValue, String template) {


        // ��������� ������ � ��������� ��������������� ��������
        if (template.equals(requestValue)) {
            return true;
        } else if (template.contains("%")) {
            // %������%
            String regex = template.replace("%", ".*");
            return requestValue.matches(regex);
        } else if (template.startsWith(requestValue) && requestValue.length() < template.length()) {
            // ������%
            String regex = "^" + requestValue + ".*";
            return requestValue.matches(regex);
        } else if (template.endsWith(requestValue) && requestValue.length() < template.length()) {
            // %������
            String regex = ".*" + requestValue + "$";
            return requestValue.matches(regex);
        } else if (template.isEmpty()) {
            // ������ ������
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
