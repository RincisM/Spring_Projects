package com.training.parser.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.training.parser.Entity.Employee;

// This class contains the logic for reading from and wrting to the JSON file
// This file is created to separate the business logic from the controller.

@Service
public class EmployeeService {
    private final String FILE_PATH = "Parser/src/main/resources/employees.json";
    private JSONParser jsonParser = new JSONParser();

    public List<Employee> readEmployees() throws IOException, ParseException {
        
        // Reading the JSON data from employees.json file
        FileReader reader = new FileReader(FILE_PATH);

        // Parsing and Converting the Objects to JSONArray for Iteration
        JSONArray employeesArray = (JSONArray) jsonParser.parse(reader);
        reader.close();

        List<Employee> employees = new ArrayList<>();

        // Looping through each Employee Object in JSONArray
        for(Object obj: employeesArray) {

            // Converting each object to JSON Object
            JSONObject employeeJson = (JSONObject) obj;

            // Creating a new Employee Object
            Employee employee = new Employee();
            employee.setId((String) employeeJson.get("id"));
            employee.setName((String) employeeJson.get("name"));
            employee.setDepartment((String) employeeJson.get("department"));
            employee.setSalary((Double) employeeJson.get("salary"));
            employee.setExperience((String) employeeJson.get("experience"));

            // Parsing the startTime and endTime fields to LocalTime
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            employee.setWorkingHoursStart(LocalTime.parse((String) employeeJson.get("workingHoursStart"), timeFormatter));
            employee.setWorkingHoursEnd(LocalTime.parse((String) employeeJson.get("workingHoursEnd"), timeFormatter));

            // Adding Employee Object to the List of Employees
            employees.add(employee);
        } 
        return employees;
    }

    @SuppressWarnings("unchecked")
    public void writeEmployees(List<Employee> employees) throws IOException {

        // Creating a JSONArray to store the Employee Data
        JSONArray employeesArray = new JSONArray();

        // Looping through each Employee Object from the List of Employees
        for(Employee employee : employees) {

            // Creating a new JSONObject for each employee
            JSONObject employeeJson = new JSONObject();
            employeeJson.put("id", employee.getId());
            employeeJson.put("name", employee.getName());
            employeeJson.put("department", employee.getDepartment());
            employeeJson.put("salary", employee.getSalary());
            employeeJson.put("experience", employee.getExperience());

            // Converting the LocalTime to String before adding it to JSON
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            employeeJson.put("workingHoursStart", employee.getWorkingHoursStart().format(timeFormatter));
            employeeJson.put("workingHoursEnd", employee.getWorkingHoursEnd().format(timeFormatter));

            // Adding Employee JSONObject to JSONArray
            employeesArray.add(employeeJson);
        }

        // Writin the JSONArray to the File
        FileWriter writer = new FileWriter(FILE_PATH);
        writer.write(employeesArray.toJSONString());
        writer.close();
    }

    // Service to get employees of the given id
    public Employee getEmployee(String id) throws IOException, ParseException {
        
        // Read all the employees and store it as a List
        List<Employee> employees = readEmployees();

        // Loop through employee list
        for (Employee emp : employees) {

            // Check if an employee id matches the given id
            if(emp.getId().equals(id)) {
                return emp;
            }
        }
        throw new IllegalArgumentException("Employee with ID " + id + "not found");
    }

    // Service to get Employees by department
    public List<Employee> getEmployeesByDepartment(String department) throws IOException, ParseException {

        // Read all the employees and store it as a List
        List<Employee> employees = readEmployees();
        List<Employee> resultingEmployees = new ArrayList<>();

        // Converting the department to lower case to make the matching case sensitive
        String depart = department.toLowerCase();

        // Loop through employee list
        for(Employee emp: employees) {

            // Check if an employee department matches the given department
            if(emp.getDepartment().toLowerCase().equals(depart)) {
                resultingEmployees.add(emp);
            }
        }

        if(!resultingEmployees.isEmpty()) {
            return resultingEmployees;
        } else {
            throw new IllegalArgumentException("Employee with Department " + department + " not found");
        }
    }

    // Service to Add new Employee to the Employee JSON
    public void addEmployee(Employee employee) throws IOException, ParseException {

        // Read all the employees and store it as a List
        List<Employee> employees = readEmployees();

        // Loop through employee list
        for(Employee emp : employees) {

            // Check if an employee id matches the given id
            if(emp.getId().equals(employee.getId())) {

                // Throw exception if id's matched
                throw new IllegalArgumentException("Employee with ID " + employee.getId() + " already exists");
            }
        }
        
        // If id's do not match, add the employee to the list
        employees.add(employee);

        // Write the employees in the file
        writeEmployees(employees);
    }

    // Service to update employee
    public void updateEmployee(Employee employee) throws IOException, ParseException {

        // Read all the employees and store it as a List
        List<Employee> employees = readEmployees();
        boolean found = false;

        // Loop through employee list
        for(int i= 0; i < employees.size(); i++) {

            // Check if an employee id matches the given id
            if(employees.get(i).getId().equals(employee.getId())) {

                // Update the employee
                employees.set(i, employee);

                // Set the boolean to true;
                found = true;
                break;
            }
        }

        // If an employee is not found throw exception
        if(!found) {
            throw new IllegalArgumentException("Employee with ID " + employee.getId() + " not found");
        }

        // If an employee is found, write in the file
        writeEmployees(employees);
    }


    // Service to delete and Employee
    public void deleteEmployee(String id) throws IOException, ParseException {

        // Read all the employees and store it as a List
        List<Employee> employees = readEmployees();
        boolean found = false;

        // Loop through employee list
        for(Employee emp: employees) {

            // Check if an employee id matches the given id
            if(emp.getId().equals(id)) {

                // If found, remove the employee from the list
                employees.remove(emp);

                // Change the boolean to true
                found = true;
                break;
            }
        }

        // If not found, throw an exception
        if(!found) {
            throw new IllegalArgumentException("Employee with ID " + id + " not found");
        }

        // Write the employee list to the file
        writeEmployees(employees);
    }
}
