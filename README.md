# Vue + Spring Boot股票追蹤系統

一個使用 Vue 3 與 Vue Router 搭配 Axios 串接 Spring Boot API 的專案。
該系統可用來查詢股票最新資訊，並且以清晰的介面顯示結果。

串接"臺灣證券交易所 OpenAPI"

## 重點特色

- **前端架構**  
  使用 Vue CLI 建立，採用 Vue 3 框架與 Composition API 撰寫程式碼。

- **動態路由管理**  
  利用 Vue Router 設定多個路由頁面（如首頁及股票查詢頁面），在不同頁面間切換。

- **API 串接**  
  透過 Axios 與 Spring Boot 後端的 API（http://localhost:8080/api/stocks）串接，直接查詢股票即時價格。
串接"臺灣證券交易所 OpenAPI" 。

- **跨域代理**  
  在 vue.config.js中配置代理，讓開發環境下前後端可以在不同端口互相通信而不受跨域限制。

- **資料庫應用**
  使用PostgreSQL儲存查詢的股票即時價格，可進行分析。

## 專案結構
stock-vue-monitor/
├── public/
│   └── index.html           # 預設 HTML 模板
├── src/
│   ├── api.js               # 管理與後端 API 的請求
│   ├── App.vue              # 主應用組件，包含 <router-view/>
│   ├── main.js              # Vue 入口檔案：建立並掛載 Vue 實例
│   ├── router/
│   │   └── index.js         # Vue Router 配置檔案
│   ├── views/
│   │   ├── HomeView.vue     # 首頁組件 (歡迎或導向頁面)
│   │   └── StockView.vue    # 股票查詢與數據展示頁面
├── vue.config.js            # Vue CLI 配置 (如 devServer 設定、代理等)
├── package.json             # 專案依賴、腳本與專案設定
└── README.md                # 專案說明文件

stockvuemonitor/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/stockmonitor/
│   │   │       ├── StockMonitorApplication.java       # 啟動類別，Spring Boot 入口
│   │   │       ├── controller/
│   │   │       │   └── StockController.java           # 定義 REST API 控制器 (/api/stocks)
│   │   │       ├── model/
│   │   │       │   └── Stock.java                     # 定義股票資料模型
│   │   │       └── service/
│   │   │           └── StockService.java              # 處理股票資料的業務邏輯
│   │   └── resources/
│   │       ├── application.properties               # 應用程式配置（例如埠口、資料庫連線等）
│   │       └── static/                              # (選用) 靜態資源，如前端檔案等
├── pom.xml                                         # Maven 專案管理與依賴設定檔
└── README.md                                       # 專案說明文件，包含專案重點與使用說明

## 前端使用說明

前端 (Vue CLI) 測試網址： http://localhost:5173/ 
在瀏覽器中此網址測試前端頁面呈現。

## 後端使用說明

後端 (Spring Boot) 測試網址： http://localhost:8080/api/stocks 
此網址是 API 的入口，測試是否能獲取股票相關數據。
如果需要查詢特定股票，例如台灣股票代碼 "2330"： http://localhost:8080/api/stocks/2330

資料庫連線 :
請將 application-example.properties 複製為 application.properties 並填入資料庫設定
