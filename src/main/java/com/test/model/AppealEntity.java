package com.test.model;

import javax.persistence.*;

/**
 * Created by liujiawang on 2019/5/17.
 */
@Entity
@Table(name = "appeal", schema = "homework", catalog = "")
public class AppealEntity {
    private int appealId;
    private Integer studentId;
    private Integer studentHomeworkId;
    private Integer status;

    @Id
    @Column(name = "appeal_id", nullable = false)
    public int getAppealId() {
        return appealId;
    }

    public void setAppealId(int appealId) {
        this.appealId = appealId;
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
    @Column(name = "student_homework_id", nullable = true)
    public Integer getStudentHomeworkId() {
        return studentHomeworkId;
    }

    public void setStudentHomeworkId(Integer studentHomeworkId) {
        this.studentHomeworkId = studentHomeworkId;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppealEntity that = (AppealEntity) o;

        if (appealId != that.appealId) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (studentHomeworkId != null ? !studentHomeworkId.equals(that.studentHomeworkId) : that.studentHomeworkId != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appealId;
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (studentHomeworkId != null ? studentHomeworkId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
