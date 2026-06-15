package com.gabriele.eventhub.dto;



public class RatingResponseDTO {

    private double averageRating;
    private int totalFeedbacks;

    public RatingResponseDTO(double averageRating, int totalFeedbacks) {
        this.averageRating = averageRating;
        this.totalFeedbacks = totalFeedbacks;
    }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public int getTotalFeedbacks() { return totalFeedbacks; }
    public void setTotalFeedbacks(int totalFeedbacks) { this.totalFeedbacks = totalFeedbacks; }
}