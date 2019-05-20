public class Student {
    private String name;
    private int age;
    private float mid_grade;

    Student() {
        name = "none";
        age = 0;
        mid_grade = 0;
    }

    Student(String name, int age, float mid_grade) {
        this.name = name;
        this.age = age;
        this.mid_grade = mid_grade;
    }

    public String getStudentName() {
        return this.name;
    }

    public int getStudentAge() {
        return this.age;
    }

    public float getMidGrade() {
        return this.mid_grade;
    }

    public void setStudentName(String new_name) {
        this.name = new_name;
    }

    public void setStudentAge(int new_age) {
        this.age = new_age;
    }

    public void setMidGrade(float grade) {
        this.mid_grade = grade;
    }

}
