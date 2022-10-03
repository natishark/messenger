package com.messenger.mess.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class TaskDto {
    @NotBlank
    @Size(max = 255, message = "Title must be not bigger than 255 symbols.")
    private String title;
    @Size(max = 5000, message = "Description must be not bigger than 5000 symbols.")
    private String description;
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime finishTime;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }
}
