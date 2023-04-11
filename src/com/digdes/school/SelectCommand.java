package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectCommand {

    public static List<Map<String, Object>> select(String request, List<Map<String, Object>> list) {


        String keyToSelect = null;
        String valueToSelect = null;
        String comparisonOperator = null;

        //Коллекция, в которой будут храниться все найденные записи для вывода в консоль
        List<Map<String, Object>> selectedMaps = new ArrayList<>();

        //Если в запросе команда SELECT без условия WHERE, то выбираем все записи
        if (request.trim().equalsIgnoreCase("select")) {
            selectedMaps = new ArrayList<>(list);

        } else {


            // Разбиваем запрос на отдельные условия
            String[] orConditions = request.split("\\s+or\\s+", Pattern.CASE_INSENSITIVE);



            // Обходим все мапы в списке
            for (Map<String, Object> map : list) {


                // Проверяем, удовлетворяет ли каждое условие этой мапе

                for (String orCondition : orConditions) {


                    String[] andConditions = orCondition.split("\\s+and\\s+", Pattern.CASE_INSENSITIVE);

                    int requiredAndConditions = andConditions.length;
                    int activatedAndConditionsInOr = 0;

                    for (String andCondition : andConditions) {


                        Pattern valueAndKeyToDeletePattern = Pattern.compile("\\s*'(\\w+)'\\s*(=|!=|>=|<=|<|>|like|ilike)\\s*(.*)", Pattern.UNICODE_CHARACTER_CLASS);
                        Matcher matcher = valueAndKeyToDeletePattern.matcher(andCondition);

                        if (matcher.find()) {
                            keyToSelect = matcher.group(1);
                            comparisonOperator = matcher.group(2);
                            valueToSelect = matcher.group(3);

                        }


                        SwitchCaseCommand sw = new SwitchCaseCommand(keyToSelect, valueToSelect);


                        if (sw.switchCase(comparisonOperator, list, map))
                            activatedAndConditionsInOr++;


                    }
                    if (activatedAndConditionsInOr == requiredAndConditions) {


                        selectedMaps.add(map);

                        break;
                    }
                }
            }
        }

        return selectedMaps;
    }

}
