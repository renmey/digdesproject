package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateCommand {

    private static void updateMap(Map<String, Object> map, Long id, String lastName, Long age, Double cost, Boolean active) {
        if (id != null) map.put("id", id);
        if (lastName != null) map.put("lastName", lastName);
        if (age != null) map.put("age", age);
        if (cost != null) map.put("cost", cost);
        if (active != null) map.put("active", active);
    }


    public static List<Map<String, Object>> update(String request, List<Map<String, Object>> list)throws IllegalArgumentException {


            Long id = null;
        String lastName = null;
        Long age = null;
        Double cost = null;
        Boolean active = null;

        String updatedKey = null;
        String updatedValue = null;
        String comparisonOperator = null;

        //Коллекция, в которой будут храниться обновленные колонки для вывода в консоль
        List<Map<String, Object>> mapList = new ArrayList<>();

        MyUtil searchPattern = new MyUtil(request);

        id = searchPattern.getId();
        lastName = searchPattern.getLastName();
        age = searchPattern.getAge();
        cost = searchPattern.getCost();
        active = searchPattern.getActive();
        Iterator<Map<String, Object>> iterator = list.iterator();

        //Если нет условия Where, обновляем все колонки
        if (!request.contains("where")) {
            for (Map<String, Object> map : list) {
                updateMap(map, id, lastName, age, cost, active);

                mapList = new ArrayList<>(list);
            }

        } else {


            // Разбиваем запрос на отдельные условия
            String[] partRequest = request.split("where", Pattern.CASE_INSENSITIVE);
            String[] orConditions = new String[0];

            if (partRequest.length > 1) {
                orConditions = partRequest[1].split("(\\s+or\\s+)", Pattern.CASE_INSENSITIVE);
            }


            // Обходим все мапы в списке
            while (iterator.hasNext()) {

                Map<String, Object> map = iterator.next();


                // Проверяем, удовлетворяет ли каждое условие этой мапе

                for (String orCondition : orConditions) {


                    String[] andConditions = orCondition.split("\\s+and\\s+", Pattern.CASE_INSENSITIVE);

                    int requiredAndConditions = andConditions.length;
                    int activatedAndConditionsInOr = 0;

                    for (String andCondition : andConditions) {


                        Pattern updatedValueAndKeyPattern = Pattern.compile("\\s*'(\\w+)'\\s*(=|!=|>=|<=|<|>|like|ilike)\\s*'?(.*)'?", Pattern.CASE_INSENSITIVE);

                        Matcher matcher6 = updatedValueAndKeyPattern.matcher(andCondition);
                        if (matcher6.find()) {
                            updatedKey = matcher6.group(1);
                            comparisonOperator = matcher6.group(2);
                            updatedValue = matcher6.group(3);
                        }


                        SwitchCaseCommand sw = new SwitchCaseCommand(updatedKey, updatedValue);


                        if (sw.switchCase(comparisonOperator, list, map))
                            activatedAndConditionsInOr++;


                    }

                    if (activatedAndConditionsInOr == requiredAndConditions) {

                        updateMap(map, id, lastName, age, cost, active);
                        mapList.add(map);
                        break;
                    }
                }
            }

        }

        return mapList;
    }

}
