import java.util.ArrayList;
import java.util.Arrays;

public class StockDataAnalysis {

    public static void main(String[] args) {
        // Data type is double[] to handle decimal prices, per instructor feedback.
        double[] stockPricesArray = {250.0, 255.0, 260.0, 252.0, 258.0, 265.0, 270.0, 268.0, 275.0, 265.0};

        // Data type is ArrayList<Double>.
        ArrayList<Double> stockPricesList = new ArrayList<>();
        for (double price : stockPricesArray) {
            stockPricesList.add(price);
        }

        System.out.println("Stock Prices Data Analysis");
        System.out.println("--------------------------");
        System.out.println("Original Stock Prices (Array): " + Arrays.toString(stockPricesArray));
        System.out.println("Original Stock Prices (ArrayList): " + stockPricesList);
        System.out.println("--------------------------\n");

        // --- Analysis using ALL overloaded methods to clear warnings ---

        System.out.println("--- Analysis using Array methods ---");
        double averagePriceArray = calculateAveragePrice(stockPricesArray);
        System.out.printf("1. Average Stock Price (from Array): %.2f\n", averagePriceArray);

        double maxPriceArray = findMaximumPrice(stockPricesArray);
        System.out.printf("2. Maximum Stock Price (from Array): %.2f\n", maxPriceArray);

        double targetPrice = 265.0;
        int occurrencesArray = countOccurrences(stockPricesArray, targetPrice);
        System.out.printf("3. Occurrences of price %.2f (from Array): %d times\n", targetPrice, occurrencesArray);

        ArrayList<Double> cumulativeSumArray = computeCumulativeSum(stockPricesArray);
        System.out.println("4. Cumulative Sum (from Array): " + cumulativeSumArray);

        System.out.println("\n--- Analysis using ArrayList methods ---");
        double averagePriceList = calculateAveragePrice(stockPricesList);
        System.out.printf("1. Average Stock Price (from ArrayList): %.2f\n", averagePriceList);

        double maxPriceList = findMaximumPrice(stockPricesList);
        System.out.printf("2. Maximum Stock Price (from ArrayList): %.2f\n", maxPriceList);

        int occurrencesList = countOccurrences(stockPricesList, targetPrice);
        System.out.printf("3. Occurrences of price %.2f (from ArrayList): %d times\n", targetPrice, occurrencesList);

        ArrayList<Double> cumulativeSumList = computeCumulativeSum(stockPricesList);
        System.out.println("4. Cumulative Sum (from ArrayList): " + cumulativeSumList);
    }

    // --- Method Implementations for BOTH Arrays and ArrayLists ---

    // 1A. Calculate Average (for Array)
    public static double calculateAveragePrice(double[] prices) {
        if (prices == null || prices.length == 0) return 0.0;
        double sum = 0;
        for (double price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    // 1B. Overloaded method for ArrayList
    public static double calculateAveragePrice(ArrayList<Double> prices) {
        if (prices == null || prices.isEmpty()) return 0.0;
        double sum = 0;
        for (double price : prices) {
            sum += price;
        }
        return sum / prices.size();
    }

    // 2A. Find Maximum (for Array)
    public static double findMaximumPrice(double[] prices) {
        if (prices == null || prices.length == 0) return 0.0;
        double maxPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > maxPrice) {
                maxPrice = prices[i];
            }
        }
        return maxPrice;
    }

    // 2B. Overloaded method for ArrayList
    public static double findMaximumPrice(ArrayList<Double> prices) {
        if (prices == null || prices.isEmpty()) return 0.0;
        // Using getFirst() as suggested by IntelliJ for cleaner code
        double maxPrice = prices.getFirst();
        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i) > maxPrice) {
                maxPrice = prices.get(i);
            }
        }
        return maxPrice;
    }

    // 3A. Count Occurrences (for Array)
    public static int countOccurrences(double[] prices, double targetPrice) {
        if (prices == null) return 0;
        int count = 0;
        // Using a small tolerance for double comparison is good practice
        for (double price : prices) {
            if (Math.abs(price - targetPrice) < 0.001) {
                count++;
            }
        }
        return count;
    }

    // 3B. Overloaded method for ArrayList
    public static int countOccurrences(ArrayList<Double> prices, double targetPrice) {
        if (prices == null) return 0;
        int count = 0;
        for (double price : prices) {
            if (Math.abs(price - targetPrice) < 0.001) {
                count++;
            }
        }
        return count;
    }

    // 4A. Compute Cumulative Sum (for Array)
    public static ArrayList<Double> computeCumulativeSum(double[] prices) {
        ArrayList<Double> cumulativeSumList = new ArrayList<>();
        if (prices == null) return cumulativeSumList;
        double runningSum = 0;
        for (double price : prices) {
            runningSum += price;
            cumulativeSumList.add(runningSum);
        }
        return cumulativeSumList;
    }

    // 4B. Overloaded method for ArrayList
    public static ArrayList<Double> computeCumulativeSum(ArrayList<Double> prices) {
        ArrayList<Double> cumulativeSumList = new ArrayList<>();
        if (prices == null) return cumulativeSumList;
        double runningSum = 0;
        for (double price : prices) {
            runningSum += price;
            cumulativeSumList.add(runningSum);
        }
        return cumulativeSumList;
    }
}