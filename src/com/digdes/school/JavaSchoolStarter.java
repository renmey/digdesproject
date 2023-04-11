package com.digdes.school;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaSchoolStarter {

    List<Map<String, Object>> list = new ArrayList<>();

    public JavaSchoolStarter() {
    }


    public List<Map<String, Object>> execute(String request) throws Exception {

        if (!MyUtil.validKey(request)) {

            throw new IllegalArgumentException("Invalid request");
        }

        List<Map<String, Object>> list1 = new ArrayList<>();


        if (request.toLowerCase().contains("insert")) {
            Map<String, Object> map = InsertCommand.insert(request);

            list.add(map);

            list1.add(map);


        } else if (request.toLowerCase().contains("update")) {
            list1 = UpdateCommand.update(request, list);


        } else if (request.toLowerCase().contains("delete")) {
            list1 = DeleteCommand.delete(request, list);


        } else if (request.toLowerCase().contains("select")) {
            list1 = SelectCommand.select(request, list);

        }

        return list1;

    }
}

