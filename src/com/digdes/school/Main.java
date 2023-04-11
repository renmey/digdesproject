package com.digdes.school;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String... args){
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
            //������� ������ � ���������
            List<Map<String,Object>> result1 = starter.execute("inseRt VALUES 'LastName' = '�������' , 'id'=3, 'age'=40, 'active'=true");
            System.out.println(result1);
            //��������� �������� ������� ���� ����������
            List<Map<String,Object>> result2 = starter.execute("UPDATE VALUES 'ACTIVE'=false, 'cost'=10.1 where 'id'=3");
            System.out.println(result2);
            //��������� ���� ������ �� ��������� (�.�. � ������ ������� �������� 1 ������)
            List<Map<String,Object>> result3 = starter.execute("select");
            System.out.println(result3);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
