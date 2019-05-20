package com.test.dao;

import com.test.model.StudentHomeworkEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liujiawang on 2019/5/16.
 */
@Repository("studentHomeworkDao")
public class StudentHomeworkDao extends BaseDao {

    public StudentHomeworkEntity getById(int id) {
        return get(StudentHomeworkEntity.class, id);
    }

    public List<StudentHomeworkEntity> getByHomeworkId(int homeworkId){
        String hql = "from StudentHomeworkEntity as student where student.homeworkId=?";
        Query query = query(hql);
        query.setInteger(0,homeworkId);
        List<StudentHomeworkEntity> home = query.list();
        return home;
    }


    public List<Map> getHomeworkDetail(String homeworkId){
        String hql = "select sh.id,sh.grade,u.name as studentName,h.name from student_homework sh,user u,homework h\n" +
                "where sh.homework_id = "+homeworkId+"\n" +
                "and u.user_id = sh.student_id\n" +
                "and h.homework_id = sh.homework_id";
        Query query  = getSession().createSQLQuery(hql);
        List<Object[]> list = query.list();
        List<Map> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] obs = list.get(i);
            Map<String,Object> map = new HashMap<>();
            map.put("studentHomeworkId",obs[0]);
            map.put("grade",obs[1]);
            map.put("studentName",obs[2]);
            map.put("homeworkName",obs[3]);
            list2.add(map);
        }
        return list2;
    }

    public void deleteByHomeworkId(String homeworkId){
        String hql = "Delete FROM StudentHomeworkEntity Where homeworkId="+homeworkId ;
        Query q = getSession().createQuery(hql) ;
        q.executeUpdate() ;
    }
}
