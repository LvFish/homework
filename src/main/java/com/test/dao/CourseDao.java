package com.test.dao;

import com.test.model.CourseEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Repository("courseDao")
public class CourseDao  extends BaseDao{

    public CourseEntity getById(int id) {
        return get(CourseEntity.class, id);

    }

    public List<Object[]> getCourseByStudent(String userId){
        String hql = "select c.course_name,u.name from inner_course_student ics,course c,inner_course_teacher ict,user u " +
                "where ics.student_id = " + userId +
                " and ics.course_id = c.course_id " +
                "and ict.course_id = c.course_id " +
                "and ict.teacher_id = u.user_id ";
        Query query  = getSession().createSQLQuery(hql);
        return query.list();
    }

    public List<Object[]> getCourseByTeacher(String userId){
        String hql = "SELECT ict.course_id,c.course_name,t.cnt FROM inner_course_teacher ict,course c,( " +
                "    SELECT count(*) AS cnt,course_id FROM inner_course_student ics GROUP BY ics.course_id) " +
                "AS t WHERE teacher_id= "+userId+" AND c.course_id=ict.course_id AND t.course_id=ict.course_id";
        Query query  = getSession().createSQLQuery(hql);
        return query.list();
    }

    public List<CourseEntity> getByCourseName(String name){
        String hql = "from CourseEntity as c where c.courseName = ? order by id desc";
        Query query = query(hql);
        query.setString(0,name);
        query.setMaxResults(1);
        return query.list();
    }

    public List<Map> getAllStudentByCourseId(String courseId){
        String hql = "select ics.id,u.name from inner_course_student ics,user u\n" +
                "where ics.course_id = "+courseId+"\n" +
                "and u.user_id = ics.student_id";
        Query query = getSession().createSQLQuery(hql);
        List<Object[]> list = query.list();
        List<Map> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] obs = list.get(i);
            Map<String,Object> map = new HashMap<>();
            map.put("icsId",obs[0]);
            map.put("studentName",obs[1]);
            list2.add(map);
        }
        return list2;
    }



}
