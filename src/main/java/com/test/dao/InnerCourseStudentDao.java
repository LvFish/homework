package com.test.dao;

import com.test.model.InnerCourseStudentEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liujiawang on 2019/5/17.
 */
@Repository("innerCourseStudentDao")
public class InnerCourseStudentDao extends BaseDao{

    public InnerCourseStudentEntity getById(int id) {
        return get(InnerCourseStudentEntity.class, id);

    }

    public List<InnerCourseStudentEntity> getByCourseId(String courseId){
        String hql = "from InnerCourseStudentEntity as tt where tt.courseId=?";
        Query query = query(hql);
        query.setInteger(0,Integer.valueOf(courseId));
        List<InnerCourseStudentEntity> list = query.list();
        return list;
    }
}
