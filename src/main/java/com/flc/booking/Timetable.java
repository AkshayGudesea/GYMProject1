package main.java.com.flc.booking;

import main.java.com.flc.booking.Lesson;
import main.java.com.flc.booking.ExerciseType;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private final List<Lesson> lessons;

    public Timetable() {
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public List<Lesson> getAllLessons() {
        return lessons;
    }

    public Lesson getLessonById(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equalsIgnoreCase(lessonId)) {
                return lesson;
            }
        }
        return null;
    }

    public List<Lesson> getLessonsByDay(String day) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getDay().equalsIgnoreCase(day)) {
                result.add(lesson);
            }
        }
        return result;
    }

    public List<Lesson> getLessonsByExerciseType(ExerciseType type) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getExerciseType() == type) {
                result.add(lesson);
            }
        }
        return result;
    }

    public List<Lesson> getLessonsByMonth(int month) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getMonth() == month) {
                result.add(lesson);
            }
        }
        return result;
    }
}
