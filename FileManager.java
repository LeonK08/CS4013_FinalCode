import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.csv.CSVPrinter;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

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


    public static List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        try (CSVParser parser = new CSVParser(new FileReader("data/students.csv"), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
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



    private static Course findCourseByCode(String code, Course[] courses) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    private static Module findModuleByCode(String code, Module[] modules) {
        for (Module module : modules) {
            if (module.getCode().equals(code)) {
                return module;
            }
        }
        return null;
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
    public static Module findModuleByCode(String code) {
        List<Module> modules = readModules();
        for (Module module : modules) {
            if (module.getCode().equals(code)) {
                return module;
            }
        }
        return null;
    }
    public static Faculty getFacultyById(String facultyId) {
        List<Faculty> faculties = readFaculty();
        for (Faculty faculty : faculties) {
            if (faculty.getFacultyId().equals(facultyId)) {
                return faculty;
            }
        }
        return null;
    }

    public static Student getStudentById(int studentId) {
        try {
            String projectDirectory = System.getProperty("user.dir");
            String studentsCsvFilePath = projectDirectory + "data/students.csv";
            String resultsCsvFilePath = projectDirectory + "data/results.csv";

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

