package club.cerbur.gdutcrawler.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Grade {
    @JSONField(name = "xnxqmc")
    private String examTime;

    @JSONField(name = "cjjd")
    private String examPole;

    @JSONField(name = "zcj")
    private String examScore;

    @JSONField(name = "kcmc")
    private String examName;

    @JSONField(name = "xdfsmc")
    private String readMethod;

    @JSONField(name = "xf")
    private String credit;

    @Override
    public String toString() {
        return "Grade{" +
                "examTime='" + examTime + '\'' +
                ", examPole='" + examPole + '\'' +
                ", examScore='" + examScore + '\'' +
                ", examName='" + examName + '\'' +
                ", readMethod='" + readMethod + '\'' +
                ", credit='" + credit + '\'' +
                '}';
    }

    public Grade() {
    }

    public Grade(String examTime, String examPole, String examScore, String examName, String readMethod, String credit) {
        this.examTime = examTime;
        this.examPole = examPole;
        this.examScore = examScore;
        this.examName = examName;
        this.readMethod = readMethod;
        this.credit = credit;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamPole() {
        return examPole;
    }

    public void setExamPole(String examPole) {
        this.examPole = examPole;
    }

    public String getExamScore() {
        return examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getReadMethod() {
        return readMethod;
    }

    public void setReadMethod(String readMethod) {
        this.readMethod = readMethod;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
