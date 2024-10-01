import java.util.*;
import java.util.stream.Collectors;

public class Analyzer {

    public double calculateTotalProfitLoss(List<Transaction> transactions) {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public int countTransactionsInMonth(List<Transaction> transactions, String month) {
        return (int) transactions.stream()
                .filter(t -> t.getDate().startsWith(month))
                .count();
    }

    public List<Transaction> topExpenses(List<Transaction> transactions, int topN) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .limit(topN)
                .collect(Collectors.toList());
    }

    public String mostSpentCategory(List<Transaction> transactions) {
        Map<String, Double> categoryTotals = new HashMap<>();

        transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .forEach(t -> categoryTotals.merge(t.getDescription(), Math.abs(t.getAmount()), Double::sum));

        return Collections.max(categoryTotals.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}

