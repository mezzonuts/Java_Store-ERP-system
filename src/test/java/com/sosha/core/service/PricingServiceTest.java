package com.sosha.core.service;

import com.sosha.core.domain.SaleItem;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {
    @Test
    void testCalculateTotalSimple() {
        SaleService service = new SaleService();
        List<SaleItem> items = new ArrayList<>();
        SaleItem item = new SaleItem();
        item.setUnitPrice(new BigDecimal("10000"));
        item.setQty(new BigDecimal("2"));
        items.add(item);

        BigDecimal total = service.calculateTotal(items);
        assertEquals(new BigDecimal("20000"), total);
    }
}
