package ru.javawebinar.topjava.to;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Created by Stanislav on 02.09.2017.
 */

public class DateTimeMapper {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dateStart;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dateEnd;

    public DateTimeMapper(LocalDateTime dateStart, LocalDateTime dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public DateTimeMapper() {
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }
}
