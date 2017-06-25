/**
 * Created by Administrator on 2017/6/23.
 */

public class Student {

    private String stuID;
    private String stuName;
    private int  stuAge;
    private String stuSex;
    private String stuSchool;
    public String getStuID() {
        return stuID;
    }
    public void setStuID(String stuID) {
        this.stuID = stuID;
    }
    public String getStuName() {
        return stuName;
    }
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
    public int getStuAge() {
        return stuAge;
    }
    public void setStuAge(int stuAge) {
        this.stuAge = stuAge;
    }
    public Student(String stuID, String stuName, int stuAge, String stuSex,
                   String stuSchool) {
        super();
        this.stuID = stuID;
        this.stuName = stuName;
        this.stuAge = stuAge;
        this.stuSex = stuSex;
        this.stuSchool = stuSchool;
    }
    public String getStuSex() {
        return stuSex;
    }
    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }
    public String getStuSchool() {
        return stuSchool;
    }
    public void setStuSchool(String stuSchool) {
        this.stuSchool = stuSchool;
    }
}
