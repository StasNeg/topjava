package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.DateTimeMapper;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping(value = "rest/meals")
public class MealRestController extends AbstractMealController {

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }


    @GetMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal create() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);
    }


    @GetMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal update(@PathVariable("id") int id) {
        return super.get(id);
    }

    @PostMapping(value = "/createUpdate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> updateOrCreate(@RequestBody Meal meal) {
        if (meal.getId().equals(null)) {
            meal = super.create(meal);
        } else
            super.update(meal, meal.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/meals/createUpdate/{id}")
                .buildAndExpand(meal.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(meal);
    }


    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestBody DateTimeMapper dateTimeMapper) {
        System.out.println("\n\n/n/n\n\n");
        return super.getBetween(dateTimeMapper.getDateStart().toLocalDate(),
                dateTimeMapper.getDateStart().toLocalTime(),
                dateTimeMapper.getDateEnd().toLocalDate(),
                dateTimeMapper.getDateEnd().toLocalTime());
    }
}