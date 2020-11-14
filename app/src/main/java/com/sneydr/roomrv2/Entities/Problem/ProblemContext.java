package com.sneydr.roomrv2.Entities.Problem;

public class ProblemContext {

    private ProblemStatus problemStatus;

    ProblemContext() {
        this.problemStatus = null;
    }

    public void setState(String status) {
        switch (status) {
            case "Reported":
                this.problemStatus = new ReportedStatus();
                break;
            case "Viewed":
                this.problemStatus = new ViewedStatus();
                break;
            case "In Progress":
                this.problemStatus = new InProgressStatus();
                break;
            case "Completed":
                this.problemStatus = new CompletedStatus();
                break;
        }
    }

    public ProblemStatus getStatus() {
        return this.problemStatus;
    }





}
