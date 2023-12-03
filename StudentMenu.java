import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private Scanner scanner;
    private Student loggedInStudent;

    public StudentMenu(Scanner scanner, Student loggedInStudent) {
        this.scanner = scanner;
        this.loggedInStudent = loggedInStudent;
    }

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

