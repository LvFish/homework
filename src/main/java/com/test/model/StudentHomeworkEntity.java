package com.test.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Entity
@Table(name = "student_homework", schema = "homework", catalog = "")
public class StudentHomeworkEntity {
    private int id;
    private Integer studentId;
    private Integer homeworkId;
    private BigDecimal grade;
    private String fileUrl;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "student_id", nullable = true)
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "homework_id", nullable = true)
    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    @Basic
    @Column(name = "grade", nullable = true, precision = 2)
    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "file_url", nullable = true, length = 255)
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentHomeworkEntity that = (StudentHomeworkEntity) o;

        if (id != that.id) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (homeworkId != null ? !homeworkId.equals(that.homeworkId) : that.homeworkId != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        if (fileUrl != null ? !fileUrl.equals(that.fileUrl) : that.fileUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (homeworkId != null ? homeworkId.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (fileUrl != null ? fileUrl.hashCode() : 0);
        return result;
    }
}
