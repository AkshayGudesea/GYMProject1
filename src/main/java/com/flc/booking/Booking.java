package main.java.com.flc.booking;

public class Booking {
    private final String bookingId;
    private final Member member;
    private Lesson lesson;
    private BookingStatus status;
    private Review review;

    public Booking(String bookingId, Member member, Lesson lesson) {
        this.bookingId = bookingId;
        this.member = member;
        this.lesson = lesson;
        this.status = BookingStatus.BOOKED;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Member getMember() {
        return member;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Review getReview() {
        return review;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return bookingId + " | Member: " + member.getName() +
                " | Lesson: " + lesson.getLessonId() +
                " | Status: " + status;
    }
}
