import java.util.Scanner;

public class FacultyMenu {
    private Scanner scanner;

    /**
     * Constructs a new FacultyMenu with the given Scanner
     * @param scanner The Scanner to use
     */
    public FacultyMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Displays the menu for faculty members. Displays login prompt followed by the option to submit results, review transcripts or exit to the main menu
     */
    public void showMenu() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        Faculty currentFaculty = login();
        if (currentFaculty == null) {
            System.out.println("Invalid Faculty ID. Access Denied.");
            return;
        }

        // Display menu options
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Faculty Menu");
            System.out.println("1. Submit Results");
            System.out.println("2. Review Transcripts");
            System.out.println("3. Exit to Main Menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    submitResults();
                    break;
                case 2:
                    reviewTranscripts();
                    break;
                case 3:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Allows a faculty member to log in using their ID
     * @return The Faculty object found with the given ID
     */
    public Faculty login() {
        System.out.println("Enter Faculty ID: ");
        String facultyId = scanner.nextLine();
        return FileManager.getFacultyById(facultyId); // Assuming FileManager has this method
    }

    /**
     * Allows the member of faculty to add results to a student's record using the student's ID and the module code
     */
    private void submitResults() {
        System.out.println("Enter Student ID for result submission:");
        int studentId = scanner.nextInt();
        Student student = FileManager.getStudentById(studentId); // Assuming this method exists

        if (student != null) {
            System.out.println("Enter Module Code:");
            String moduleCode = scanner.next();
            Module module = FileManager.findModuleByCode(moduleCode); // Assuming this method exists

            if (module != null) {
                System.out.println("Enter Grade:");
                double grade = scanner.nextDouble();
                int currentYear = student.getYear();
                Result newResult = new Result(module, grade, currentYear);
                student.addResult(newResult); // Assuming method addResult in Student class
                FileManager.updateStudentResults(student);; // Assuming this method updates the student's results in a file or database

                System.out.println("Result submitted successfully.");
            } else {
                System.out.println("Module not found.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    /**
     * Allows a member of faculty to view a student's results transcript
     */
    private void reviewTranscripts() {
        System.out.println("Enter Student ID to review transcripts:");
        int studentId = scanner.nextInt();
        Student student = FileManager.getStudentById(studentId); // Assuming this method exists

        if (student != null) {
            String transcript = student.getTranscript(); // Assuming getTranscript method in Student class
            System.out.println(transcript);
        } else {
            System.out.println("Student not found.");
        }
    }
}
