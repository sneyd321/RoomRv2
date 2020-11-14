package com.sneydr.roomrv2.Entities.Problem;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.sneydr.roomrv2.Entities.RentDetails.CalendarHandler;



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

    @Ignore
    private transient ProblemContext context;


    public Problem(int problemId, String category, String description, String status, String datePosted, String lastUpdated, @Nullable String imageUrl, int houseId) {
        this.problemId = problemId;
        this.category = category;
        this.description = description;
        this.status = status;
        this.datePosted = datePosted;
        this.lastUpdated = lastUpdated;
        this.imageUrl = imageUrl;
        this.houseId = houseId;
        context = new ProblemContext();
        context.setState(this.status);
    }

    @Ignore
    public Problem(String category, String description, int houseId) {
        this.category = category;
        this.description = description;
        this.houseId = houseId;
        this.imageUrl = null;
        this.context = new ProblemContext();
        this.getContext().setState("Reported");
        this.status = getContext().getStatus().getName();
        CalendarHandler calendarHandler = new CalendarHandler();
        this.datePosted = calendarHandler.getNow();
        this.lastUpdated = calendarHandler.getNow();
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getHouseId() {
        return this.houseId;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ProblemContext getContext() {
        ProblemContext context = new ProblemContext();
        context.setState(this.status);
        return context;
    }
}
