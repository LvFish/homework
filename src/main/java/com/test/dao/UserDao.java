package com.test.dao;

import com.test.model.UserEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liujiawang on 2019/1/28.
 */
@Repository("userDao")
public class UserDao extends BaseDao {
    public UserEntity getById(int id) {
        return get(UserEntity.class, id);

    }


    public List<UserEntity> getByUserName(String UserName){
        String hql = "from UserEntity as user where user.username=?";
        Query query = query(hql);
        query.setString(0,UserName);
        List<UserEntity> users = query.list();
        return users;
    }


    public List<UserEntity> getAllTeacher(){
        String hql = "from UserEntity as user where user.isTeacher = 1";
        Query query = query(hql);
        return query.list();
    }

    public List<UserEntity> getAllStudent(){
        String hql = "from UserEntity as user where user.isTeacher = 0";
        Query query = query(hql);
        return query.list();
    }

    public List<UserEntity> getStudentNotInCourse(String courseId){
        String hql = "select * from user u\n" +
                "where u.user_id not in(\n" +
                "select u.user_id from inner_course_student ics,user u\n" +
                "where ics.course_id = "+courseId+"\n" +
                "and u.user_id =ics.student_id" +
                ") and u.is_teacher = 0";
        System.out.println(hql);
        Query query = getSession().createSQLQuery(hql).addEntity(UserEntity.class);

        return query.list();
    }

}