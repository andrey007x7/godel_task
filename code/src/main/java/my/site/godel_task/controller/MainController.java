package my.site.golden_task.controller;

import my.site.golden_task.entity.Category;
import my.site.golden_task.entity.Expense;
import my.site.golden_task.repo.CategoryRepository;
import my.site.golden_task.repo.ExpenseRepository;
import my.site.golden_task.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ExpenseService expenseService;

    @GetMapping("/")
    public String home(@RequestParam(name = "category", required = false) Long idCategory,
                         @RequestParam(name = "fromDate", required = false) String fromDateStringFormat,
                         @RequestParam(name = "untilDate", required = false) String untilDateStringFormat,
                         Model model) {
        Iterable<Expense> expenses;
        if (idCategory == null) {
             expenses = expenseRepository.findAll();
        } else {
             expenses = expenseService.getSearchResult(idCategory,
                    fromDateStringFormat, untilDateStringFormat);
        }
        Iterable<Category> categories = categoryRepository.findAll();
        List<Expense> expenseList = new ArrayList<>((List)expenses);
        model.addAttribute("sum", expenseService.getSum(expenses));
        model.addAttribute("month", expenseService.getTheMostExpenseMonth(expenseList));
        model.addAttribute("dayWithoutExpense", expenseService.getLastDayWithoutExpense(expenses,
                fromDateStringFormat, untilDateStringFormat));
        model.addAttribute("expenses", expenses);
        model.addAttribute("categories", categories);
        return "main";
    }
}
