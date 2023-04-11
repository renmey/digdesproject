package com.digdes.school;

import javax.management.MBeanRegistrationException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Класс с методами для поиска паттернов в запросе и для конвертации строк в подходящий тип для значения в мапе

public class MyUtil {
    public static final Set<String> ALLOWED_KEYS = new HashSet<>(Arrays.asList("id", "lastname", "age", "cost", "active"));
    private String request;

    public MyUtil(String request) {
        this.request = request;
    }

    public Long getId() {
        Long id = null;
        Pattern idPattern = Pattern.compile("'id'\\s*=\\s*(\\w+)");
        Matcher matcher1 = idPattern.matcher(request);

        if (matcher1.find()) {
            id = Long.parseLong(matcher1.group(1));
        }
        return id;
    }

    public String getLastName() {
        String lastName = null;
        Pattern lastNamePattern = Pattern.compile("'lastname'\\s*=\\s*'(\\w+)'", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = lastNamePattern.matcher(request);
        if (matcher2.find()) {
            lastName = matcher2.group(1);
        }
        return lastName;
    }

    public Long getAge() {
        Long age = null;

        Pattern agePattern = Pattern.compile("'age'\\s*=\\s*(\\d+)");
        Matcher matcher3 = agePattern.matcher(request);

        if (matcher3.find()) {
            age = Long.parseLong(matcher3.group(1));
        }
        return age;
    }

    public Double getCost() {
        Double cost = null;
        Pattern costPattern = Pattern.compile("'cost'\\s*=\\s*(\\w+\\.\\w+)");
        Matcher matcher4 = costPattern.matcher(request);
        if (matcher4.find()) {
            cost = Double.parseDouble(matcher4.group(1));
        }
        return cost;
    }

    public Boolean getActive() {
        Boolean active = null;

        Pattern activePattern = Pattern.compile("'active'\\s*=\\s*(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher5 = activePattern.matcher(request);
        if (matcher5.find()) {
            active = Boolean.parseBoolean(matcher5.group(1));
        }
        return active;


    }

    public static Object convertToCorrectType(String value, Object object) {

        if (object instanceof String) {
            return value;
        } else if (object instanceof Long) {
            return Long.parseLong(value);
        } else if (object instanceof Double) {
            return Double.parseDouble(value);
        } else if (object instanceof Boolean) {
            return Boolean.parseBoolean(value);
        } else {
            return value;
        }
    }

    public static boolean validKey(String request) {


        List<String> requestKeys = new ArrayList<>();


        List<String> comparisonOperators = new ArrayList<>();

        boolean isValidKey = false;
        boolean isValidOperator = false;

        Pattern validKeyAndStatement = Pattern.compile("\\s*'(\\w+)'\\s*(=|!=|>=|<=|<|>|like|ilike)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validKeyAndStatement.matcher(request.toLowerCase());

        while (matcher.find()) {
            requestKeys.add(matcher.group(1));
            comparisonOperators.add(matcher.group(2));

        }

        if (requestKeys.isEmpty()) {
            return true;
        }

        for (String key : requestKeys) {
            if (ALLOWED_KEYS.contains(key)) {
                isValidKey = true;
            }

        }

        for (int i = 0; i < requestKeys.size(); i++) {

            isValidOperator = true;

            switch (requestKeys.get(i)) {
                case "id":
                case "age":
                case "cost":
                    if (!comparisonOperators.get(i).matches("(=|!=|>=|<=|<|>)")) {
                        isValidOperator = false;
                    }
                    break;
                case "lastname":
                    if (!comparisonOperators.get(i).matches("(=|!=|like|ilike)")) {
                        isValidOperator = false;
                    }
                    break;

                case "active":
                    if (!comparisonOperators.get(i).equals("=")) {
                        isValidOperator = false;
                    }
                    break;
                default:
                    return false;

            }

        }


        return (isValidKey && isValidOperator);
    }


}
