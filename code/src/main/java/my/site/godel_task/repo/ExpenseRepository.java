package my.site.golden_task.repo;

import my.site.golden_task.entity.Category;
import my.site.golden_task.entity.Expense;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    Iterable<Expense> findAllByCategoryEqualsAndDateBetween(Category category, LocalDate fromDate,
                                                            LocalDate untilDate);

    Iterable<Expense> findAllByDateBetween(LocalDate fromDate, LocalDate untilDate);
}
