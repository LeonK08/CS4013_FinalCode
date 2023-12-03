import java.util.Scanner;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Student loggedInStudent = null;

        while (true) {
            System.out.println("Welcome to the UL Student Records System!");
            System.out.println("Please select your user type:");
            System.out.println("1. Faculty");
            System.out.println("2. Student");
            System.out.println("3. Department");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    FacultyMenu facultyMenu = new FacultyMenu(scanner);
                    facultyMenu.showMenu();
                    break;
                case 2:
                    System.out.print("Enter student ID to log in: ");
                    int studentId = scanner.nextInt();
                    Student foundStudent = findStudentById(studentId);

                    if (foundStudent != null) {
                        System.out.println("Student logged in successfully.");
                        loggedInStudent = foundStudent;
                        StudentMenu studentMenu = new StudentMenu(scanner, loggedInStudent);
                        studentMenu.showMenu();
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 3:
                    DepartmentMenu departmentMenu = new DepartmentMenu(scanner);
                    departmentMenu.showMenu();
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static Student findStudentById(int studentId) {
        try {
            String projectDirectory = System.getProperty("user.dir");
            String studentsCsvFilePath = projectDirectory + "/data/students.csv";
            String resultsCsvFilePath = projectDirectory + "/data/results.csv";

            FileReader studentsReader = new FileReader(studentsCsvFilePath);
            CSVParser studentsCsvParser = new CSVParser(studentsReader, CSVFormat.DEFAULT.withHeader());
            FileReader resultsReader = new FileReader(resultsCsvFilePath);
            CSVParser resultsCsvParser = new CSVParser(resultsReader, CSVFormat.DEFAULT.withHeader());

            for (CSVRecord studentRecord : studentsCsvParser) {
                int recordStudentId = Integer.parseInt(studentRecord.get("StudentID"));
                if (recordStudentId == studentId) {
                    String firstName = studentRecord.get("First Name");
                    String lastName = studentRecord.get("Last Name");
                    String courseTitle = studentRecord.get("Course");
                    Course course = Course.findCourseByTitle(courseTitle);
                    int year = Integer.parseInt(studentRecord.get("Year"));
                    Student student = new Student(studentId, firstName, lastName, course , year);
                    for (CSVRecord resultRecord : resultsCsvParser) {
                        int resultStudentId = Integer.parseInt(resultRecord.get("StudentID"));
                        if (resultStudentId == studentId) {
                            String moduleCode = resultRecord.get("ModuleCode");
                            double grade = Double.parseDouble(resultRecord.get("Grade"));
                            int semester = Integer.parseInt(resultRecord.get("Semester"));
                            Module module = new Module(moduleCode);
                            Result result = new Result(module, grade, year, semester);
                            student.addResult(result);
                        }
                    }return student;
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
