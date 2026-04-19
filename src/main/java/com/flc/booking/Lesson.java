package main.java.com.flc.booking;


import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private final String lessonId;
    private final ExerciseType exerciseType;
    private final String day;
    private final String timeSlot;
    private final int weekendNumber;
    private final int month;
    private final double price;
    private final int capacity;
    private final List<Booking> bookings;

    public Lesson(String lessonId, ExerciseType exerciseType, String day, String timeSlot,
                  int weekendNumber, int month, double price, int capacity) {
        this.lessonId = lessonId;
        this.exerciseType = exerciseType;
        this.day = day;
        this.timeSlot = timeSlot;
        this.weekendNumber = weekendNumber;
        this.month = month;
        this.price = price;
        this.capacity = capacity;
        this.bookings = new ArrayList<>();
    }

    public String getLessonId() {
        return lessonId;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public String getDay() {
        return day;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public int getWeekendNumber() {
        return weekendNumber;
    }

    public int getMonth() {
        return month;
    }

    public boolean isFull() {
        int activeCount = 0;
        for (Booking booking : bookings) {
            if (booking.getStatus() != BookingStatus.CANCELLED) {
                activeCount++;
            }
        }
        return activeCount >= capacity;
    }

    public boolean addBooking(Booking booking) {
        if (isFull()) {
            return false;
        }
        bookings.add(booking);
        return true;
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    public int getAttendedCount() {
        int count = 0;
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.ATTENDED) {
                count++;
            }
        }
        return count;
    }

    public double getAverageRating() {
        int total = 0;
        int count = 0;

        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.ATTENDED && booking.getReview() != null) {
                total += booking.getReview().getRating();
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }
        return (double) total / count;
    }

    public double getIncome() {
        int attended = 0;
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.ATTENDED) {
                attended++;
            }
        }
        return attended * price;
    }

    @Override
    public String toString() {
        return lessonId + " | " + exerciseType + " | " + day + " | " + timeSlot +
                " | Weekend " + weekendNumber + " | Month " + month +
                " | £" + price;
    }
}
