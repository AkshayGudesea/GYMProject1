package main.java.com.flc.booking;

public class Review {
    private final String reviewId;
    private final String comment;
    private final int rating;

    public Review(String reviewId, String comment, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.reviewId = reviewId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Rating: " + rating + ", Review: " + comment;
    }
}
