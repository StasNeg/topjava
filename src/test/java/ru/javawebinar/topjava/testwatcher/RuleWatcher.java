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
    public static final String ANSI_RED = "\u001B[31m";
    public  String succesOrFailed;

    private static StringBuilder resultString = new StringBuilder();
    private Logger log;
    private long timeStart;
    private long timeEnd;
    private final static String EOL = System.getProperty("line.separator");
    private String color;
    public RuleWatcher(Class clazz) {
        this.log = LoggerFactory.getLogger(clazz);
    }

    @Override
    protected void starting(Description description) {
        timeStart = new Date().getTime();
    }

    @Override
    protected void succeeded(Description description) {
        super.succeeded(description);
        color = ANSI_CYAN;
        succesOrFailed = "SUCCESSFULLY";
    }

    @Override
    protected void failed(Throwable e, Description description) {
        super.failed(e, description);
        color = ANSI_RED;
        succesOrFailed = "UNSUCCESSFULLY";
    }

    @Override
    protected void finished(Description description) {
        timeEnd = new Date().getTime();
        String withColor = color + "Finished method [{}] - in {} ms" + ANSI_RESET;
        log.debug(withColor, description.getMethodName(), timeEnd - timeStart);
        resultString.append(String.format(color + "[%s] Tested method - Finished %s in {%s} ms" + ANSI_RESET+EOL,
                succesOrFailed,description.getMethodName(), timeEnd - timeStart));

    }

    public StringBuilder getResultString() {
        return resultString;
    }

    public void setResultString(StringBuilder resultString) {
        this.resultString = resultString;
    }
}
