//package com.mrahmed.HrAndPayrollManagement.restcontroller;
//
//
//import com.mrahmed.HrAndPayrollManagement.entity.Branch;
//import com.mrahmed.HrAndPayrollManagement.repository.BranchRepository;
//import com.mrahmed.HrAndPayrollManagement.service.BranchService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/branches") // for both GET and POST requests
//// on /branches endpoint. If POST is used, a new branch will be
//// created. If GET is used, all branches will be returned.
//// For PUT, PATCH, DELETE, and other HTTP methods, you would
//// need to use @RequestMapping with the respective method name.
//@CrossOrigin("*")
//public class BranchController {
//
//    @Autowired
//    private BranchService branchService;
//
//    @Autowired
//    private BranchRepository branchRepository;
//
//
//    @GetMapping("/")
//    public ResponseEntity<List<Branch>> getAllBranches() {
//        List<Branch> branches = branchRepository.findAll();
//        return ResponseEntity.ok(branches);
//    }
//
//    private ResponseEntity<Void> createBranches(List<Branch> branches) {
//        for (Branch branch : branches) {
//            branchRepository.save(branch);
//        }
//        return ResponseEntity.noContent().build();
//    }
//
//
//}
