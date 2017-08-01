package ru.javawebinar.topjava.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;


import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceImplTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;


    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

//get
    @Test
    public void get() throws Exception {
        MATCHER.assertEquals(sixthUser,service.get(START_SEQ,USER_ID));
    }
    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(START_SEQ,ADMIN_ID);
    }

//delete
    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(START_SEQ,ADMIN_ID);

    }
    @Test
    public void deleteNotFound() throws Exception {
        service.delete(START_SEQ+6,ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(firstAdmin), service.getAll(ADMIN_ID));
    }


//save
    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Breakfest", 890);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal,firstUser,secondUser,thirthUser,forthUser,fifthUser,sixthUser), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void saveNotFound() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        Meal created = service.save(newMeal, USER_ID);
    }

//update
    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(service.get(START_SEQ, USER_ID), ADMIN_ID);
    }

//getAll
    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(firstUser,secondUser,thirthUser,forthUser,fifthUser,sixthUser), service.getAll(USER_ID));
    }
    @Test
    public void getAllAdmin() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(firstAdmin,secondAdmin), service.getAll(ADMIN_ID));
    }

//getAllBetween
    @Test
    public void getAllBetweenDate() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(firstUser,secondUser,thirthUser),
                service.getBetweenDates(LocalDate.of(2015, Month.MAY, 31),LocalDate.of(2015, Month.MAY, 31),USER_ID));
    }
    public void getAllBetweenDateTime() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(firstUser),
                service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 31, 20, 0),
                        LocalDateTime.of(2015, Month.MAY, 31, 20, 0),USER_ID));
    }

}