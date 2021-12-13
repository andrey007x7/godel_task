package my.site.golden_task.service;

import my.site.golden_task.entity.Category;
import my.site.golden_task.entity.Expense;
import my.site.golden_task.repo.CategoryRepository;
import my.site.golden_task.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ExpenseServiceImp implements ExpenseService{

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Iterable<Expense> getSearchResult(Long idCategory, String fromDateStringFormat, String untilDateStringFormat) {
        Iterable<Expense> expenses;
        LocalDate fromDate = getFromDate(fromDateStringFormat);
        LocalDate untilDate = getUntilDate(untilDateStringFormat);
        Category category;
        if (idCategory == 0) {
            expenses = expenseRepository.findAllByDateBetween(fromDate, untilDate);
        } else {
            Optional<Category> optionalCategory = categoryRepository.findById(idCategory);
            category = new Category();
            if (optionalCategory.isPresent()) {
                category = optionalCategory.get();
            }
            expenses = expenseRepository.findAllByCategoryEqualsAndDateBetween(category, fromDate, untilDate);
        }

        return expenses;
    }

    private LocalDate getUntilDate(String untilDateStringFormat) {
        LocalDate untilDate;
        if (untilDateStringFormat == null || untilDateStringFormat.isEmpty()) {
            untilDate = LocalDate.now();
        } else {
            untilDate = LocalDate.parse(untilDateStringFormat);
        }
        return untilDate;
    }

    private LocalDate getFromDate(String fromDateStringFormat) {
        if (fromDateStringFormat == null || fromDateStringFormat.isEmpty()) {
            fromDateStringFormat = "1970-01-01";
        }
        LocalDate fromDate = LocalDate.parse(fromDateStringFormat);
        return fromDate;
    }

    @Override
    public double getSum(Iterable<Expense> expenses) {
        List<Expense> expensesList = (List<Expense>) expenses;
        return expensesList.stream().mapToDouble(Expense::getAmount).sum();
    }

    @Override
    public String getTheMostExpenseMonth(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            return "Not found any expenses";
        }
        expenses.sort(Comparator.comparing(Expense::getDate));
        double theMostExpenseSum = expenses.get(0).getAmount();
        double sumExpenseOfMonth = expenses.get(0).getAmount();
        LocalDate theMostExpenseMonth = expenses.get(0).getDate();
        for (int i = 1; i < expenses.size(); i++) {
            if (expenses.get(i - 1).getDate().getMonthValue() == expenses.get(i).getDate().getMonthValue()
            && expenses.get(i-1).getDate().getYear() == expenses.get(i).getDate().getYear()) {
                sumExpenseOfMonth += expenses.get(i).getAmount();
            } else {
                sumExpenseOfMonth = expenses.get(i).getAmount();
            }
            if (sumExpenseOfMonth > theMostExpenseSum) {
                theMostExpenseSum = sumExpenseOfMonth;
                theMostExpenseMonth  = expenses.get(i).getDate();
            }
        }
        return "The most expense month was " + theMostExpenseMonth.format(DateTimeFormatter.ofPattern("MM.yy"))
                +  " on sum: " + theMostExpenseSum;
    }

    @Override
    public String getLastDayWithoutExpense(Iterable<Expense> expenses, String fromDateStringFormat,
                                           String untilDateStringFormat) {
        LocalDate fromDate = getFromDate(fromDateStringFormat);
        LocalDate lastDay = getUntilDate(untilDateStringFormat);
        boolean check = false;
        do {
            for (Expense expense : expenses) {
                if (expense.getDate().equals(lastDay)) {
                    check = true;
                    break;
                }
            }
            if (check) {
                lastDay = lastDay.minusDays(1);
                check = false;
            } else {
                break;
            }
        } while (!fromDate.equals(lastDay));
        if (lastDay.equals(fromDate.minusDays(1))) {
            return "No day without expense in this period";
        }
        return "Last day without expense: " + lastDay.format(DateTimeFormatter.ofPattern("dd.MM"));
    }
}
