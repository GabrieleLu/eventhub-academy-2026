package com.gabriele.eventhub.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FeedbackRequestDTO {

    @Min(value = 1, message = "Il voto deve essere almeno 1")
    @Max(value = 5, message = "Il voto non può superare 5")
    private int rating;

    @NotBlank(message = "Il commento è obbligatorio")
    @Size(min = 5, max = 1000, message = "Il commento deve essere tra 5 e 1000 caratteri")
    private String comment;

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}