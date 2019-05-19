package com.test.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Entity
@Table(name = "homework", schema = "homework", catalog = "")
public class HomeworkEntity {
    private int homeworkId;
    private String name;
    private Integer courseId;
    private Integer percent;
    private Timestamp deadline;

    @Id
    @Column(name = "homework_id", nullable = false)
    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "course_id", nullable = true)
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "percent", nullable = true)
    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Basic
    @Column(name = "deadline", nullable = true)
    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HomeworkEntity that = (HomeworkEntity) o;

        if (homeworkId != that.homeworkId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (courseId != null ? !courseId.equals(that.courseId) : that.courseId != null) return false;
        if (percent != null ? !percent.equals(that.percent) : that.percent != null) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = homeworkId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (percent != null ? percent.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        return result;
    }
}
