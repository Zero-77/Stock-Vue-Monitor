import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/stocks";

export const fetchStockPrice = async (symbol) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/${symbol}`);
    return response.data;
  } catch (error) {
    console.error("獲取股價失敗:", error);
  }
};
