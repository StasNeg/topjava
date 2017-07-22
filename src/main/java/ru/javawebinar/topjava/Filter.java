package ru.javawebinar.topjava;

import java.time.LocalDate;
import java.time.LocalTime;


public class Filter {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public Filter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Filter() {
        init();
    }

    public void init(){
        startDate = null;
        endDate = null;
        startTime = null;
        endTime = null;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
