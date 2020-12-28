package com.sneydr.roomrv2.Entities.Problem;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sneydr.roomrv2.Entities.House.RentDetails.CalendarHandler;



@Entity(tableName = "problem_table")
public class Problem {


    @PrimaryKey
    private int problemId;
    private String category;
    private String description;
    private String status;
    private String datePosted;
    private String lastUpdated;
    @Nullable
    private String imageUrl;
    private int houseId;



    public Problem(String category, String description, int houseId) {
        this.category = category;
        this.description = description;
        this.houseId = houseId;
        this.imageUrl = null;
        this.status = "Reported";
        CalendarHandler calendarHandler = new CalendarHandler();
        this.datePosted = calendarHandler.getNow();
        this.lastUpdated = calendarHandler.getNow();
    }

    public Problem(String category, String description, int houseId, int problemId, String status) {
        this.problemId = problemId;
        this.category = category;
        this.description = description;
        this.houseId = houseId;
        this.imageUrl = null;
        this.status = status;
        CalendarHandler calendarHandler = new CalendarHandler();
        this.datePosted = calendarHandler.getNow();
        this.lastUpdated = calendarHandler.getNow();
    }

    public int getProblemId() {
        return problemId;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public ProblemStatus getStatus() {
        ProblemContext context = new ProblemContext();
        context.setState(this.status);
        return context.getStatus();
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getHouseId() {
        return this.houseId;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

}
