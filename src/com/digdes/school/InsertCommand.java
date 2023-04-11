package com.digdes.school;

import java.util.*;

public class InsertCommand {

    public static Map<String, Object> insert(String request) {



        Long id = null;
        String lastName = null;
        Long age = null;
        Double cost = null;
        Boolean active = null;

        Map<String, Object> row = new HashMap<>();
        row.put("id", null);
        row.put("lastName", null);
        row.put("age", null);
        row.put("cost", null);
        row.put("active", null);

        MyUtil searchPattern = new MyUtil(request);

        // ѕолучаем все значени€ из строки
        id = searchPattern.getId();
        lastName = searchPattern.getLastName();
        age = searchPattern.getAge();
        cost = searchPattern.getCost();
        active = searchPattern.getActive();

        try {


            row.put("id", id);
            row.put("lastName", lastName);
            row.put("age", age);
            row.put("cost", cost);
            row.put("active", active);

            //’от€ бы одно значение не должно быть пустым

            if (row.get("id") == null && row.get("lastName") == null && row.get("age") == null
              && row.get("cost") == null && row.get("active") == null) {
                throw new NullPointerException();
        }

        }catch (NullPointerException e){
            System.out.println("¬се значени€ в строке не могут быть пустыми");
            return null;

        }



            return row;
    }


}
