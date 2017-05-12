package adapters;

/**
 * Created by Administrator on 2016/11/8.
 */
public class JavaB {
    private boolean isCheck;
    private String name;
    private int age;

    public JavaB(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
