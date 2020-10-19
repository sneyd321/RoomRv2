package com.sneydr.roomrv2.Entities.Problem;

import android.graphics.Bitmap;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import javax.annotation.Nullable;

import kotlin.jvm.Transient;

public class Problem {


    @PrimaryKey
    private int problemId;
    private String category;
    private String description;
    private String status;
    @Nullable
    private String imageUrl;
    private int houseId;


    @Ignore
    private transient ProblemContext context;


    public Problem(int problemId, String category, String description, String status, @Nullable String imageUrl, int houseId) {
        this.problemId = problemId;
        this.category = category;
        this.description = description;
        this.status = status;
        this.imageUrl = imageUrl;
        this.houseId = houseId;
        context = new ProblemContext();
    }

    @Ignore
    public Problem(String category, String description, int houseId) {
        this.category = category;
        this.description = description;
        this.houseId = houseId;
        this.imageUrl = null;
        this.context = new ProblemContext();
        this.context.setState(new ReportedStatus());
        this.status = context.getStatus().getName();
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


    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
