package com.portfolio.finance.api;

import com.portfolio.finance.model.TransactionRecord;
import com.portfolio.finance.service.ReconciliationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {
    private final ReconciliationService service;
    public TransactionController(ReconciliationService service) { this.service = service; }

    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "UP", "service", "ledger-reconciliation"); }

    @GetMapping("/transactions")
    public List<TransactionRecord> transactions() { return service.all(); }

    @PostMapping("/transactions/reconcile")
    public Map<String, Object> reconcile() { return service.reconcile(); }
}
