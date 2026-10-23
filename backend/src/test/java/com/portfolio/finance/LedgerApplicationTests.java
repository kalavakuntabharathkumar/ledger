package com.portfolio.finance;

import com.portfolio.finance.service.ReconciliationService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LedgerApplicationTests {
    @Test void reconciliationMatchesBalancedReferences() {
        ReconciliationService service = new ReconciliationService();
        var result = service.reconcile();
        assertEquals(4, result.get("processed"));
        assertEquals(2L, result.get("matched"));
        assertEquals(2L, result.get("unmatched"));
    }
}
