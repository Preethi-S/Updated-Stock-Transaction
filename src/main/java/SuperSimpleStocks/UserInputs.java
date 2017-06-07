package SuperSimpleStocks;

import java.util.*;

import SuperSimpleStocks.Transaction.StockTransactions;
import SuperSimpleStocks.Transaction.TransactionCollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInputs
{
    private static Scanner input = new Scanner(System.in);
    private static Logger Logger = LoggerFactory.getLogger(UserInputs.class);
    private List<SimpleStock> inputStocks = new ArrayList<SimpleStock>();
    private List<StockTransactions> inputTransactions = new ArrayList<StockTransactions>();

    private void recordStocks()
      {
          GetStockInputs getStock = new GetStockInputs();
          System.out.println("Enter the number of Stock Inputs: ");
          int numberOfInputStocks = input.nextInt();
          for (int i = 0; i < numberOfInputStocks; i++)
          {
              inputStocks.add(getStock.getUserStockInputs());
          }
      }
    private void calculateDividendYieldPERatio()
    {
        System.out.println("Enter the Stock Symbol for Dividend Yield and P/E Ration calculation: ");
        String stockSymbol = input.next();
        Iterator<SimpleStock> iterator= inputStocks.listIterator();
        try{
            while (iterator.hasNext()){
                SimpleStock stockCalculation = iterator.next();
                if (stockCalculation.stockSymbol.equalsIgnoreCase(stockSymbol))
                {
                    System.out.println("Dividend Yield for Stock Symbol  " + stockSymbol + " "+ stockCalculation.computeDivyield());
                    System.out.println("P/E Ratio for Stock Symbol  " + stockSymbol + " " + stockCalculation.computePERatio());
                }
            }
        }catch(IndexOutOfBoundsException e)
        {
            Logger.info("Simple Stocks List Index out of Bound exception" );
        }
    }
    private void calculateAllShareIndex()
    {
        SimpleStockCollection allStocks = new SimpleStockCollection(inputStocks);
        try{
            double geometricMean = allStocks.allShareIndex();
            System.out.println("GBCE All Share Index of all stocks: " + geometricMean);
        } catch(IndexOutOfBoundsException e)
        {
            Logger.info("Simple Stocks List Index out of Bound exception" );
        }

    }
    private void recordTransactions() {
        GetTransactionInputs getTransaction = new GetTransactionInputs();
        System.out.println("Enter the number of Transactions to be recorded:");
        int proceedInput = input.nextInt();
        for (int i = 0; i < proceedInput; i++)
            inputTransactions.add(getTransaction.getUserTransactionInputs());

    }
    private void calculateVolumeWeightedStockPrice()
    {
        String transStockSymbol;
        System.out.println("Do you want to calculate Volume Weighted Stock Price:");
        char calculateVolumeWeighted=input.next().charAt(0);
           if (calculateVolumeWeighted == 'Y' || calculateVolumeWeighted == 'y')
                {
                    System.out.println("Enter the Stock Symbol: ");
                    transStockSymbol =input.next();
                    System.out.println("Volume Weighted Stock Price on trades in past 15 minutes: ");
                    TransactionCollection inputTransactionCollection = new TransactionCollection(inputTransactions);
                    System.out.println(inputTransactionCollection.computeVolumeWeightedStockPrice(transStockSymbol.toUpperCase()));
                }
           else
                    Logger.info("Volume weighted Stock is not calculated");


    }


    public static void main (String[] args)
    {
        UserInputs userValue = new UserInputs();
        System.out.println("Enter Choice of Computation (1/2)");
        System.out.println("1. Record Stocks");
        System.out.println("2. Record Transactions");
        int computationChoice = input.nextInt();
        switch(computationChoice)
        {
            case 1:
                userValue.recordStocks();
                if (userValue.inputStocks.size() > 0) {
                    System.out.println("Do you want to perform calculations on Stock Records : ");
                    char computeYes = input.next().charAt(0);
                    if (computeYes == 'Y' || computeYes == 'y')
                        userValue.computeStockCalculations();
                }
                break;
            case 2:
                userValue.recordTransactions();
                if (userValue.inputTransactions.size()>0) {
                    userValue.calculateVolumeWeightedStockPrice();
                } else
                    Logger.info("No transactions to calculate Volume Weighted Stock Price");
                break;

        }
        input.close();
    }

    private void computeStockCalculations()
    {
        int computationChoice;
        System.out.println("1. Calculate Dividend Yield & P/E Ratio");
        System.out.println("2. Calculate GBCE All Share Index");
        computationChoice = input.nextInt();
        switch(computationChoice)
        {
            case 1:
                this.calculateDividendYieldPERatio();
                break;
            case 2:
                this.calculateAllShareIndex();
                break;
        }
    }

    class GetStockInputs{

       private SimpleStock getUserStockInputs()
        {
            SimpleStock inputStock;
            try
            {
                String inputstockSymbol = getUserInputs("Enter the Stock Symbol:");
                int inputlastDividend=Integer.parseInt(getUserInputs("Enter the last Div:"));
                int inputfixedDividend=Integer.parseInt(getUserInputs("Enter the fixed Div:"));
                int inputparValue=Integer.parseInt(getUserInputs("Enter the parValue:"));
                int inputmarketPrice= Integer.parseInt(getUserInputs("Enter the Market Price:"));
                String inputStockType = getUserInputs("Enter the Type (COMMON or PREFERRED):");
                if (inputStockType.equalsIgnoreCase("COMMON"))
                    inputStock = new CommonSimpleStock(inputstockSymbol.toUpperCase(),inputlastDividend,inputfixedDividend,inputparValue,inputmarketPrice) ;
                else
                    inputStock = new PreferredSimpleStock(inputstockSymbol.toUpperCase(),inputlastDividend,inputfixedDividend,inputparValue,inputmarketPrice) ;
                return inputStock;
            }
            catch(NumberFormatException e)
            {
                Logger.info("Invalid Stock Data is entered");
                return null;
            }

        }

        String getUserInputs(String displayText)
        {
            System.out.println(displayText);
            return (input.next());

        }
    }
    class GetTransactionInputs{

        private StockTransactions getUserTransactionInputs()
        {
            StockTransactions inputTransaction;
            try{
                String userTransactionSymbol=getUserInputs("Enter the Stock Symbol:");
                char userBuySellIndicator =getUserInputs("Enter the B/S Indicator:").charAt(0);
                int userQuantity= Integer.parseInt(getUserInputs("Enter the Quantity:"));
                int userTradePrice=Integer.parseInt(getUserInputs("Enter the Trade Price:"));
                inputTransaction = new StockTransactions(userTransactionSymbol.toUpperCase(),Character.toUpperCase(userBuySellIndicator),userQuantity,userTradePrice);
                return inputTransaction;
            }
            catch(InputMismatchException e)
            {
                Logger.info("Input Transaction details are Invalid");
                return null;
            }

        }

        String getUserInputs(String displayText)
        {
            System.out.println(displayText);
            return (input.next());

        }
    }
}
