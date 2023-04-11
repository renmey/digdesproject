package com.digdes.school;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SwitchCaseCommand {


    private String updatedKey;
    private String updatedValue;


    public SwitchCaseCommand(String updatedKey, String updatedValue) {

        this.updatedKey = updatedKey;
        this.updatedValue = updatedValue;
    }


    public boolean switchCase(String comparisonOperator, List<Map<String, Object>> list, Map<String, Object> map) {
        boolean b = false;


        switch (comparisonOperator) {
            case "=":

                if (Objects.nonNull(map.get(updatedKey)) && map.get(updatedKey).equals(MyUtil.convertToCorrectType(updatedValue, map.get(updatedKey)))) {
                    b = true;
                }

                break;

            case "!=":

                if (Objects.nonNull(map.get(updatedKey)) && !map.get(updatedKey).equals(MyUtil.convertToCorrectType(updatedValue, map.get(updatedKey)))) {
                    b = true;

                }
                break;
            case ">=":

                if (Objects.nonNull(map.get(updatedKey)) && Double.parseDouble(map.get(updatedKey).toString()) >= Double.parseDouble(updatedValue.toString())) {

                    b = true;

                }
                break;
            case "<=":

                if (Objects.nonNull(map.get(updatedKey)) && Double.parseDouble(map.get(updatedKey).toString()) <= Double.parseDouble(updatedValue.toString())) {
                    b = true;
                }


                break;

            case "<":

                if (Objects.nonNull(map.get(updatedKey)) && Double.parseDouble(map.get(updatedKey).toString()) < Double.parseDouble(updatedValue.toString())) {
                    b = true;


                }
                break;

            case ">":

                if (Objects.nonNull(map.get(updatedKey)) && Double.parseDouble(map.get(updatedKey).toString()) > Double.parseDouble(updatedValue.toString())) {
                    b = true;

                }
                break;

            case "like":

                if (Objects.nonNull(map.get(updatedKey)) &&
                        TemplateSearchByLike.like(map.get(updatedKey).toString(), updatedValue)) {
                    b = true;


                }
                break;

            case "ilike":

                if (Objects.nonNull(map.get(updatedKey)) &&
                        TemplateSearchByLike.ilike(map.get(updatedKey).toString(), updatedValue)) {
                    b = true;


                }
                break;

        }

        return b;
    }
}



