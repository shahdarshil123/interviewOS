package com.example.interview_os.dto;

import com.example.interview_os.enums.StoryCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StoryRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Situation is required")
    private String situation;

    @NotBlank(message = "Task is required")
    private String task;

    @NotBlank(message = "Action is required")
    private String action;

    @NotBlank(message = "Result is required")
    private String result;

    @NotNull(message = "Category is required")
    private StoryCategory category;

    private String tags;

    // generate getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public StoryCategory getCategory() {
        return category;
    }

    public void setCategory(StoryCategory category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}