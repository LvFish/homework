package com.test.dao;

import com.test.model.AppealEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liujiawang on 2019/5/17.
 */
@Repository("appealDao")
public class AppealDao extends BaseDao{
    public AppealEntity getById(int id) {
        return get(AppealEntity.class, id);

    }

    public List<AppealEntity> getByStudentAndHomework(String userId, String studentHomeworkId){
        String hql = "from AppealEntity as ae where ae.studentId=? and ae.studentHomeworkId = ?";
        Query query = query(hql);
        query.setInteger(1,Integer.valueOf(studentHomeworkId));
        query.setInteger(0,Integer.valueOf(userId));
        query.setMaxResults(1);
        List<AppealEntity> correct = query.list();
        return correct;
    }

    public List<Map> getAppealByTeacher(String userId){
        String hql = "select u2.name as studentName,h.name as homeworkName,c.course_name,st.file_url,a.appeal_id,st.id from user u,inner_course_teacher ict,course c,homework h,student_homework st,appeal a,user u2\n" +
                "where u.user_id = "+userId+"\n" +
                "and ict.teacher_id = u.user_id\n" +
                "and ict.course_id = c.course_id\n" +
                "and h.course_id = c.course_id\n" +
                "and st.homework_id = h.homework_id\n" +
                "and a.student_id = st.student_id\n" +
                "and a.student_homework_id = st.id\n" +
                "and u2.user_id = a.student_id\n" +
                "and a.status = 0";
        Query query  = getSession().createSQLQuery(hql);
        List<Object[]> list = query.list();
        List<Map> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] obs = list.get(i);
            Map<String,Object> map = new HashMap<>();
            map.put("studentName",obs[0]);
            map.put("homeworkName",obs[1]);
            map.put("courseName",obs[2]);
            map.put("fileUrl",obs[3]);
            map.put("appealId",obs[4]);
            map.put("studentHomeworkId",obs[5]);
            list2.add(map);
        }
        return list2;
    }
}
