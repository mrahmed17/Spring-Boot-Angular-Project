//package com.mrahmed.HRandPayrollManagementSystem.restcontroller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/salaries")
//@CrossOrigin("*")
//public class SalaryRestController {
//
//
//    @Autowired
//    public SalaryRepository salaryRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//    @Autowired
//    private SalaryService salaryService;
//
//
//    @PostMapping("/salary_save")
//    public ResponseEntity<Salary> saveEmpSalary(@RequestBody Salary salary) {
//        String employeeName = salary.getEmployee().getName();
//        Employee employee = employeeRepository.findByName(employeeName).get();
//        salary.setEmployee(employee);
//        salaryRepository.save(salary);
//        return ResponseEntity.ok(salary);
//
//    }
//
//    @GetMapping("")
//    public ResponseEntity<List<Salary>> getAllSalaries() {
//        List<Salary> salaries = salaryRepository.findAll();
//        return ResponseEntity.ok(salaries);
//    }
//
//
//    // Update method
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateSalary(@PathVariable Long id, @RequestBody Salary updatedSalary) {
//        Optional<Salary> optionalSalary = salaryRepository.findById(id);
//        if (optionalSalary.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Salary existingSalary = optionalSalary.get();
//        // Update the existing salary with the new data
//        existingSalary.setEmployee(updatedSalary.getEmployee());
//        existingSalary.setAmount(updatedSalary.getAmount());
//        existingSalary.setDate(updatedSalary.getDate());
////        existingSalary.setTotalAmount(updatedSalary.getTotalAmount());
//        // Update other fields as needed
//
//        // Save the updated salary
//        salaryRepository.save(existingSalary);
//
//        return ResponseEntity.ok("Salary updated successfully");
//    }
//
//    // Delete method
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteSalary(@PathVariable Long id) {
//        Optional<Salary> optionalSalary = salaryRepository.findById(id);
//        if (optionalSalary.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Delete the salary
//        salaryRepository.delete(optionalSalary.get());
//
//        return ResponseEntity.ok("Salary deleted successfully");
//    }
//
//    @GetMapping("/{name}")
//    public ResponseEntity<Employee> findByEmployeeName(@PathVariable String name) {
//        Employee employee = employeeRepository.findByName(name).get();
//        return ResponseEntity.ok(employee);
//    }
//
//
//
//}
