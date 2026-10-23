package com.portfolio.finance.service;

import com.portfolio.finance.model.TransactionRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ReconciliationService {
    private final List<TransactionRecord> records = new CopyOnWriteArrayList<>(List.of(
        new TransactionRecord("TX-1001","INV-204","Cash",new BigDecimal("1250.00"),"USD","BANK","UNMATCHED"),
        new TransactionRecord("TX-1002","INV-204","Accounts Receivable",new BigDecimal("-1250.00"),"USD","ERP","UNMATCHED"),
        new TransactionRecord("TX-1003","INV-205","Cash",new BigDecimal("640.50"),"USD","BANK","UNMATCHED"),
        new TransactionRecord("TX-1004","INV-999","Fees",new BigDecimal("-12.00"),"USD","ERP","UNMATCHED")
    ));

    public List<TransactionRecord> all() { return List.copyOf(records); }

    public Map<String, Object> reconcile() {
        Map<String, List<TransactionRecord>> groups = new ConcurrentHashMap<>();
        records.parallelStream().forEach(r -> groups.computeIfAbsent(r.reference(), k -> new CopyOnWriteArrayList<>()).add(r));
        Set<String> matchedRefs = ConcurrentHashMap.newKeySet();
        groups.entrySet().parallelStream().forEach(entry -> {
            BigDecimal sum = entry.getValue().stream().map(TransactionRecord::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (sum.compareTo(BigDecimal.ZERO) == 0 && entry.getValue().size() > 1) matchedRefs.add(entry.getKey());
        });
        List<TransactionRecord> updated = records.stream().map(r -> new TransactionRecord(
            r.id(), r.reference(), r.account(), r.amount(), r.currency(), r.source(),
            matchedRefs.contains(r.reference()) ? "MATCHED" : "UNMATCHED")).toList();
        records.clear(); records.addAll(updated);
        long matched = records.stream().filter(r -> r.status().equals("MATCHED")).count();
        return Map.of("processed", records.size(), "matched", matched, "unmatched", records.size()-matched,
                      "matchedReferences", matchedRefs, "message", "Reconciliation completed");
    }
}
