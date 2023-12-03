import java.util.ArrayList;

public class Faculty {
    private String facultyId;
    private String name;
    private ArrayList<String> modulesTaught;

    public Faculty(String facultyId, String name) {
        this.facultyId = facultyId;
        this.name = name;
        this.modulesTaught = new ArrayList<String>();
    }

    public void addModule(String module) {
        modulesTaught.add(module);
    }

    public void removeModule(String module) {
        if (modulesTaught.contains(module)) {
            modulesTaught.remove(module);
        } else {
            System.out.println(module + " is not taught by " + name + ".");
        }
    }

    public void getFacultyDetails() {
        System.out.println("Faculty ID: " + facultyId);
        System.out.println("Name: " + name);
        System.out.println("Modules taught: " + modulesTaught);
    }
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
