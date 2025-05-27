package com.company.stockvuemonitor.controller;

import com.company.stockvuemonitor.service.StockService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;


    /*
    透過構造函式注入 StockService
     */
    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    /**
     * 取得指定股票最新價格
     * @param symbol 股票代碼，例如 "2330"
     * @return JSON 格式的股票數據
     */
    @GetMapping("/{symbol}")
    public String fetchStockPrice(@PathVariable String symbol) {
        return stockService.fetchAndSaveStockPrice(symbol);
    }

    /**
     * 取得指定股票的歷史價格
     * @param symbol 股票代碼，例如 "2330"
     * @param days 查詢的天數，例如 7 表示最近 7 天
     * @return JSON 格式的歷史股價
     */
    @GetMapping("/history/{symbol}/{days}")
    public String getStockHistory(@PathVariable String symbol, @PathVariable int days) {
        return stockService.getStockHistory(symbol, days);
    }
}
