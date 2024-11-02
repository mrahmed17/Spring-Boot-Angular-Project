//package com.mrahmed.HRandPayrollManagementSystem.exceptionhandle;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.nio.file.attribute.UserPrincipalNotFoundException;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
//        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
//    }
//
//    @ExceptionHandler(UserPrincipalNotFoundException.class)
//    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserPrincipalNotFoundException ex) {
//        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
//    }
//
//    @ExceptionHandler(LeaveNotFoundException.class)
//    public ResponseEntity<Map<String, String>> handleLeaveNotFoundException(LeaveNotFoundException ex) {
//        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
//        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
//    }
//
//    private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String message) {
//        Map<String, String> response = new HashMap<>();
//        response.put("error", status.getReasonPhrase());
//        response.put("message", message);
//        return new ResponseEntity<>(response, status);
//    }
//
//    public class LeaveNotFoundException extends RuntimeException {
//        public LeaveNotFoundException(String message) {
//            super(message);
//        }
//
//    }
//
//
//
//}
