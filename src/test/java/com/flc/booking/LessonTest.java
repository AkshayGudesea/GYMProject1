package test.java.com.flc.booking;

import main.java.com.flc.booking.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LessonTest {

    private Lesson lesson;
    private Member member1;
    private Member member2;
    private Member member3;
    private Member member4;
    private Member member5;

    @Before
    public void setUp() {
        lesson = new Lesson("L1", ExerciseType.YOGA, "Saturday", "Morning", 1, 5, 10.0, 4);

        member1 = new Member("M1", "Alice", "alice@test.com", "111111");
        member2 = new Member("M2", "Bob", "bob@test.com", "222222");
        member3 = new Member("M3", "Charlie", "charlie@test.com", "333333");
        member4 = new Member("M4", "Diana", "diana@test.com", "444444");
        member5 = new Member("M5", "Eva", "eva@test.com", "555555");
    }

    @Test
    public void shouldAddBookingWhenLessonIsNotFull() {
        Booking booking = new Booking("B1", member1, lesson);

        boolean result = lesson.addBooking(booking);

        assertTrue(result);
        assertFalse(lesson.isFull());
    }

    @Test
    public void shouldReturnTrueWhenLessonIsFull() {
        Booking b1 = new Booking("B1", member1, lesson);
        Booking b2 = new Booking("B2", member2, lesson);
        Booking b3 = new Booking("B3", member3, lesson);
        Booking b4 = new Booking("B4", member4, lesson);

        lesson.addBooking(b1);
        lesson.addBooking(b2);
        lesson.addBooking(b3);
        lesson.addBooking(b4);

        assertTrue(lesson.isFull());
    }

    @Test
    public void shouldNotAddBookingWhenLessonIsFull() {
        Booking b1 = new Booking("B1", member1, lesson);
        Booking b2 = new Booking("B2", member2, lesson);
        Booking b3 = new Booking("B3", member3, lesson);
        Booking b4 = new Booking("B4", member4, lesson);
        Booking b5 = new Booking("B5", member5, lesson);

        lesson.addBooking(b1);
        lesson.addBooking(b2);
        lesson.addBooking(b3);
        lesson.addBooking(b4);

        boolean result = lesson.addBooking(b5);

        assertFalse(result);
    }

    @Test
    public void shouldNotCountCancelledBookingWhenCheckingFull() {
        Booking b1 = new Booking("B1", member1, lesson);
        Booking b2 = new Booking("B2", member2, lesson);
        Booking b3 = new Booking("B3", member3, lesson);
        Booking b4 = new Booking("B4", member4, lesson);

        b4.setStatus(BookingStatus.CANCELLED);

        lesson.addBooking(b1);
        lesson.addBooking(b2);
        lesson.addBooking(b3);
        lesson.addBooking(b4);

        assertFalse(lesson.isFull());
    }

    @Test
    public void shouldRemoveBookingSuccessfully() {
        Booking booking = new Booking("B1", member1, lesson);
        lesson.addBooking(booking);

        lesson.removeBooking(booking);

        assertEquals(0, lesson.getAttendedCount());
        assertFalse(lesson.isFull());
    }

    @Test
    public void shouldReturnCorrectAttendedCount() {
        Booking b1 = new Booking("B1", member1, lesson);
        Booking b2 = new Booking("B2", member2, lesson);
        Booking b3 = new Booking("B3", member3, lesson);

        b1.setStatus(BookingStatus.ATTENDED);
        b2.setStatus(BookingStatus.ATTENDED);
        b3.setStatus(BookingStatus.BOOKED);

        lesson.addBooking(b1);
        lesson.addBooking(b2);
        lesson.addBooking(b3);

        assertEquals(2, lesson.getAttendedCount());
    }

    @Test
    public void shouldReturnZeroAverageRatingWhenNoAttendedReviewsExist() {
        Booking booking = new Booking("B1", member1, lesson);
        booking.setStatus(BookingStatus.BOOKED);
        lesson.addBooking(booking);

        assertEquals(0.0, lesson.getAverageRating(), 0.001);
    }

    @Test
    public void shouldCalculateAverageRatingCorrectly() {
        Booking b1 = new Booking("B1", member1, lesson);
        Booking b2 = new Booking("B2", member2, lesson);

        b1.setStatus(BookingStatus.ATTENDED);
        b2.setStatus(BookingStatus.ATTENDED);

        b1.setReview(new Review("R1", "Good", 4));
        b2.setReview(new Review("R2", "Excellent", 5));

        lesson.addBooking(b1);
        lesson.addBooking(b2);

        assertEquals(4.5, lesson.getAverageRating(), 0.001);
    }

    @Test
    public void shouldIgnoreAttendedBookingWithoutReviewInAverageRating() {
        Booking b1 = new Booking("B1", member1, lesson);
        Booking b2 = new Booking("B2", member2, lesson);

        b1.setStatus(BookingStatus.ATTENDED);
        b2.setStatus(BookingStatus.ATTENDED);

        b1.setReview(new Review("R1", "Nice", 4));
        // b2 has no review

        lesson.addBooking(b1);
        lesson.addBooking(b2);

        assertEquals(4.0, lesson.getAverageRating(), 0.001);
    }

    @Test
    public void shouldCalculateIncomeBasedOnAttendedBookingsOnly() {
        Booking b1 = new Booking("B1", member1, lesson);
        Booking b2 = new Booking("B2", member2, lesson);
        Booking b3 = new Booking("B3", member3, lesson);

        b1.setStatus(BookingStatus.ATTENDED);
        b2.setStatus(BookingStatus.ATTENDED);
        b3.setStatus(BookingStatus.BOOKED);

        lesson.addBooking(b1);
        lesson.addBooking(b2);
        lesson.addBooking(b3);

        assertEquals(20.0, lesson.getIncome(), 0.001);
    }

    @Test
    public void shouldReturnCorrectLessonDetailsInToString() {
        String result = lesson.toString();

        assertTrue(result.contains("L1"));
        assertTrue(result.contains("YOGA"));
        assertTrue(result.contains("Saturday"));
        assertTrue(result.contains("Morning"));
        assertTrue(result.contains("Weekend 1"));
        assertTrue(result.contains("Month 5"));
        assertTrue(result.contains("£10.0"));
    }
}
