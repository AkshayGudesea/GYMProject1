package test.java.com.flc.booking;

import main.java.com.flc.booking.ExerciseType;
import main.java.com.flc.booking.Lesson;
import main.java.com.flc.booking.Timetable;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TimetableTest {

    private Timetable timetable;
    private Lesson lesson1;
    private Lesson lesson2;
    private Lesson lesson3;

    @Before
    public void setUp() {
        timetable = new Timetable();

        lesson1 = new Lesson("L1", ExerciseType.YOGA, "Saturday", "Morning", 1, 5, 10.0, 4);
        lesson2 = new Lesson("L2", ExerciseType.ZUMBA, "Sunday", "Afternoon", 1, 5, 12.0, 4);
        lesson3 = new Lesson("L3", ExerciseType.YOGA, "Saturday", "Evening", 2, 6, 10.0, 4);

        timetable.addLesson(lesson1);
        timetable.addLesson(lesson2);
        timetable.addLesson(lesson3);
    }

    @Test
    public void shouldAddLessonToTimetable() {
        List<Lesson> lessons = timetable.getAllLessons();

        assertEquals(3, lessons.size());
        assertTrue(lessons.contains(lesson1));
        assertTrue(lessons.contains(lesson2));
        assertTrue(lessons.contains(lesson3));
    }

    @Test
    public void shouldReturnLessonById() {
        Lesson foundLesson = timetable.getLessonById("L1");

        assertNotNull(foundLesson);
        assertEquals("L1", foundLesson.getLessonId());
        assertEquals(ExerciseType.YOGA, foundLesson.getExerciseType());
    }

    @Test
    public void shouldReturnNullWhenLessonIdDoesNotExist() {
        Lesson foundLesson = timetable.getLessonById("L99");

        assertNull(foundLesson);
    }

    @Test
    public void shouldReturnLessonsByDay() {
        List<Lesson> saturdayLessons = timetable.getLessonsByDay("Saturday");

        assertEquals(2, saturdayLessons.size());
        assertTrue(saturdayLessons.contains(lesson1));
        assertTrue(saturdayLessons.contains(lesson3));
    }

    @Test
    public void shouldReturnEmptyListWhenNoLessonsForDay() {
        List<Lesson> mondayLessons = timetable.getLessonsByDay("Monday");

        assertNotNull(mondayLessons);
        assertTrue(mondayLessons.isEmpty());
    }

    @Test
    public void shouldReturnLessonsByExerciseType() {
        List<Lesson> yogaLessons = timetable.getLessonsByExerciseType(ExerciseType.YOGA);

        assertEquals(2, yogaLessons.size());
        assertTrue(yogaLessons.contains(lesson1));
        assertTrue(yogaLessons.contains(lesson3));
    }

    @Test
    public void shouldReturnEmptyListWhenNoLessonsForExerciseType() {
        List<Lesson> aquaciseLessons = timetable.getLessonsByExerciseType(ExerciseType.AQUACISE);

        assertNotNull(aquaciseLessons);
        assertTrue(aquaciseLessons.isEmpty());
    }

    @Test
    public void shouldReturnLessonsByMonth() {
        List<Lesson> monthFiveLessons = timetable.getLessonsByMonth(5);

        assertEquals(2, monthFiveLessons.size());
        assertTrue(monthFiveLessons.contains(lesson1));
        assertTrue(monthFiveLessons.contains(lesson2));
    }

    @Test
    public void shouldReturnEmptyListWhenNoLessonsForMonth() {
        List<Lesson> monthTenLessons = timetable.getLessonsByMonth(10);

        assertNotNull(monthTenLessons);
        assertTrue(monthTenLessons.isEmpty());
    }

    @Test
    public void shouldFindLessonByIdIgnoringCase() {
        Lesson foundLesson = timetable.getLessonById("l1");

        assertNotNull(foundLesson);
        assertEquals("L1", foundLesson.getLessonId());
    }

    @Test
    public void shouldFindLessonsByDayIgnoringCase() {
        List<Lesson> lessons = timetable.getLessonsByDay("saturday");

        assertEquals(2, lessons.size());
    }
}
