import java.util.ArrayList;

public class Faculty {
    private String facultyId;
    private String name;
    private ArrayList<String> modulesTaught;

    /**
     * Constructs a faculty member with the given ID and name
     * @param facultyId ID of the faculty member
     * @param name Name of the faculty member
     */
    public Faculty(String facultyId, String name) {
        this.facultyId = facultyId;
        this.name = name;
        this.modulesTaught = new ArrayList<String>();
    }

    /**
     * Adds a Module to the list of modules taught by the faculty member
     * @param module The Module object to add to modulesTaught
     */
    public void addModule(String module) {
        modulesTaught.add(module);
    }

    /**
     * Removes a module from the list of modules taught by the faculty member
     * @param module The Module to remove from modulesTaught
     */
    public void removeModule(String module) {
        if (modulesTaught.contains(module)) {
            modulesTaught.remove(module);
        } else {
            System.out.println(module + " is not taught by " + name + ".");
        }
    }

    /**
     * Returns string formatted details of the faculty member (ID, name and modules taught)
     */
    public void getFacultyDetails() {
        System.out.println("Faculty ID: " + facultyId);
        System.out.println("Name: " + name);
        System.out.println("Modules taught: " + modulesTaught);
    }

    // Getters
    public String getFacultyId(){
        return facultyId;
    }
    public String getName(){
        return name;
    }

    public ArrayList<String> getModulesTaught(){
        return modulesTaught;
    }
}
