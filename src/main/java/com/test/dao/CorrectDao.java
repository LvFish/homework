package com.test.dao;

import com.test.model.CorrectEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Repository("correctDao")
public class CorrectDao extends BaseDao{
    public CorrectEntity getById(int id) {
        return get(CorrectEntity.class, id);

    }

    public List<Map> getCourseByManager(){
        String hql = "select c.course_id,c.course_name,ict.id,ict.teacher_id,u.`name` from course c,inner_course_teacher ict,user u\n" +
                "where c.course_id = ict.course_id\n" +
                "and ict.teacher_id = u.user_id";
        Query query  = getSession().createSQLQuery(hql);
        List<Object[]> list = query.list();
        List<Map> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] obs = list.get(i);
            Map<String,Object> map = new HashMap<>();
            map.put("courseId",obs[0]);
            map.put("courseName",obs[1]);
            map.put("ictId",obs[2]);
            map.put("ictTeacherId",obs[3]);
            map.put("teacherName",obs[4]);
            list2.add(map);
        }
        return list2;
    }

    public List<CorrectEntity> getByStudentHomeworkId(String studentHomeworkId){
        String hql = "from CorrectEntity as correct where correct.studentHomeworkId=? and correct.grade is not null";
        Query query = query(hql);
        query.setInteger(0,Integer.valueOf(studentHomeworkId));
        List<CorrectEntity> correct = query.list();
        return correct;
    }

    public List<Map> getCorrectDetail(String homeworkId){
        String hql = "select c.grade,u.name from correct c,user u\n" +
                "where c.student_homework_id = "+homeworkId+"\n" +
                "and c.student_id = u.user_id";
        Query query  = getSession().createSQLQuery(hql);
        List<Object[]> list = query.list();
        List<Map> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] obs = list.get(i);
            Map<String,Object> map = new HashMap<>();
            map.put("grade",obs[0]);
            map.put("name",obs[1]);
            map.put("type","学生");
            list2.add(map);
        }
        return list2;
    }

    public List<Map> getCorrectDetailByTeacher(String homeworkId){
        String hql = "select tc.grade,u.name from teacher_correct tc,user u\n" +
                "where tc.student_homework_id = "+homeworkId+"\n" +
                "and tc.teacher_id = u.user_id";
        Query query  = getSession().createSQLQuery(hql);
        List<Object[]> list = query.list();
        List<Map> list2 = new ArrayList<>();
        if(list.size()>0) {
            Object[] obs = list.get(0);
            Map<String, Object> map = new HashMap<>();
            map.put("grade", obs[0]);
            map.put("name", obs[1]);
            map.put("type", "教师");
            list2.add(map);
        }
        return list2;
    }

}
