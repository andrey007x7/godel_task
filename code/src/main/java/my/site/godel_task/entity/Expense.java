package my.site.golden_task.entity;





import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private LocalDate date;

    @ManyToOne()
    private Category category;

    private double amount;

    public Expense() {
    }

    public Expense(String name, LocalDate date, Category category, double amount) {
        this.name = name;
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "| name='" + name + '\'' +
                ", date=" + date +
                ", amount=" + amount + "|";
    }
}
