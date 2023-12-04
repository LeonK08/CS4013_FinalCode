import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVPrinter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles file operations for the university system, including reading and writing faculty, module, course, and student data.
 */

public class FileManager {

    /**
     * Reads faculty data from a CSV file and returns a list of Faculty objects.
     *
     * @return List of Faculty objects read from the file.
     */
    public static List<Faculty> readFaculty() {
        List<Faculty> faculties = new ArrayList<>();
        try (CSVParser parser = new CSVParser(new FileReader("Data/faculty.csv"), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                String id = record.get("Faculty ID");
                String name = record.get("Name");
                faculties.add(new Faculty(id, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return faculties;
    }

    /**
     * Reads module data from a CSV file and returns a list of Module objects.
     *
     * @return List of Module objects read from the file.
     */
    public static List<Module> readModules() {
        List<Module> modules = new ArrayList<>();
        try (CSVParser parser = new CSVParser(new FileReader("Data/modules.csv"), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                String code = record.get("Module Code");
                String title = record.get("Module Title");
                int credits = Integer.parseInt(record.get("Credits"));
                modules.add(new Module(title, code, credits));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modules;
    }

    /**
     * Reads course data from a CSV file and returns a list of Course objects.
     *
     * @return List of Course objects read from the file.
     */
    public static List<Course> readCourses() {
        List<Course> courses = new ArrayList<>();
        try (CSVParser parser = new CSVParser(new FileReader("Data/courses.csv"), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                String title = record.get("Course Title");
                String code = record.get("Course Code");
                courses.add(new Course(title, code));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Reads student data from a CSV file and returns a list of Student objects.
     *
     * @return List of Student objects read from the file.
     */

    public static List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        try (CSVParser parser = new CSVParser(new FileReader("/Data/students.csv"), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                int studentId = Integer.parseInt(record.get("StudentID"));
                String firstName = record.get("FirstName");
                String lastName = record.get("LastName");
                String courseTitle = record.get("Course");
                Course course = Course.findCourseByTitle(courseTitle);
                int year = Integer.parseInt((record.get("Year")));

                students.add(new Student(studentId, firstName, lastName, course, year ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }


    /**
     * Finds a Course by its code.
     *
     * @param code The code of the Course to find.
     * @param courses Array of Courses to search in.
     * @return The found Course object, or null if not found.
     */

    private static Course findCourseByCode(String code, Course[] courses) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    /**
     * Finds a Module by its code.
     *
     * @param code The code of the Module to find.
     * @param modules Array of Modules to search in.
     * @return The found Module object, or null if not found.
     */
    private static Module findModuleByCode(String code, Module[] modules) {
        for (Module module : modules) {
            if (module.getCode().equals(code)) {
                return module;
            }
        }
        return null;
    }

    /**
     * Finds a Student by their ID using CSV file data.
     *
     * @param studentId The ID of the student to find.
     * @return The found Student object, or null if not found.
     */
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

    /**
     * Finds a Module by its code by reading from a CSV file.
     *
     * @param code The code of the Module to find.
     * @return The found Module object, or null if not found.
     */
    public static Module findModuleByCode(String code) {
        List<Module> modules = readModules();
        for (Module module : modules) {
            if (module.getCode().equals(code)) {
                return module;
            }
        }
        return null;
    }

    /**
     * Gets a Faculty member by their ID by reading from a CSV file.
     *
     * @param facultyId The ID of the Faculty member to find.
     * @return The found Faculty object, or null if not found.
     */
    public static Faculty getFacultyById(String facultyId) {
        List<Faculty> faculties = readFaculty();
        for (Faculty faculty : faculties) {
            if (faculty.getFacultyId().equals(facultyId)) {
                return faculty;
            }
        }
        return null;
    }

    /**
     * Gets a Student by their ID by reading from a CSV file.
     *
     * @param studentId The ID of the student to find.
     * @return The found Student object, or null if not found.
     */
    public static Student getStudentById(int studentId) {
        try {
            String projectDirectory = System.getProperty("user.dir");
            String studentsCsvFilePath = projectDirectory + "/Data/students.csv";
            String resultsCsvFilePath = projectDirectory + "/Data/results.csv";

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
                    Student student = new Student(studentId, firstName, lastName, course, year);
                    for (CSVRecord resultRecord : resultsCsvParser) {
                        int resultStudentId = Integer.parseInt(resultRecord.get("StudentID"));
                        if (resultStudentId == studentId) {
                            String moduleCode = resultRecord.get("ModuleCode");
                            double grade = Double.parseDouble(resultRecord.get("Grade"));
                            Module module = new Module(moduleCode);
                            Result result = new Result(module, grade);
                            student.addResult(result);
                        }
                    }return student;
                }
            }return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates the results of a given student in the results CSV file.
     *
     * @param student The student whose results need updating.
     */
    public static void updateStudentResults(Student student) {
        List<CSVRecord> allRecords = new ArrayList<>();
        String resultsFile = "data/results.csv";

        try (CSVParser parser = new CSVParser(new FileReader(resultsFile), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            allRecords.addAll(parser.getRecords());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (CSVPrinter printer = new CSVPrinter(new FileWriter(resultsFile), CSVFormat.DEFAULT.withHeader("StudentID", "ModuleCode", "Grade"))) {
            for (CSVRecord record : allRecords) {
                if (!record.get("StudentID").equals(String.valueOf(student.getStudentId()))) {
                    printer.printRecord(record);
                }
            }
            for (Result result : student.getResults()) {
                printer.printRecord(student.getStudentId(), result.getModule().getCode(), result.getGrade());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

