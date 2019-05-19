package com.test.model;

import javax.persistence.*;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Entity
@Table(name = "teacher_correct", schema = "homework", catalog = "")
public class TeacherCorrectEntity {
    private int id;
    private Integer teacherId;
    private Integer grade;
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
    @Column(name = "teacher_id", nullable = true)
    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Basic
    @Column(name = "grade", nullable = true, precision = 0)
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
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

        TeacherCorrectEntity that = (TeacherCorrectEntity) o;

        if (id != that.id) return false;
        if (teacherId != null ? !teacherId.equals(that.teacherId) : that.teacherId != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        if (studentHomeworkId != null ? !studentHomeworkId.equals(that.studentHomeworkId) : that.studentHomeworkId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (teacherId != null ? teacherId.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (studentHomeworkId != null ? studentHomeworkId.hashCode() : 0);
        return result;
    }
}
