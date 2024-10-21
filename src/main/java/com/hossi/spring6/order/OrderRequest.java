package com.hossi.spring6.order;

import java.math.BigDecimal;

public record OrderRequest(String no, BigDecimal totalPrice) {
}
