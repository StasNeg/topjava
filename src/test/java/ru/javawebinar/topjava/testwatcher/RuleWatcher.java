package ru.javawebinar.topjava.testwatcher;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Stanislav on 05.08.2017.
 */
public class RuleWatcher extends TestWatcher {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private Logger log;
    private long timeStart;
    private long timeEnd;

    public RuleWatcher(Class clazz) {
        this.log = LoggerFactory.getLogger(clazz);
    }

    @Override
    protected void starting(Description description) {
        timeStart = new Date().getTime();
    }

    @Override
    protected void finished(Description description) {
        timeEnd = new Date().getTime();
        String withColor = ANSI_CYAN + "Finished method [{}] - in {} ms" + ANSI_RESET;
        log.debug(withColor, description.getMethodName(), timeEnd - timeStart);
    }

}
