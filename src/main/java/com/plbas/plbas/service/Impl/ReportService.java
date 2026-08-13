package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.enums.CategoryDirection;

import java.math.BigDecimal;
import java.util.Map;

public interface ReportService {

    Response<Map<String,Object>> getMonthTrendByYearMonth(String yearMonth, CategoryDirection direction);

    Response<Map<String,Object>> getCategoryPie();

    Response<BigDecimal> getTotalAccountBalance();

}
