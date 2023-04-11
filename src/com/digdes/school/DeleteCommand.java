package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommand {


    public static List<Map<String, Object>> delete(String request, List<Map<String, Object>> list) {


        String keyToDelete = null;
        String valueToDelete = null;
        String comparisonOperator = null;

        //Коллекция, в которой будут храниться все удаленные записи для вывода в консоль
        List<Map<String, Object>> deletedMaps = new ArrayList<>();

        //Если в запросе команда DELETE без условия WHERE, то удаляем все записи
        if (request.trim().equalsIgnoreCase("delete")) {
            deletedMaps = new ArrayList<>(list);
            list.removeAll(list);
        } else {

            Iterator<Map<String, Object>> iterator = list.iterator();

            // Разбиваем запрос на отдельные условия
            String[] orConditions = request.split("\\s+or\\s+", Pattern.CASE_INSENSITIVE);



            // Обходим все мапы в списке
            while (iterator.hasNext()) {

                Map<String, Object> map = iterator.next();


                // Проверяем, удовлетворяет ли каждое условие этой мапе

                for (String orCondition : orConditions) {


                    String[] andConditions = orCondition.split("\\s+and\\s+", Pattern.CASE_INSENSITIVE);

                    int requiredAndConditions = andConditions.length;
                    int activatedAndConditionsInOr = 0;

                    for (String andCondition : andConditions) {


                        Pattern valueAndKeyToDeletePattern = Pattern.compile("\\s*'(\\w+)'\\s*(=|!=|>=|<=|<|>|like|ilike)\\s*(.*)", Pattern.UNICODE_CHARACTER_CLASS);
                        Matcher matcher = valueAndKeyToDeletePattern.matcher(andCondition);

                        if (matcher.find()) {
                            keyToDelete = matcher.group(1);
                            comparisonOperator = matcher.group(2);
                            valueToDelete = matcher.group(3);

                        }


                        SwitchCaseCommand sw = new SwitchCaseCommand(keyToDelete, valueToDelete);


                        if (sw.switchCase(comparisonOperator, list, map))
                            activatedAndConditionsInOr++;


                    }

                    if (activatedAndConditionsInOr == requiredAndConditions) {


                        deletedMaps.add(map);
                        iterator.remove();
                        break;
                    }
                }
            }
        }
        return deletedMaps;
    }
}