public class Child {
    private String name;
    private int age;

    Child() {
        name = "none";
        age = 0;
    }

    Child(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getChildName() {
        return this.name;
    }

    public int getChildAge() {
        return this.age;
    }

    public void setChildName(String new_name) {
        this.name = new_name;
    }

    public void setChildAge(int new_age) {
        this.age = new_age;
    }


}
