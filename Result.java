public class Result {
    private Module module;
    private double grade;
    private int year;
    private int semester;

    /**
     * constructs a new instance of Result
     * 
     * @param module module the result is for
     * @param grade the grade for the module
     * @param year the year of the module taught
     * @param semester the semester of the module tought 
     */
    public Result(Module module, double grade, int year, int semester) {
        this.module = module;
        this.grade = grade;
        this.year = year;
        this.semester = semester;
    }

    /**
     * constructs a new instance of Result
     * 
     * @param module module of the result
     * @param grade grade of the module
     * @param year year the module is in
     */
    public Result(Module module, double grade, int year) {
        this.module = module;
        this.grade = grade;
        this.year = year;
    }

    /**
     * constructs a new instance of Result
     * 
     * @param module module the result will be in   
     * @param grade grade for the module
     */
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
