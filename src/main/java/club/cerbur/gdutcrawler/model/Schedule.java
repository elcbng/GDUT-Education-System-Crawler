package club.cerbur.gdutcrawler.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Schedule {

    @JSONField(name = "kcmc")
    private String courseName;

    @JSONField(name = "sknrjj")
    private String courseContent;

    @JSONField(name = "jcdm")
    private String courseTime;

    @JSONField(name = "zc")
    private String courseWeek;

    @JSONField(name = "xq")
    private String courseDay;

    @JSONField(name = "jxcdmc")
    private String courseRoom;

    @JSONField(name = "teaxms")
    private String courseTeacher;


    @Override
    public String toString() {
        return "Schedule{" +
                "courseName='" + courseName + '\'' +
                ", courseContent='" + courseContent + '\'' +
                ", courseTime='" + courseTime + '\'' +
                ", courseWeek='" + courseWeek + '\'' +
                ", courseDay='" + courseDay + '\'' +
                ", courseRoom='" + courseRoom + '\'' +
                ", courseTeacher='" + courseTeacher + '\'' +
                '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseWeek() {
        return courseWeek;
    }

    public void setCourseWeek(String courseWeek) {
        this.courseWeek = courseWeek;
    }

    public String getCourseDay() {
        return courseDay;
    }

    public void setCourseDay(String courseDay) {
        this.courseDay = courseDay;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public Schedule() {
    }

    public Schedule(String courseName, String courseContent, String courseTime, String courseWeek, String courseDay, String courseRoom, String courseTeacher) {
        this.courseName = courseName;
        this.courseContent = courseContent;
        this.courseTime = courseTime;
        this.courseWeek = courseWeek;
        this.courseDay = courseDay;
        this.courseRoom = courseRoom;
        this.courseTeacher = courseTeacher;
    }
}
