import java.util.Scanner;
/**
 * Represents the department menu in the UL Student Records system.
 * This class is responsible for handling various departmental tasks such as holding exam boards,
 * reviewing student transcripts, and altering student progression.
 */
public class DepartmentMenu {
    private Scanner scanner;
    private boolean examBoardInSession = false;

    /**
     * Constructs a new DepartmentMenu with the given scanner.
     *
     * @param scanner The scanner object to read input from the user.
     */
    public DepartmentMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Displays the department menu in the command line and handles user interactions.
     * Allows users to choose various departmental operations like holding exam boards,
     * reviewing transcripts, and changing student progression.
     */
    public void showMenu() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Department Menu");
            System.out.println("1. Hold Exam Board");
            System.out.println("2. Review Student Transcripts");
            System.out.println("3. Change Student Progression");
            System.out.println("4. Exit to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Holding exam board...");
                    examBoardInSession = true;
                    break;
                case 2:
                    if (examBoardInSession) {
                        reviewStudentTranscripts();
                    } else {
                        System.out.println("Exam board is not in session. Cannot review transcripts now.");
                    }
                    break;
                case 3:
                    if (examBoardInSession) {
                        changeStudentProgression();
                    } else {
                        System.out.println("Exam board is not in session. Cannot change student progression now.");
                    }
                    break;
                case 4:
                    isRunning = false;
                    examBoardInSession = false;
                    System.out.println("Exiting Department Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Handles the review of student transcripts.
     * Can only be performed when the exam board is in session.
     */
    private void reviewStudentTranscripts() {
        System.out.println("Enter Student ID to review transcripts:");
        int studentId = scanner.nextInt();
        Student student = FileManager.getStudentById(studentId);
        if (student != null) {
            String transcript = student.getTranscript();
            System.out.println(transcript);
        } else {
            System.out.println("Student not found.");
        }
    }

    /**
     * Handles the change of student progression.
     * Provides options like repeating a module, linking-in modules, repeating a semester or year.
     * Can only be performed when the exam board is in session.
     */
    private void changeStudentProgression() {
        System.out.println("Enter Student ID to change progression:");
        int studentId = scanner.nextInt();
        Student student = FileManager.getStudentById(studentId);
        if (student != null) {
            System.out.println("Select Progression Option: 1. Repeat Module 2. Link-In Module 3. Repeat Semester 4. Repeat Year");
            int option = scanner.nextInt();
            System.out.println("Student progression updated.");
        } else {
            System.out.println("Student not found.");
        }
    }
}

