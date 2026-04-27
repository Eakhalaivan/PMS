package com.pharmadesk.backend.pharmacy.controller;

import com.pharmadesk.backend.pharmacy.dto.ApiResponse;
import com.pharmadesk.backend.model.MedicineReturn;
import com.pharmadesk.backend.model.MedicineStock;
import com.pharmadesk.backend.model.PharmacyBill;
import com.pharmadesk.backend.pharmacy.repository.MedicineReturnRepository;
import com.pharmadesk.backend.pharmacy.repository.MedicineStockRepository;
import com.pharmadesk.backend.pharmacy.repository.PharmacyBillRepository;
import com.pharmadesk.backend.pharmacy.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pharmacy/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final PharmacyBillRepository pharmacyBillRepository;
    private final MedicineReturnRepository medicineReturnRepository;
    private final MedicineStockRepository medicineStockRepository;

    public DashboardController(DashboardService dashboardService, 
                               PharmacyBillRepository pharmacyBillRepository, 
                               MedicineReturnRepository medicineReturnRepository, 
                               MedicineStockRepository medicineStockRepository) {
        this.dashboardService = dashboardService;
        this.pharmacyBillRepository = pharmacyBillRepository;
        this.medicineReturnRepository = medicineReturnRepository;
        this.medicineStockRepository = medicineStockRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        LocalDateTime today = LocalDate.now().atStartOfDay();
        
        BigDecimal todaySales = pharmacyBillRepository.findByBillingDateAfter(today)
                .stream().map(PharmacyBill::getNetAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal todayReturns = medicineReturnRepository.findByReturnDateAfter(today)
                .stream().map(MedicineReturn::getTotalReturnAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSales = pharmacyBillRepository.findAll().stream()
                .map(PharmacyBill::getNetAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalReturns = medicineReturnRepository.findAll().stream()
                .map(MedicineReturn::getTotalReturnAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        List<MedicineStock> lowStockMedicines = medicineStockRepository.findAll().stream()
                .filter(s -> s.getQuantityAvailable() < 20).toList();

        BigDecimal totalStockValue = medicineStockRepository.findTotalStockValue();

        stats.put("todaySales", todaySales);
        stats.put("todayReturns", todayReturns);
        stats.put("totalSales", totalSales);
        stats.put("totalReturns", totalReturns);
        stats.put("lowStockCount", lowStockMedicines.size());
        stats.put("lowStockMedicines", lowStockMedicines);
        stats.put("totalStockValue", totalStockValue != null ? totalStockValue : BigDecimal.ZERO);
        
        return ResponseEntity.ok(ApiResponse.success(stats, "Stats fetched"));
    }

    @GetMapping("/recent-activities")
    public ResponseEntity<ApiResponse<List<PharmacyBill>>> getRecentActivities() {
        return ResponseEntity.ok(ApiResponse.success(pharmacyBillRepository.findAll(), "Recent activities fetched"));
    }
}
