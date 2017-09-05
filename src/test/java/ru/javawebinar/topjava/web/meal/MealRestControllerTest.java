package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.DateTimeMapper;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static java.time.LocalDateTime.of;
import java.time.Month;
import java.util.Arrays;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealWithExceededTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/rest/meals/";

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXSEED.contentListMatcher(MEAL_EXSEED6, MEAL_EXSEED5, MEAL_EXSEED4, MEAL_EXSEED3, MEAL_EXSEED2, MEAL_EXSEED1)));
    }

    @Test
    public void testGetBeetween() throws Exception {
        DateTimeMapper filter = new DateTimeMapper(of(2015, Month.MAY, 30, 13, 0),of(2015, Month.MAY, 31, 13, 0));
        ResultActions action = mockMvc.perform(post(REST_URL+"filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(filter)))
                .andExpect(status().isOk());

        List<MealWithExceed> returned = MATCHER_WITH_EXSEED.fromJsonActions(action);
        System.out.println(returned);
        MATCHER_WITH_EXSEED.assertListEquals(Arrays.asList(MEAL_EXSEED5, MEAL_EXSEED2), returned);
    }

    @Test
    public void testGetBeetweenNull() throws Exception {
        DateTimeMapper filter = new DateTimeMapper();
        ResultActions action = mockMvc.perform(post(REST_URL+"filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(filter)))
                .andExpect(status().isOk());
        List<MealWithExceed> returned = MATCHER_WITH_EXSEED.fromJsonActions(action);
        System.out.println(returned);
        MATCHER_WITH_EXSEED.assertListEquals(Arrays.asList(MEAL_EXSEED6, MEAL_EXSEED5, MEAL_EXSEED4, MEAL_EXSEED3, MEAL_EXSEED2, MEAL_EXSEED1), returned);
    }

    @Test
    public void testGetBeetweenWithAttributes() throws Exception {
        DateTimeMapper filter = new DateTimeMapper();
        ResultActions action = mockMvc.perform(post(REST_URL + "filterWithAttributes")
                .contentType(MediaType.ALL_VALUE)
                .param("dateStart","2015-05-30T13:00:00")
                .param("dateEnd","2017-05-30T13:00:00"))
                .andExpect(status().isOk());
        List<MealWithExceed> returned = MATCHER_WITH_EXSEED.fromJsonActions(action);
        System.out.println(returned);
        MATCHER_WITH_EXSEED.assertListEquals(Arrays.asList(MEAL_EXSEED5, MEAL_EXSEED2), returned);
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(of(2016, Month.MAY, 30, 13, 0), "Завтрак", 700);
        ResultActions action = mockMvc.perform(post(REST_URL + "create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());
        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        MATCHER.assertEquals(expected, returned);
        MATCHER.assertListEquals(Arrays.asList(expected, MEAL6, MEAL5,MEAL4,MEAL3,MEAL2,MEAL1), mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal expected = new Meal(of(2016, Month.MAY, 30, 13, 0), "Завтрак", 700);
        expected.setId(MealTestData.MEAL1_ID);
        ResultActions action = mockMvc.perform(put(REST_URL + "update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());
        Meal returned = MATCHER.fromJsonAction(action);
        MATCHER.assertEquals(expected, returned);
        MATCHER.assertListEquals(Arrays.asList(expected, MEAL6, MEAL5,MEAL4,MEAL3,MEAL2),  mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MealTestData.MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertListEquals(Arrays.asList(MEAL6, MEAL5,MEAL4,MEAL3,MEAL2), mealService.getAll(AuthorizedUser.id()));
    }
}
