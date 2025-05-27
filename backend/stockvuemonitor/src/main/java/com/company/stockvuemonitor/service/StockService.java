package com.company.stockvuemonitor.service;

import com.company.stockvuemonitor.model.StockEntity;
import com.company.stockvuemonitor.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class StockService {

    private final StockRepository stockRepository; //JPA Repository 依賴注入

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * 取得指定股票最新價格，並存入資料庫
     * @param symbol 股票代碼，例如 "2330"
     * @return JSON 格式的股票數據
     */
    public String fetchAndSaveStockPrice(String symbol) {
        try {
            // 建構 TWSE API 請求 URL
            String apiUrl = "https://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=tse_"
                    + symbol + ".tw&json=1&delay=0";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            // 讀取 API 回應
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // 解析 JSON 數據
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());

            if (rootNode.has("msgArray") && rootNode.get("msgArray").size() > 0) {
                JsonNode stockData = rootNode.get("msgArray").get(0);
                String stockCode = stockData.get("c").asText();  // 股票代碼
                String lastPrice = stockData.get("z").asText();    // 最新成交價格

                // 建立並存入資料庫
                StockEntity stockEntity = new StockEntity(stockCode, lastPrice);
                stockRepository.save(stockEntity);

                return String.format("{ \"symbol\": \"%s\", \"price\": \"%s\", \"timestamp\": \"%s\" }",
                        stockCode, lastPrice, stockEntity.getTimestamp().toString());
            } else {
                return "{ \"error\": \"無法取得有效的股票資料\" }";
            }
        } catch (IOException e) {
            return "{ \"error\": \"API 請求失敗: " + e.getMessage() + "\" }";
        }
    }

    /**
     * 查詢指定股票的歷史價格
     * @param symbol 股票代碼，例如 "2330"
     * @param days 查詢的天數，例如 7 表示最近 7 天
     * @return JSON 格式的股票歷史數據
     */
    public String getStockHistory(String symbol, int days) {
        LocalDateTime startTime = LocalDateTime.now().minusDays(days); // 計算起始時間
        List<StockEntity> stockHistory = stockRepository.findStocksInTimeRange(symbol, startTime);

        // 構造 JSON 格式回應
        StringBuilder jsonResponse = new StringBuilder("{ \"symbol\": \"" + symbol + "\", \"prices\": [");
        for (StockEntity stock : stockHistory) {
            jsonResponse.append(String.format("{ \"price\": \"%s\", \"timestamp\": \"%s\" },",
                    stock.getPrice(), stock.getTimestamp()));
        }

        if (!stockHistory.isEmpty()) {
            jsonResponse.deleteCharAt(jsonResponse.length() - 1); // 移除最後的逗號
        }

        jsonResponse.append("] }");

        return jsonResponse.toString();
    }
}
