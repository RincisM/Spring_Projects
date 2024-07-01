package com.training.parser.Controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.parser.Entity.Employee;
import com.training.parser.Service.EmployeeService;


// This controller will map the HTTP requests to the service methods

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    // Get Method to retrieve all employees
    @GetMapping
    public List<Employee> getAllEmployees() throws IOException, ParseException {
        return employeeService.readEmployees();
    }

    // Get Method to retrieve employees with the given id
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") String id) throws IOException, ParseException {
        return employeeService.getEmployee(id);
    }

    // Get Method to retrieve employees with the given department
    @GetMapping("/department/{department}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String department) throws IOException, ParseException {
        return employeeService.getEmployeesByDepartment(department);
    }
    
    // Post Method to add new Employee
    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        try {
            employeeService.addEmployee(employee);
            return ResponseEntity.ok("Employee added Successfully");
        } catch (IOException | ParseException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Put Method to update and existing employee
    @PutMapping
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) {
        try {
            employeeService.updateEmployee(employee);
            return ResponseEntity.ok("Employee updated Successfully");
        } catch (IOException | ParseException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete Method to delete an employee with a given id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted Successfully");
        } catch (IOException | ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
