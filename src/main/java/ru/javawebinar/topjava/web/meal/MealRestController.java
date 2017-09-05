package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.DateTimeMapper;
import ru.javawebinar.topjava.to.MealWithExceed;

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


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createPost(@RequestBody Meal meal) {
        meal = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/meals/create/{id}")
                .buildAndExpand(meal.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(meal);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> updatePost(@RequestBody Meal meal) {
        super.update(meal, meal.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/meals/update/{id}")
                .buildAndExpand(meal.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(meal);
    }

    @PostMapping(value = "/filterWithAttributes", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestParam("dateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTimeStart,
                                           @RequestParam("dateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTimeEnd) {
        LocalDate dateStart = dateTimeStart.toLocalDate() == null ? null : dateTimeStart.toLocalDate();
        LocalDate dateEnd = dateTimeEnd.toLocalDate() == null ? null : dateTimeEnd.toLocalDate();
        LocalTime timeStart = dateTimeStart.toLocalTime() == null ? null : dateTimeStart.toLocalTime();
        LocalTime timeEnd = dateTimeEnd.toLocalTime() == null ? null : dateTimeEnd.toLocalTime();
        return super.getBetween(dateStart, timeStart, dateEnd, timeEnd);
    }


    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestBody DateTimeMapper dateTimeMapper) {
        LocalDate dateStart = dateTimeMapper.getDateStart() == null ? null : dateTimeMapper.getDateStart().toLocalDate();
        LocalDate dateEnd = dateTimeMapper.getDateEnd() == null ? null : dateTimeMapper.getDateEnd().toLocalDate();
        LocalTime timeStart = dateTimeMapper.getDateStart() == null ? null : dateTimeMapper.getDateStart().toLocalTime();
        LocalTime timeEnd = dateTimeMapper.getDateEnd() == null ? null : dateTimeMapper.getDateEnd().toLocalTime();

        return super.getBetween(dateStart, timeStart, dateEnd, timeEnd);
    }
}