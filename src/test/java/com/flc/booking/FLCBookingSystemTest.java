package test.java.com.flc.booking;

import main.java.com.flc.booking.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class FLCBookingSystemTest {

    @Test
    public void shouldBookLessonSuccessfully() {
        FLCBookingSystem system = new FLCBookingSystem();
        system.addMember(new Member("M1", "Alice", "a@test.com", "123"));
        Lesson lesson = new Lesson("L1", ExerciseType.YOGA, "Saturday", "Morning", 1, 5, 10.0, 4);
        system.getTimetable().addLesson(lesson);

        Booking booking = system.bookLesson("M1", "L1");

        assertNotNull(booking);
        assertEquals("M1", booking.getMember().getMemberId());
    }

    @Test
    public void shouldNotAllowDuplicateBooking() {
        FLCBookingSystem system = new FLCBookingSystem();
        system.addMember(new Member("M1", "Alice", "a@test.com", "123"));
        Lesson lesson = new Lesson("L1", ExerciseType.YOGA, "Saturday", "Morning", 1, 5, 10.0, 4);
        system.getTimetable().addLesson(lesson);

        Booking first = system.bookLesson("M1", "L1");
        Booking second = system.bookLesson("M1", "L1");

        assertNotNull(first);
        assertNull(second);
    }

    @Test
    public void shouldCancelBookingSuccessfully() {
        FLCBookingSystem system = new FLCBookingSystem();
        system.addMember(new Member("M1", "Alice", "a@test.com", "123"));
        Lesson lesson = new Lesson("L1", ExerciseType.YOGA, "Saturday", "Morning", 1, 5, 10.0, 4);
        system.getTimetable().addLesson(lesson);

        Booking booking = system.bookLesson("M1", "L1");
        boolean result = system.cancelBooking(booking.getBookingId());

        assertTrue(result);
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
    }

    @Test
    public void shouldAttendLessonAndStoreReview() {
        FLCBookingSystem system = new FLCBookingSystem();
        system.addMember(new Member("M1", "Alice", "a@test.com", "123"));
        Lesson lesson = new Lesson("L1", ExerciseType.YOGA, "Saturday", "Morning", 1, 5, 10.0, 4);
        system.getTimetable().addLesson(lesson);

        Booking booking = system.bookLesson("M1", "L1");
        boolean result = system.attendLesson(booking.getBookingId(), "Great lesson", 5);

        assertTrue(result);
        assertEquals(BookingStatus.ATTENDED, booking.getStatus());
        assertNotNull(booking.getReview());
        assertEquals(5, booking.getReview().getRating());
    }
}
