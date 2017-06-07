package SuperSimpleStocks.Transaction;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockTransactions {
		 String transactionStockSymbol;	
		 LocalDateTime transactionDate;
		 int quantity;
		 char buysellIndicator;
		 int tradePrice;
		 static Logger Logger = LoggerFactory.getLogger(StockTransactions.class);
		 
		public StockTransactions(String inputTransactionSymbol, char inputBuySellIndicator,int inputQuantity,int inputTradePrice)
			 {
				 transactionStockSymbol = inputTransactionSymbol;
				 transactionDate = LocalDateTime.now();
				 quantity = inputQuantity;
				 buysellIndicator = inputBuySellIndicator;
				 tradePrice = inputTradePrice;
			 }
			 
//			 public static StockTransactions getUserTransactionInputs(StockTransactions transaction)
//		 {
//			 String userTransactionSymbol;
//	       	 char userBuySellIndicator;
//			 int userQuantity;
//			 int userTradePrice;
//			 Scanner inputScanner = new Scanner(System.in);
//
//			 return transaction;
//		 }


}
