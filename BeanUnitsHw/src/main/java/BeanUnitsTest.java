import org.junit.Assert;
import org.junit.Test;

public class BeanUnitsTest {
    @Test
    public void ChangeStudentTest() {
        Student student = new Student("Peter", 19, (float) 6.3);
        Child child = new Child("Nick", 10);

        BeanUnits.assign(student, child);
        Assert.assertEquals(student.getStudentName(), child.getChildName());
        Assert.assertEquals(student.getStudentAge(), child.getChildAge());
    }
}
