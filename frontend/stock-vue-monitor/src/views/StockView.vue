<template>
  <div>
    <h1>股票監控系統</h1>
    <input v-model="symbol" placeholder="輸入股票代碼，例如 2330" />
    <button @click="getStockData">查詢</button>

    <div v-if="stock">
      <h2>最新價格: {{ stock.price }}</h2>
      <p>時間: {{ new Date(stock.timestamp).toLocaleString("zh-TW") }}</p>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { fetchStockPrice } from "../api";

export default {
  setup() {
    const symbol = ref("2330");
    const stock = ref(null);

    const getStockData = async () => {
      try {
        stock.value = await fetchStockPrice(symbol.value);
      } catch (error) {
        console.error("查詢股票數據失敗:", error);
      }
    };

    return { symbol, stock, getStockData };
  }
};
</script>
