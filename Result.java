public class Result {
    private Module module;
    private double grade;
    private int year;
    private int semester;

    public Result(Module module, double grade, int year, int semester) {
        this.module = module;
        this.grade = grade;
        this.year = year;
        this.semester = semester;
    }

    public Result(Module module, double grade, int year) {
        this.module = module;
        this.grade = grade;
        this.year = year;
    }

    public Result(Module module, double grade){
        this.module = module;
        this.grade = grade;
    }


    public Module getModule() {
        return module;
    }

    public double getGrade() {
        return grade;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }

    public double calculateResultQCA() {
        return module.calculateResultQCA(grade);
    }


}
