package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.enums.CategoryDirection;
import com.plbas.plbas.service.Impl.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 统计报表接口
 */
@Tag(name = "总览管理")
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Operation(summary = "月度消费趋势")
    @GetMapping("/get/monthTrend")
    public Response<Map<String,Object>> getMonthTrendByYearMonth(String yearMonth, CategoryDirection direction)
    {
        return reportService.getMonthTrendByYearMonth(yearMonth,direction);
    }

    @Operation(summary = "消费分类占比")
    @GetMapping("/get/categoryPie")
    public Response<Map<String,Object>> getCategoryPie()
    {
        return reportService.getCategoryPie();
    }

    @Operation(summary = "查看总余额")
    @GetMapping("/get/totalBalance")
    public Response<BigDecimal> getTotalAccountBalance()
    {
        return reportService.getTotalAccountBalance();
    }

}
