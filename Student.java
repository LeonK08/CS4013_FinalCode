import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private Course course; // Reference to the associated course
    private int year;
    private List<Result> results;

    private String degreeType;

    private static List<Student> students = new ArrayList<>();

    static {
        students = FileManager.readStudents();
    }

    public Student(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int studentId, String firstName, String lastName, Course course, int year) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.year = year;
        this.results = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Course getCourse() {
        return course;
    }

    public int getYear(){
        return year;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Result> getResults() {
        return results;
    }

    public void addResult(Module module, double grade, int year, int semester) {
        Result result = new Result(module, grade, year, semester);
        results.add(result);
    }

    public void addResult(Result result){
        results.add(result);
    }


    public int getTotalCredits() {
        int totalCredits = 0;

        for (Result result : results) {
            Module module = result.getModule();
            totalCredits += module.getCredits();
        }

        return totalCredits;
    }

    public double overallFinalQca() {
        double totalQca = 0.0;
        int totalResults = 0;

        for (Result result : results) {
            int year = result.getYear();
            if (year > 1) {
                totalQca += result.calculateResultQCA();
                totalResults++;
            }
        }

        return totalResults > 0 ? totalQca / totalResults : 0.0;
    }

    public double calculateQCAForYear(int year) {
        double totalQca = 0.0;
        int totalResults = 0;

        for (Result result : results) {
            if (result.getYear() == year) {
                totalQca += result.calculateResultQCA();
                totalResults++;
            }
        }

        return totalResults > 0 ? totalQca / totalResults : 0.0;
    }

    public double calculateQCAForYear1() {
        return calculateQCAForYear(1);
    }

    public double calculateQCAForYear2() {
        return calculateQCAForYear(2);
    }

    public double calculateQCAForYear3() {
        return calculateQCAForYear(3);
    }

    public double calculateQCAForYear4() {
        return calculateQCAForYear(4);
    }

    private static Student findStudentById(int studentId) {
        try {
            String projectDirectory = System.getProperty("user.dir");
            String studentsCsvFilePath = projectDirectory + "/data/students.csv"; // Replace with your students CSV file path
            String resultsCsvFilePath = projectDirectory + "/data/results.csv"; // Replace with your results CSV file path


            FileReader studentsReader = new FileReader(studentsCsvFilePath);
            CSVParser studentsCsvParser = new CSVParser(studentsReader, CSVFormat.DEFAULT.withHeader());
            FileReader resultsReader = new FileReader(resultsCsvFilePath);
            CSVParser resultsCsvParser = new CSVParser(resultsReader, CSVFormat.DEFAULT.withHeader());

            for (CSVRecord studentRecord : studentsCsvParser) {
                int recordStudentId = Integer.parseInt(studentRecord.get("StudentID"));
                if (recordStudentId == studentId) {
                    String firstName = studentRecord.get("First Name");
                    String lastName = studentRecord.get("Last Name");
                    Student student = new Student(studentId, firstName, lastName);
                    for (CSVRecord resultRecord : resultsCsvParser) {
                        int resultStudentId = Integer.parseInt(resultRecord.get("StudentId"));
                        if (resultStudentId == studentId) {
                            String moduleCode = resultRecord.get("ModuleCode");
                            double grade = Double.parseDouble(resultRecord.get("Grade"));
                            int resultYear = Integer.parseInt(resultRecord.get("Year"));
                            int semester = Integer.parseInt(resultRecord.get("Semester"));
                            Module module = new Module(moduleCode);
                            Result result = new Result(module, grade, resultYear, semester);
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
    public Student(int studentId, String firstName, String lastName, Course course, int year,String degreeType) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.degreeType = degreeType;
        this.year = year;
        this.results = new ArrayList<>();
    }

    public String getDegreeType() { return degreeType;}

    public String getTranscript() {
        StringBuilder transcript = new StringBuilder();
        transcript.append("Transcript for ").append(firstName).append(" ").append(lastName).append(" (ID: ").append(studentId).append(")\n");
        transcript.append("Course: ").append(course.getTitle()).append("\n");

        // Assuming each result has a module and grade
        for (Result result : results) {
            Module module = result.getModule();
            transcript.append("Module: ").append(module.getTitle()).append(" (").append(module.getCode()).append(") - Grade: ").append(result.getGrade()).append("\n");
            double qca = calculateQCA(result);
            transcript.append("Quality Cumulative Average (QCA): ").append(qca).append("\n");
        }


        return transcript.toString();
    }

    // Method to calculate the Quality Cumulative Average (QCA)
    private double calculateQCA(Result result) {
        double grade = result.getGrade();
        if (grade >= 80.0) {
            return 4.0;  // A1
        } else if (grade >= 72.0) {
            return 3.6;  // A2
        } else if (grade >= 64.0) {
            return 3.2;  // B1
        } else if (grade >= 60.0) {
            return 2.8;  // B2
        } else if (grade >= 56.0) {
            return 2.6;  // B3
        } else if (grade >= 52.0) {
            return 2.4;  // C1
        } else if (grade >= 48.0) {
            return 2.2;  // C2
        } else if (grade >= 40.0) {
            return 2.0;  // C3
        } else if (grade >= 35.0) {
            return 1.6;  // D1
        } else if (grade >= 30.0) {
            return 1.2;  // D2
        } else {
            return 0.0; // F
        }
    }


}