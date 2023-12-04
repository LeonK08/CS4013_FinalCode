import java.util.List;
import java.util.Scanner;
/**
 * Represents the student menu in the UL records system.
 * This class provides functionality for students to interact with their academic records and
 * view their transcript.
 *  */

public class StudentMenu {
    private Scanner scanner;
    private Student loggedInStudent;

    /**
     * Constructs a new StudentMenu with a given scanner and a logged-in student.
     *
     * @param scanner The scanner object to read input from the user.
     * @param loggedInStudent The currently logged-in student.
     */

    public StudentMenu(Scanner scanner, Student loggedInStudent) {
        this.scanner = scanner;
        this.loggedInStudent = loggedInStudent;
    }

    /**
     * Displays the student menu and handles user interactions.
     * Allows students to view their transcript or exit to the main menu.
     */

    public void showMenu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Student Menu");
            System.out.println("1. View Transcript");
            System.out.println("2. Exit to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Transcript for Student ID " + loggedInStudent.getStudentId() + ":");
                    List<Result> results = loggedInStudent.getResults();
                    for (Result result : results) {
                        Module module = result.getModule();
                        System.out.println("Module: " + module.getCode());
                        System.out.println("Grade: " + result.getGrade());
                        System.out.println("Year: " + result.getYear());
                        System.out.println("Semester: " + result.getSemester());
                        System.out.println("QCA = " + result.calculateResultQCA() );
                    }
                    break;
                case 2:
                    isRunning = false;
                    System.out.println("Exiting Student Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}



