package com.test.dao;

import com.test.model.InnerCourseTeacherEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by liujiawang on 2019/5/17.
 */
@Repository("innerCourseTeacherDao")
public class InnerCourseTeacherDao extends BaseDao{

    public InnerCourseTeacherEntity getById(int id) {
        return get(InnerCourseTeacherEntity.class, id);

    }

    public InnerCourseTeacherEntity getByCourseId(int courseId){
        String hql = "from InnerCourseTeacherEntity as ict where ict.courseId = "+courseId;
        Query query = query(hql);
        return (InnerCourseTeacherEntity)query.list().get(0);
    }

}
