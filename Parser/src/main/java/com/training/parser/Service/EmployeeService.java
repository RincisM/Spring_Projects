package com.training.parser.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.training.parser.Entity.Employee;

// This class contains the logic for reading from and wrting to the JSON file
// This file is created to separate the business logic from the controller.

public class EmployeeService {
    private final String FILE_PATH = "src/main/resources/employees.json";
    private JSONParser jsonParser = new JSONParser();

    public List<Employee> readEmployees() throws IOException, ParseException {
        FileReader reader = new FileReader(FILE_PATH);
        JSONArray employeesArray = (JSONArray) jsonParser.parse(reader);
        reader.close();

        List<Employee> employees = new ArrayList<>();
        for(Object obj: employeesArray) {
            JSONObject employeeJson = (JSONObject) obj;
            Employee employee = new Employee();
            employee.setId((String) employeeJson.get("id"));
            employee.setName((String) employeeJson.get("name"));
            employee.setDepartment((String) employeeJson.get("department"));
            employee.setSalary((Double) employeeJson.get("salary"));
            employees.add(employee);
        } 
        return employees;
    }

    public void writeEmployees(List<Employee> employees) throws IOException {
        JSONArray employeesArray = new JSONArray();
        for(Employee employee : employees) {
            JSONObject employeeJson = new JSONObject();
            employeeJson.put("id", employee.getId());
            employeeJson.put("name", employee.getName());
            employeeJson.put("department", employee.getDepartment());
            employeeJson.put("salary", employee.getSalary());
            employeesArray.add(employeeJson);
        }
    }
}
