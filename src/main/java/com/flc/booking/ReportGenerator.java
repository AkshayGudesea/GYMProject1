package main.java.com.flc.booking;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {

    public void generateMonthlyLessonReport(List<Lesson> lessons) {
        System.out.println("\n===== MONTHLY LESSON REPORT =====");
        for (Lesson lesson : lessons) {
            System.out.printf(
                    "%s | %s | %s | Attended: %d | Avg Rating: %.2f%n",
                    lesson.getLessonId(),
                    lesson.getExerciseType(),
                    lesson.getDay() + " " + lesson.getTimeSlot(),
                    lesson.getAttendedCount(),
                    lesson.getAverageRating()
            );
        }
    }

    public void generateMonthlyChampionReport(List<Lesson> lessons) {
        System.out.println("\n===== MONTHLY CHAMPION EXERCISE REPORT =====");

        Map<ExerciseType, Double> incomeMap = new HashMap<>();

        for (Lesson lesson : lessons) {
            incomeMap.put(
                    lesson.getExerciseType(),
                    incomeMap.getOrDefault(lesson.getExerciseType(), 0.0) + lesson.getIncome()
            );
        }

        ExerciseType champion = null;
        double maxIncome = -1;

        for (Map.Entry<ExerciseType, Double> entry : incomeMap.entrySet()) {
            System.out.printf("%s -> £%.2f%n", entry.getKey(), entry.getValue());

            if (entry.getValue() > maxIncome) {
                maxIncome = entry.getValue();
                champion = entry.getKey();
            }
        }

        if (champion != null) {
            System.out.println("Champion Exercise Type: " + champion + " (£" + maxIncome + ")");
        }
    }
}