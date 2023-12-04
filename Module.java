import java.util.ArrayList;
import java.util.List;

public class Module {
    private String title;
    private String code;
    private int credits;

    private static List<Module> modules = new ArrayList<>();

    static {
        modules = FileManager.readModules();
    }

    /**
     * Constructs a Module with the given title, code and number of credits
     *
     * @param title   Module title
     * @param code    Module code
     * @param credits Number of credits the module is worth
     */
    public Module(String title, String code, int credits) {
        this.title = title;
        this.code = code;
        this.credits = credits;
    }

    /**
     * Constructs a Module with the given code
     *
     * @param code    Module code
     */
    public Module(String code){
        this.code = code;
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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Calculates the QCA for a specific grade in this module
     *
     * @param grade The grade obtained in the module
     * @return The QCA for the module based on the grade
     */
    public double calculateResultQCA(double grade) {
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
