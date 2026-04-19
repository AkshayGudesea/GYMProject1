package test.java.com.flc.booking;

import main.java.com.flc.booking.ExerciseType;
import main.java.com.flc.booking.FLCBookingSystem;
import main.java.com.flc.booking.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class MainTest {

    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;
    private ByteArrayOutputStream outputStreamCaptor;

    @Before
    public void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void shouldExitProgramWhenInputIsZero() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("===== FLC BOOKING SYSTEM ====="));
        assertTrue(output.contains("Exiting..."));
    }

    @Test
    public void shouldShowInvalidChoiceForWrongMenuOption() {
        String input = "99\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Invalid choice."));
        assertTrue(output.contains("Exiting..."));
    }

    @Test
    public void shouldViewTimetableByDay() {
        String input = "1\nSaturday\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Enter day (Saturday/Sunday): "));
        assertTrue(output.contains("Saturday"));
        assertTrue(output.contains("L"));
    }

    @Test
    public void shouldViewTimetableByExerciseType() {
        String input = "2\nYOGA\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Enter exercise type"));
        assertTrue(output.contains("YOGA"));
    }

    @Test
    public void shouldBookLessonSuccessfullyFromMenu() {
        String input = "3\nM1\nL1\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Enter member ID: "));
        assertTrue(output.contains("Enter lesson ID: "));
        assertTrue(output.contains("Booking successful:"));
    }

    @Test
    public void shouldFailBookingForInvalidMemberOrLesson() {
        String input = "3\nM999\nL999\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Booking failed."));
    }

    @Test
    public void shouldViewAllBookingsAfterBookingOneLesson() {
        String input = "3\nM1\nL1\n9\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Booking successful:"));
        assertTrue(output.contains("B1"));
    }

    @Test
    public void shouldInvokeSeedDataAndPopulateSystem() throws Exception {
        FLCBookingSystem system = new FLCBookingSystem();

        Method seedDataMethod = Main.class.getDeclaredMethod("seedData", FLCBookingSystem.class);
        seedDataMethod.setAccessible(true);
        seedDataMethod.invoke(null, system);

        assertEquals(10, system.getMembers().size());
        assertEquals(48, system.getTimetable().getAllLessons().size());
    }

    @Test
    public void shouldReturnCorrectPriceForYoga() throws Exception {
        Method getPriceMethod = Main.class.getDeclaredMethod(
                "getPrice",
                ExerciseType.class,
                double.class,
                double.class,
                double.class,
                double.class,
                double.class
        );
        getPriceMethod.setAccessible(true);

        double result = (double) getPriceMethod.invoke(
                null,
                ExerciseType.YOGA,
                10.0,
                12.0,
                11.0,
                13.0,
                14.0
        );

        assertEquals(10.0, result, 0.001);
    }

    @Test
    public void shouldReturnCorrectPriceForBoxFit() throws Exception {
        Method getPriceMethod = Main.class.getDeclaredMethod(
                "getPrice",
                ExerciseType.class,
                double.class,
                double.class,
                double.class,
                double.class,
                double.class
        );
        getPriceMethod.setAccessible(true);

        double result = (double) getPriceMethod.invoke(
                null,
                ExerciseType.BOX_FIT,
                10.0,
                12.0,
                11.0,
                13.0,
                14.0
        );

        assertEquals(13.0, result, 0.001);
    }

    @Test
    public void shouldAttendLessonFromMenuSuccessfully() {
        String input = "3\nM1\nL1\n6\nB1\nGreat class\n5\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Booking successful:"));
        assertTrue(output.contains("Lesson attended and review recorded."));
    }

    @Test
    public void shouldCancelBookingFromMenuSuccessfully() {
        String input = "3\nM1\nL1\n5\nB1\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Booking successful:"));
        assertTrue(output.contains("Booking cancelled successfully."));
    }
}
