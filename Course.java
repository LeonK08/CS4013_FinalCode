import java.util.ArrayList;
import java.util.List;

public class Course {
    private String title;
    private String code;
    private ArrayList<Module> modules = new ArrayList<>();

    private static List<Course> courses = new ArrayList<>();

    static{
        courses = FileManager.readCourses();
    }

    private static final String COURSES_CSV_FILE = "Data/courses.csv";

    /**
     * Constructs a Course with the given title and code
     *
     * @param title Course title
     * @param code  Course code
     */
    public Course(String title, String code) {
        this.title = title;
        this.code = code;
    }

    /**
     * Constructs a Course with the given title, code and list of modules
     *
     * @param title   Course title
     * @param code    Course code
     * @param modules ArrayList of Modules
     */
    public Course(String title, String code, ArrayList<Module> modules) {
        this.title = title;
        this.code = code;
        this.modules = modules;
    }

    /**
     * Adds the given Module to the modules ArrayList
     *
     * @param module The Module to be added
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Constructs a new Module with the given title, code and number of credits, and adds it to the modules ArrayList
     *
     * @param title   Module title
     * @param code    Module code
     * @param credits Number of credits the module is worth
     */
    public void addModule(String title, String code, int credits) {
        modules.add(new Module(title, code, credits));
    }

    /**
     * Removes the given module from the modules ArrayList
     *
     * @param module The Module to remove
     */
    public void removeModule(Module module) {
        modules.remove(module);
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }
    public static Course findCourseByTitle(String title) {
        for (Course course : courses) {
            if (course.getTitle().equalsIgnoreCase(title)) {
                return course;
            }
        }
        return null;
    }
}


