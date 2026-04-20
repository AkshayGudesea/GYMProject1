package main.java.com.flc.booking;


import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FLCBookingSystem system = new FLCBookingSystem();
        seedData(system);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== FLC BOOKING SYSTEM =====");
            System.out.println("1. View timetable by day");
            System.out.println("2. View timetable by exercise type");
            System.out.println("3. Book a lesson");
            System.out.println("4. Change a booking");
            System.out.println("5. Cancel a booking");
            System.out.println("6. Attend a lesson and review");
            System.out.println("7. Monthly lesson report");
            System.out.println("8. Monthly champion exercise report");
            System.out.println("9. View all bookings");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter day (Saturday/Sunday): ");
                    String day = scanner.nextLine();
                    List<Lesson> lessonsByDay = system.getTimetable().getLessonsByDay(day);
                    for (Lesson lesson : lessonsByDay) {
                        System.out.println(lesson);
                    }
                    break;

                case 2:
                    System.out.print("Enter exercise type (YOGA, ZUMBA, AQUACISE, BOX_FIT, BODY_BLITZ): ");
                    ExerciseType type = ExerciseType.valueOf(scanner.nextLine().trim().toUpperCase());
                    List<Lesson> lessonsByType = system.getTimetable().getLessonsByExerciseType(type);
                    for (Lesson lesson : lessonsByType) {
                        System.out.println(lesson);
                    }
                    break;

                case 3:
                    System.out.print("Enter member ID: ");
                    String memberId = scanner.nextLine();
                    System.out.print("Enter lesson ID: ");
                    String lessonId = scanner.nextLine();

                    Booking booking = system.bookLesson(memberId, lessonId);
                    if (booking != null) {
                        System.out.println("Booking successful: " + booking);
                    } else {
                        System.out.println("Booking failed.");
                    }
                    break;

                case 4:
                    System.out.print("Enter booking ID: ");
                    String bookingId = scanner.nextLine();
                    System.out.print("Enter new lesson ID: ");
                    String newLessonId = scanner.nextLine();

                    if (system.changeBooking(bookingId, newLessonId)) {
                        System.out.println("Booking changed successfully.");
                    } else {
                        System.out.println("Booking change failed.");
                    }
                    break;

                case 5:
                    System.out.print("Enter booking ID: ");
                    String cancelBookingId = scanner.nextLine();

                    if (system.cancelBooking(cancelBookingId)) {
                        System.out.println("Booking cancelled successfully.");
                    } else {
                        System.out.println("Cancellation failed.");
                    }
                    break;

                case 6:
                    System.out.print("Enter booking ID: ");
                    String attendBookingId = scanner.nextLine();
                    System.out.print("Enter review text: ");
                    String reviewText = scanner.nextLine();
                    System.out.print("Enter rating (1-5): ");
                    int rating = Integer.parseInt(scanner.nextLine());

                    if (system.attendLesson(attendBookingId, reviewText, rating)) {
                        System.out.println("Lesson attended and review recorded.");
                    } else {
                        System.out.println("Attend action failed.");
                    }
                    break;

                case 7:
                    System.out.print("Enter month number: ");
                    int reportMonth = Integer.parseInt(scanner.nextLine());
                    system.generateMonthlyLessonReport(reportMonth);
                    break;

                case 8:
                    System.out.print("Enter month number: ");
                    int championMonth = Integer.parseInt(scanner.nextLine());
                    system.generateMonthlyChampionReport(championMonth);
                    break;

                case 9:
                    for (Booking b : system.getBookings()) {
                        System.out.println(b);
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void seedData(FLCBookingSystem system) {
        system.addMember(new Member("M1", "Alice", "alice@test.com", "111111"));
        system.addMember(new Member("M2", "Bob", "bob@test.com", "222222"));
        system.addMember(new Member("M3", "Charlie", "charlie@test.com", "333333"));
        system.addMember(new Member("M4", "Diana", "diana@test.com", "444444"));
        system.addMember(new Member("M5", "Eva", "eva@test.com", "555555"));
        system.addMember(new Member("M6", "Frank", "frank@test.com", "666666"));
        system.addMember(new Member("M7", "Grace", "grace@test.com", "777777"));
        system.addMember(new Member("M8", "Henry", "henry@test.com", "888888"));
        system.addMember(new Member("M9", "Isla", "isla@test.com", "999999"));
        system.addMember(new Member("M10", "Jack", "jack@test.com", "101010"));

        ExerciseType[] types = {
                ExerciseType.YOGA,
                ExerciseType.ZUMBA,
                ExerciseType.AQUACISE,
                ExerciseType.BOX_FIT,
                ExerciseType.BODY_BLITZ
        };

        double yogaPrice = 10.0;
        double zumbaPrice = 12.0;
        double aquacisePrice = 11.0;
        double boxFitPrice = 13.0;
        double bodyBlitzPrice = 14.0;

        int lessonCounter = 1;

        for (int weekend = 1; weekend <= 8; weekend++) {
            int month = (weekend <= 4) ? 5 : 6;

            system.getTimetable().addLesson(new Lesson(
                    "L" + lessonCounter++, types[(weekend - 1) % types.length], "Saturday", "Morning", weekend, month,
                    getPrice(types[(weekend - 1) % types.length], yogaPrice, zumbaPrice, aquacisePrice, boxFitPrice, bodyBlitzPrice), 4));

            system.getTimetable().addLesson(new Lesson(
                    "L" + lessonCounter++, types[(weekend) % types.length], "Saturday", "Afternoon", weekend, month,
                    getPrice(types[(weekend) % types.length], yogaPrice, zumbaPrice, aquacisePrice, boxFitPrice, bodyBlitzPrice), 4));

            system.getTimetable().addLesson(new Lesson(
                    "L" + lessonCounter++, types[(weekend + 1) % types.length], "Saturday", "Evening", weekend, month,
                    getPrice(types[(weekend + 1) % types.length], yogaPrice, zumbaPrice, aquacisePrice, boxFitPrice, bodyBlitzPrice), 4));

            ExerciseType type = types[(weekend + 2) % types.length];
            system.getTimetable().addLesson(new Lesson(
                    "L" + lessonCounter++, type, "Sunday", "Morning", weekend, month,
                    getPrice(type, yogaPrice, zumbaPrice, aquacisePrice, boxFitPrice, bodyBlitzPrice), 4));

            ExerciseType type1 = types[(weekend + 3) % types.length];
            system.getTimetable().addLesson(new Lesson(
                    "L" + lessonCounter++, type1, "Sunday", "Afternoon", weekend, month,
                    getPrice(type1, yogaPrice, zumbaPrice, aquacisePrice, boxFitPrice, bodyBlitzPrice), 4));

            ExerciseType type2 = types[(weekend + 4) % types.length];
            system.getTimetable().addLesson(new Lesson(
                    "L" + lessonCounter++, type2, "Sunday", "Evening", weekend, month,
                    getPrice(type2, yogaPrice, zumbaPrice, aquacisePrice, boxFitPrice, bodyBlitzPrice), 4));
        }
    }

    private static double getPrice(ExerciseType type, double yogaPrice, double zumbaPrice,
                                   double aquacisePrice, double boxFitPrice, double bodyBlitzPrice) {
        return switch (type) {
            case YOGA -> yogaPrice;
            case ZUMBA -> zumbaPrice;
            case AQUACISE -> aquacisePrice;
            case BOX_FIT -> boxFitPrice;
            case BODY_BLITZ -> bodyBlitzPrice;
            default -> 10.0;
        };
    }
}