package com.company.stockvuemonitor.repository;


import com.company.stockvuemonitor.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 股票數據的 JPA Repository 介面，負責儲存與查詢股票數據。
 * 繼承 JpaRepository，提供基本的 CRUD 操作。
 */
public interface StockRepository extends JpaRepository<StockEntity, Long> {

    /**
     * 查詢特定股票的最近一次交易記錄
     * @param symbol 股票代碼，例如 "2330"
     * @return 最近的股票交易數據
     */
    StockEntity findFirstBySymbolOrderByTimestampDesc(String symbol);

    /**
     * 查詢指定時間範圍內的股票交易數據
     * @param symbol 股票代碼
     * @param startTime 查詢的起始時間
     * @return 股票數據列表
     */
    @Query("SELECT s FROM StockEntity s WHERE s.symbol = :symbol AND s.timestamp >= :startTime ORDER BY s.timestamp DESC")
    List<StockEntity> findStocksInTimeRange(@Param("symbol") String symbol, @Param("startTime") LocalDateTime startTime);
}
