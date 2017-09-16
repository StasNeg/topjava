package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDateTime;

/**
 * Created by Stanislav on 16.09.2017.
 */
public class MealTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    public MealTo() {
    }


    public MealTo(Integer id, @NotBlank String description, @Range(min = 10, max = 10000) @NotNull Integer calories, @NotNull LocalDateTime dateTime) {
        super(id);
        this.description = description;
        this.calories = calories;
        this.dateTime = dateTime;
    }

    @NotBlank
    private String description;

    @Range(min = 10, max = 10000)
    @NotNull
    private Integer calories;


    @NotNull
    private LocalDateTime dateTime;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = parseLocalDateTime(dateTime);
    }
}
