package com.messenger.mess.model.dtos;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class TaskDto {
    @NotBlank
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }
}
