//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankStatementAnalyzer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file path:");
        String filePath = scanner.nextLine();

        List<Transaction> transactions = new ArrayList<>();
        TransactionReader reader = new TransactionReader();
        Analyzer analyzer = new Analyzer();

        try {
            if (filePath.endsWith(".csv")) {
                transactions = reader.readCSV(filePath);
            } else if (filePath.endsWith(".json")) {
                transactions = reader.readJSON(filePath);
            } else if (filePath.endsWith(".xml")) {
                transactions = reader.readXML(filePath);
            } else {
                System.out.println("Unsupported file format.");
                return;
            }

            double totalProfitLoss = analyzer.calculateTotalProfitLoss(transactions);
            System.out.println("Total Profit/Loss: " + totalProfitLoss);


            int janTransactions = analyzer.countTransactionsInMonth(transactions, "30-01-2017");
            System.out.println("Transactions in January: " + janTransactions);

            List<Transaction> topExpenses = analyzer.topExpenses(transactions, 10);
            System.out.println("Top 10 Expenses: " + topExpenses);

            String mostSpentCategory = analyzer.mostSpentCategory(transactions);
            System.out.println("Most spent category: " + mostSpentCategory);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
