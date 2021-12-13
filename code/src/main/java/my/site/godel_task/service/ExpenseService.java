package my.site.golden_task.service;

import my.site.golden_task.entity.Expense;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {

    Iterable<Expense> getSearchResult(Long idCategory, String fromDateStringFormat, String untilDateStringFormat);

    double getSum(Iterable<Expense> expenses);

    String getTheMostExpenseMonth(List<Expense> expenses);

    String getLastDayWithoutExpense(Iterable<Expense> expenses, String fromDateStringFormat,
                                    String untilDateStringFormat);
}
