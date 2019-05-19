package com.test.dao;

import com.test.model.TeacherCorrectEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Repository("teacherCorrectDao")
public class TeacherCorrectDao extends BaseDao{
    public TeacherCorrectEntity getById(int id) {
        return get(TeacherCorrectEntity.class, id);

    }

    public List<TeacherCorrectEntity> getByStudentHomeworkId(String studentHomeworkId){
        String hql = "from TeacherCorrectEntity as correct where correct.studentHomeworkId=?";
        Query query = query(hql);
        query.setInteger(0,Integer.valueOf(studentHomeworkId));
        List<TeacherCorrectEntity> correct = query.list();
        return correct;
    }
}
