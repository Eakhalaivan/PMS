package com.pharmadesk.backend.pharmacy.service;

import com.pharmadesk.backend.pharmacy.repository.MedicineReturnRepository;
import com.pharmadesk.backend.pharmacy.repository.MedicineStockRepository;
import com.pharmadesk.backend.pharmacy.repository.PharmacyBillRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final PharmacyBillRepository billRepository;
    private final MedicineReturnRepository returnRepository;
    private final MedicineStockRepository stockRepository;

    public DashboardService(PharmacyBillRepository billRepository, 
                            MedicineReturnRepository returnRepository, 
                            MedicineStockRepository stockRepository) {
        this.billRepository = billRepository;
        this.returnRepository = returnRepository;
        this.stockRepository = stockRepository;
    }

    public Map<String, Object> getDashboardStats() {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        Map<String, Object> stats = new HashMap<>();

        // Today Sales
        BigDecimal todaySales = billRepository.findAll().stream()
                .filter(b -> b.getBillingDate().isAfter(startOfDay) && b.getBillingDate().isBefore(endOfDay))
                .map(b -> b.getNetAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("todaySales", todaySales);

        // Today Returns
        BigDecimal todayReturns = returnRepository.findAll().stream()
                .filter(r -> r.getReturnDate().isAfter(startOfDay) && r.getReturnDate().isBefore(endOfDay))
                .map(r -> r.getTotalReturnAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("todayReturns", todayReturns);

        // Low Stock Count (threshold e.g., 50)
        long lowStockCount = stockRepository.findAll().stream()
                .filter(s -> s.getQuantityAvailable() < 50)
                .count();
        stats.put("lowStockCount", lowStockCount);

        // Additional stats like category breakdown or top selling can be added here
        // For production, these should be optimized JPQL or Native Queries

        return stats;
    }
}
