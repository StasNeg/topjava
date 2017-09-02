package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.to.DateTimeMapper;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static java.time.LocalDateTime.of;
import java.time.Month;
import java.util.Arrays;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.javawebinar.topjava.MealWithExceededTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/rest/meals/";



    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHERWITHEXSEED.contentListMatcher(MEALEXSEED6, MEALEXSEED5,MEALEXSEED4,MEALEXSEED3,MEALEXSEED2,MEALEXSEED1)));
    }

    @Test
    public void testGetBeetween() throws Exception {
        DateTimeMapper filter = new DateTimeMapper(of(2015, Month.MAY, 30, 13, 0),of(2015, Month.MAY, 31, 13, 0));
        ResultActions action = mockMvc.perform(post(REST_URL+"filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(filter)))
                .andExpect(status().isOk());

        List<MealWithExceed> returned = MATCHERWITHEXSEED.fromJsonActions(action);
        System.out.println(returned);

        MATCHERWITHEXSEED.assertListEquals(Arrays.asList(MEALEXSEED5,MEALEXSEED2), returned);
    }

}
