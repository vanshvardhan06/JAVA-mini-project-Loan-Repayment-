package com.example.loanrepayment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loanrepayment.service.LoanService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Allow frontend calls (adjust for production)
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/calculateEMI")
    public ResponseEntity<Map<String, Object>> calculateEMI(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int loanType = Integer.parseInt(request.get("loanType").toString());
            double amount = Double.parseDouble(request.get("amount").toString());
            double weight = Double.parseDouble(request.getOrDefault("weight", "0").toString());
            int duration = Integer.parseInt(request.get("duration").toString());

            double emi = loanService.calculateEMI(loanType, amount, weight, duration);
            response.put("success", true);
            response.put("emi", String.format("%.2f", emi));
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}