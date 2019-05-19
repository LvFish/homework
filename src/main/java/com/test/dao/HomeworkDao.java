package com.test.dao;

import com.test.model.HomeworkEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Repository("homeworkDao")
public class HomeworkDao extends BaseDao{

    public HomeworkEntity getById(int id) {
        return get(HomeworkEntity.class, id);

    }

    public HomeworkEntity getNewLast(){
        String hql = "from HomeworkEntity as home order by home.homeworkId desc";
        Query query = query(hql);
        query.setMaxResults(1);
        List<HomeworkEntity> home = query.list();
        return home.get(0);
    }


    public List<Object[]> getHomeworkByUser(String userId){
        String hql = "select sh.id,sh.file_url,sh.grade,h.name,c.course_name,u.name as teacherName,h.deadline from student_homework sh,homework h,course c,\n" +
                "inner_course_teacher ict,user u\n" +
                "where sh.student_id = "+userId+"\n" +
                "and sh.homework_id = h.homework_id\n" +
                "and h.course_id = c.course_id\n" +
                "and ict.course_id = c.course_id\n" +
                "and ict.teacher_id = u.user_id";
        Query query  = getSession().createSQLQuery(hql);
        return query.list();
    }

    public List<Object[]> getCorrectHomeworkByUser(String userId){
        String hql = "select c.id,sh.file_url,h.name,cc.course_name,u.name as teacherName from correct c,student_homework sh,homework h,course cc,inner_course_teacher ict,user u\n" +
                "where c.student_id = "+userId+"\n" +
                "and c.grade is null\n" +
                "and sh.id = c.student_homework_id\n" +
                "and sh.homework_id = h.homework_id\n" +
                "and h.course_id = cc.course_id\n" +
                "and ict.course_id = cc.course_id\n" +
                "and ict.teacher_id = u.user_id";
        Query query  = getSession().createSQLQuery(hql);
        return query.list();
    }

    public List<Map> getCorrectHomework(String userId){
        String hql = "select st.file_url,u.name,h.name as homeworkName,c.course_name,tc.id from teacher_correct tc,student_homework st,user u,homework h,course c\n" +
                "where tc.grade is null\n" +
                "and st.id = tc.student_homework_id\n" +
                "and u.user_id = st.student_id\n" +
                "and h.homework_id = st.homework_id\n" +
                "and c.course_id = h.course_id\n" +
                "and tc.teacher_id = "+userId;
        Query query  = getSession().createSQLQuery(hql);
        List<Object[]> list = query.list();
        List<Map> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] obs = list.get(i);
            Map<String,Object> map = new HashMap<>();
            map.put("fileUrl",obs[0]);
            map.put("studentName",obs[1]);
            map.put("homeworkName",obs[2]);
            map.put("courseName",obs[3]);
            map.put("id",obs[4]);
            list2.add(map);
        }
        return list2;
    }

    public List<Map> getHomeworkByTeacher(String userId){
        String hql = "select c.course_name,h.name,h.deadline,t.* from course c,inner_course_teacher ict,homework h,(\n" +
                "select sh.homework_id,count(*) as all_number,sum(if(file_url is null,0,1)) as un_finished  from student_homework sh\n" +
                "group by sh.homework_id\n" +
                ") as t\n" +
                "where c.course_id = ict.course_id\n" +
                "and ict.teacher_id = "+userId+"\n" +
                "and h.course_id = c.course_id\n" +
                "and h.homework_id = t.homework_id\n" +
                "\n";
        Query query  = getSession().createSQLQuery(hql);
        List<Object[]> list = query.list();
        List<Map> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] obs = list.get(i);
            Map<String,Object> map = new HashMap<>();
            map.put("courseName",obs[0]);
            map.put("name",obs[1]);
            map.put("deadline",obs[2]);
            map.put("homeworkId",obs[3]);
            map.put("allNumber",obs[4]);
            map.put("unFinished",obs[5]);
            list2.add(map);
        }
        return list2;
    }


}
