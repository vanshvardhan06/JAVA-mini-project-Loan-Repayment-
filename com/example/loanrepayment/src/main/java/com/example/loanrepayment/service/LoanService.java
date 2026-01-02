package com.example.loanrepayment.service;

import org.springframework.stereotype.Service;

@Service
public class LoanService {

    // EMI calculation formula: ((amount * rate * years) / 100 + amount) / (years * 12)
    public double calculateEMI(int loanType, double amount, double weight, int duration) {
        double rate = 0.0;
        double principal = amount;

        switch (loanType) {
            case 1: // Home Loan
                rate = 7.5;
                break;
            case 2: // Auto Loan
                rate = 10.25;
                break;
            case 3: // Personal Loan
                rate = 15.25;
                break;
            case 4: // Gold Loan
                // Calculate max loan: (weight * 11700 * 75) / 100
                double maxLoan = (weight * 11700 * 75) / 100;
                if (amount > maxLoan) {
                    throw new IllegalArgumentException("Requested amount exceeds the limit. Max loan: " + maxLoan);
                }
                rate = 10.25;
                break;
            default:
                throw new IllegalArgumentException("Invalid loan type");
        }

        if (duration <= 0) {
            throw new IllegalArgumentException("Invalid duration");
        }

        double emi = ((principal * rate * duration) / 100 + principal) / (duration * 12);
        return emi;
    }
}