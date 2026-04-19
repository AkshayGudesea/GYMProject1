package test.java.com.flc.booking;

import main.java.com.flc.booking.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ReportGeneratorTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void shouldGenerateMonthlyLessonReportWithLessonDetails() {
        ReportGenerator reportGenerator = new ReportGenerator();
        List<Lesson> lessons = new ArrayList<>();

        Lesson lesson = new Lesson("L1", ExerciseType.YOGA, "Saturday", "Morning", 1, 5, 10.0, 4);

        Member member1 = new Member("M1", "Alice", "alice@test.com", "111111");
        Member member2 = new Member("M2", "Bob", "bob@test.com", "222222");

        Booking booking1 = new Booking("B1", member1, lesson);
        booking1.setStatus(BookingStatus.ATTENDED);
        booking1.setReview(new Review("R1", "Good lesson", 4));

        Booking booking2 = new Booking("B2", member2, lesson);
        booking2.setStatus(BookingStatus.ATTENDED);
        booking2.setReview(new Review("R2", "Excellent", 5));

        lesson.addBooking(booking1);
        lesson.addBooking(booking2);

        lessons.add(lesson);

        reportGenerator.generateMonthlyLessonReport(lessons);

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("===== MONTHLY LESSON REPORT ====="));
        assertTrue(output.contains("L1"));
        assertTrue(output.contains("YOGA"));
        assertTrue(output.contains("Saturday Morning"));
        assertTrue(output.contains("Attended: 2"));
        assertTrue(output.contains("Avg Rating: 4.50"));
    }

    @Test
    public void shouldGenerateChampionReportWithCorrectChampionExercise() {
        ReportGenerator reportGenerator = new ReportGenerator();
        List<Lesson> lessons = new ArrayList<>();

        Lesson yogaLesson = new Lesson("L1", ExerciseType.YOGA, "Saturday", "Morning", 1, 5, 10.0, 4);
        Lesson zumbaLesson = new Lesson("L2", ExerciseType.ZUMBA, "Sunday", "Evening", 1, 5, 20.0, 4);

        Member member1 = new Member("M1", "Alice", "alice@test.com", "111111");
        Member member2 = new Member("M2", "Bob", "bob@test.com", "222222");
        Member member3 = new Member("M3", "Charlie", "charlie@test.com", "333333");

        Booking booking1 = new Booking("B1", member1, yogaLesson);
        booking1.setStatus(BookingStatus.ATTENDED);

        Booking booking2 = new Booking("B2", member2, yogaLesson);
        booking2.setStatus(BookingStatus.ATTENDED);

        Booking booking3 = new Booking("B3", member3, zumbaLesson);
        booking3.setStatus(BookingStatus.ATTENDED);

        yogaLesson.addBooking(booking1);
        yogaLesson.addBooking(booking2);
        zumbaLesson.addBooking(booking3);

        lessons.add(yogaLesson);
        lessons.add(zumbaLesson);

        reportGenerator.generateMonthlyChampionReport(lessons);

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("===== MONTHLY CHAMPION EXERCISE REPORT ====="));
        assertTrue(output.contains("YOGA -> £20.00"));
        assertTrue(output.contains("ZUMBA -> £20.00"));
        assertTrue(output.contains("Champion Exercise Type:"));
    }

    @Test
    public void shouldShowHighestIncomeExerciseTypeWhenOneExerciseHasMoreIncome() {
        ReportGenerator reportGenerator = new ReportGenerator();
        List<Lesson> lessons = new ArrayList<>();

        Lesson yogaLesson = new Lesson("L1", ExerciseType.YOGA, "Saturday", "Morning", 1, 5, 10.0, 4);
        Lesson boxFitLesson = new Lesson("L2", ExerciseType.BOX_FIT, "Sunday", "Afternoon", 1, 5, 15.0, 4);

        Member member1 = new Member("M1", "Alice", "alice@test.com", "111111");
        Member member2 = new Member("M2", "Bob", "bob@test.com", "222222");
        Member member3 = new Member("M3", "Chris", "chris@test.com", "333333");

        Booking yogaBooking = new Booking("B1", member1, yogaLesson);
        yogaBooking.setStatus(BookingStatus.ATTENDED);

        Booking boxBooking1 = new Booking("B2", member2, boxFitLesson);
        boxBooking1.setStatus(BookingStatus.ATTENDED);

        Booking boxBooking2 = new Booking("B3", member3, boxFitLesson);
        boxBooking2.setStatus(BookingStatus.ATTENDED);

        yogaLesson.addBooking(yogaBooking);
        boxFitLesson.addBooking(boxBooking1);
        boxFitLesson.addBooking(boxBooking2);

        lessons.add(yogaLesson);
        lessons.add(boxFitLesson);

        reportGenerator.generateMonthlyChampionReport(lessons);

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("YOGA -> £10.00"));
        assertTrue(output.contains("BOX_FIT -> £30.00"));
        assertTrue(output.contains("Champion Exercise Type: BOX_FIT (£30.0)"));
    }

    @Test
    public void shouldGenerateLessonReportEvenWhenNoLessonsExist() {
        ReportGenerator reportGenerator = new ReportGenerator();
        List<Lesson> lessons = new ArrayList<>();

        reportGenerator.generateMonthlyLessonReport(lessons);

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("===== MONTHLY LESSON REPORT ====="));
    }

    @Test
    public void shouldGenerateChampionReportEvenWhenNoLessonsExist() {
        ReportGenerator reportGenerator = new ReportGenerator();
        List<Lesson> lessons = new ArrayList<>();

        reportGenerator.generateMonthlyChampionReport(lessons);

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("===== MONTHLY CHAMPION EXERCISE REPORT ====="));
    }
}
