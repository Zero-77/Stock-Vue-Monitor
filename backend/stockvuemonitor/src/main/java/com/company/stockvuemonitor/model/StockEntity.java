package com.company.stockvuemonitor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * 股票數據表的JPA實體類，對應資料庫中的 "stocks" 鰾格
 */
@Entity //標記為JPA Entity，Spring Boot 會自動建立對應表格
@Table(name = "stocks") //指定表格名稱
public class StockEntity {

    @Id //設定主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY) //讓主鍵自動遞增
    private Long id;

    private String symbol;  // 股票代碼，例如 "2330"
    private String price;   // 成交價格，例如 "645"
    private LocalDateTime timestamp;    // 記錄股票數據的時間戳記

    // **建構子（JPA 需要無參數建構子）**
    public StockEntity() {}

    public StockEntity(String symbol, String price) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = LocalDateTime.now(); //取得當前時間
    }

    // **Getter & Setter 方法**
    public Long getId() { return id; }
    public String getSymbol() { return symbol; }
    public String getPrice()  { return price; }
    public LocalDateTime getTimestamp() { return timestamp; }
}