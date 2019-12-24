package club.cerbur.gdutcrawler.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Exam {
    @JSONField(name = "ksrq")
    private String examDate;

    @JSONField(name = "zc")
    private String examWeek;

    @JSONField(name = "xq")
    private String examDay;

    @JSONField(name = "kssj")
    private String examTime;

    @JSONField(name = "zwh")
    private String examPosition;

    @JSONField(name = "kcmc")
    private String examSubject;

    @JSONField(name = "kscdmc")
    private String examClassroom;

    @Override
    public String toString() {
        return "Exam{" +
                "examDate='" + examDate + '\'' +
                ", examWeek='" + examWeek + '\'' +
                ", examDay='" + examDay + '\'' +
                ", examTime='" + examTime + '\'' +
                ", examPosition='" + examPosition + '\'' +
                ", examSubject='" + examSubject + '\'' +
                ", examClassroom='" + examClassroom + '\'' +
                '}';
    }

    public Exam() {
    }

    public Exam(String examDate, String examWeek, String examDay, String examTime, String examPosition, String examSubject, String examClassroom) {
        this.examDate = examDate;
        this.examWeek = examWeek;
        this.examDay = examDay;
        this.examTime = examTime;
        this.examPosition = examPosition;
        this.examSubject = examSubject;
        this.examClassroom = examClassroom;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamWeek() {
        return examWeek;
    }

    public void setExamWeek(String examWeek) {
        this.examWeek = examWeek;
    }

    public String getExamDay() {
        return examDay;
    }

    public void setExamDay(String examDay) {
        this.examDay = examDay;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamPosition() {
        return examPosition;
    }

    public void setExamPosition(String examPosition) {
        this.examPosition = examPosition;
    }

    public String getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(String examSubject) {
        this.examSubject = examSubject;
    }

    public String getExamClassroom() {
        return examClassroom;
    }

    public void setExamClassroom(String examClassroom) {
        this.examClassroom = examClassroom;
    }
}
