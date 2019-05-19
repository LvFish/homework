package com.test.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Entity
@Table(name = "correct", schema = "homework", catalog = "")
public class CorrectEntity {
    private int id;
    private Integer studentId;
    private BigDecimal grade;
    private Integer studentHomeworkId;

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
    @Column(name = "grade", nullable = true, precision = 2)
    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "student_homework_id", nullable = true)
    public Integer getStudentHomeworkId() {
        return studentHomeworkId;
    }

    public void setStudentHomeworkId(Integer studentHomeworkId) {
        this.studentHomeworkId = studentHomeworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CorrectEntity that = (CorrectEntity) o;

        if (id != that.id) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        if (studentHomeworkId != null ? !studentHomeworkId.equals(that.studentHomeworkId) : that.studentHomeworkId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (studentHomeworkId != null ? studentHomeworkId.hashCode() : 0);
        return result;
    }
}
