package main.java.com.flc.booking;

import java.util.ArrayList;
import java.util.List;

public class FLCBookingSystem {
    private final List<Member> members;
    private final List<Booking> bookings;
    private final Timetable timetable;
    private final ReportGenerator reportGenerator;
    private int bookingCounter = 1;
    private int reviewCounter = 1;

    public FLCBookingSystem() {
        members = new ArrayList<>();
        bookings = new ArrayList<>();
        timetable = new Timetable();
        reportGenerator = new ReportGenerator();
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                return member;
            }
        }
        return null;
    }

    public Booking findBookingById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    public boolean hasDuplicateBooking(Member member, Lesson lesson) {
        for (Booking booking : bookings) {
            if (booking.getMember().getMemberId().equalsIgnoreCase(member.getMemberId())
                    && booking.getLesson().getLessonId().equalsIgnoreCase(lesson.getLessonId())
                    && booking.getStatus() != BookingStatus.CANCELLED) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTimeConflict(Member member, Lesson newLesson) {
        for (Booking booking : bookings) {
            if (booking.getMember().getMemberId().equalsIgnoreCase(member.getMemberId())
                    && booking.getStatus() != BookingStatus.CANCELLED) {

                Lesson existing = booking.getLesson();

                boolean sameWeekend = existing.getWeekendNumber() == newLesson.getWeekendNumber();
                boolean sameDay = existing.getDay().equalsIgnoreCase(newLesson.getDay());
                boolean sameTime = existing.getTimeSlot().equalsIgnoreCase(newLesson.getTimeSlot());

                if (sameWeekend && sameDay && sameTime) {
                    return true;
                }
            }
        }
        return false;
    }

    public Booking bookLesson(String memberId, String lessonId) {
        Member member = findMemberById(memberId);
        Lesson lesson = timetable.getLessonById(lessonId);

        if (member == null || lesson == null) {
            return null;
        }

        if (hasDuplicateBooking(member, lesson)) {
            System.out.println("Duplicate booking is not allowed.");
            return null;
        }

        if (hasTimeConflict(member, lesson)) {
            System.out.println("Time conflict detected.");
            return null;
        }

        if (lesson.isFull()) {
            System.out.println("Lesson is full.");
            return null;
        }

        String bookingId = "B" + bookingCounter++;
        Booking booking = new Booking(bookingId, member, lesson);

        if (lesson.addBooking(booking)) {
            bookings.add(booking);
            return booking;
        }

        return null;
    }

    public boolean changeBooking(String bookingId, String newLessonId) {
        Booking booking = findBookingById(bookingId);
        Lesson newLesson = timetable.getLessonById(newLessonId);

        if (booking == null || newLesson == null) {
            return false;
        }

        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.ATTENDED) {
            return false;
        }

        Member member = booking.getMember();

        if (hasDuplicateBooking(member, newLesson)) {
            System.out.println("Duplicate booking is not allowed.");
            return false;
        }

        for (Booking other : bookings) {
            if (!other.getBookingId().equalsIgnoreCase(bookingId)
                    && other.getMember().getMemberId().equalsIgnoreCase(member.getMemberId())
                    && other.getStatus() != BookingStatus.CANCELLED) {

                Lesson existing = other.getLesson();

                boolean sameWeekend = existing.getWeekendNumber() == newLesson.getWeekendNumber();
                boolean sameDay = existing.getDay().equalsIgnoreCase(newLesson.getDay());
                boolean sameTime = existing.getTimeSlot().equalsIgnoreCase(newLesson.getTimeSlot());

                if (sameWeekend && sameDay && sameTime) {
                    System.out.println("Time conflict detected.");
                    return false;
                }
            }
        }

        if (newLesson.isFull()) {
            System.out.println("New lesson is full.");
            return false;
        }

        Lesson oldLesson = booking.getLesson();
        oldLesson.removeBooking(booking);

        booking.setLesson(newLesson);
        booking.setStatus(BookingStatus.CHANGED);
        newLesson.addBooking(booking);

        return true;
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = findBookingById(bookingId);

        if (booking == null) {
            return false;
        }

        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.ATTENDED) {
            return false;
        }

        booking.getLesson().removeBooking(booking);
        booking.setStatus(BookingStatus.CANCELLED);
        return true;
    }

    public boolean attendLesson(String bookingId, String reviewText, int rating) {
        Booking booking = findBookingById(bookingId);

        if (booking == null) {
            return false;
        }

        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.ATTENDED) {
            return false;
        }

        String reviewId = "R" + reviewCounter++;
        Review review = new Review(reviewId, reviewText, rating);
        booking.setReview(review);
        booking.setStatus(BookingStatus.ATTENDED);

        return true;
    }

    public void generateMonthlyLessonReport(int month) {
        reportGenerator.generateMonthlyLessonReport(timetable.getLessonsByMonth(month));
    }

    public void generateMonthlyChampionReport(int month) {
        reportGenerator.generateMonthlyChampionReport(timetable.getLessonsByMonth(month));
    }
}