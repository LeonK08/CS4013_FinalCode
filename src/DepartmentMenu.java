import java.util.Scanner;

public class DepartmentMenu {
    private Scanner scanner;
    private boolean examBoardInSession = false;
    public DepartmentMenu(Scanner scanner) {
        this.scanner = scanner;
    }
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

