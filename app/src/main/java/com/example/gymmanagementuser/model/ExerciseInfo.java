package com.example.gymmanagementuser.model;

public class ExerciseInfo {
    private String bodyType, daysOfWeek, exerciseDetails,exerciseId,exerciseKey, exerciseName, imageLink1, imageLink2, imageLink3, reps, set, totalDaysOfWeek, videoLink;

    public ExerciseInfo() {
    }

    public ExerciseInfo(String bodyType, String daysOfWeek, String exerciseDetails, String exerciseId
            ,String exerciseKey, String exerciseName, String imageLink1, String imageLink2, String imageLink3, String reps
            , String set, String totalDaysOfWeek, String videoLink) {
        this.bodyType = bodyType;
        this.daysOfWeek = daysOfWeek;
        this.exerciseDetails = exerciseDetails;
        this.exerciseId=exerciseId;
        this.exerciseKey=exerciseKey;
        this.exerciseName = exerciseName;
        this.imageLink1 = imageLink1;
        this.imageLink2 = imageLink2;
        this.imageLink3 = imageLink3;
        this.reps = reps;
        this.set = set;
        this.totalDaysOfWeek = totalDaysOfWeek;
        this.videoLink = videoLink;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getExerciseDetails() {
        return exerciseDetails;
    }

    public void setExerciseDetails(String exerciseDetails) {
        this.exerciseDetails = exerciseDetails;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseKey() {
        return exerciseKey;
    }

    public void setExerciseKey(String exerciseKey) {
        this.exerciseKey = exerciseKey;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getImageLink1() {
        return imageLink1;
    }

    public void setImageLink1(String imageLink1) {
        this.imageLink1 = imageLink1;
    }

    public String getImageLink2() {
        return imageLink2;
    }

    public void setImageLink2(String imageLink2) {
        this.imageLink2 = imageLink2;
    }

    public String getImageLink3() {
        return imageLink3;
    }

    public void setImageLink3(String imageLink3) {
        this.imageLink3 = imageLink3;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getTotalDaysOfWeek() {
        return totalDaysOfWeek;
    }

    public void setTotalDaysOfWeek(String totalDaysOfWeek) {
        this.totalDaysOfWeek = totalDaysOfWeek;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
