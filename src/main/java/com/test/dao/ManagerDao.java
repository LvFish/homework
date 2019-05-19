package com.test.dao;

import com.test.model.ManagerEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liujiawang on 2019/5/17.
 */
@Repository("managerDao")
public class ManagerDao extends BaseDao{
    public ManagerEntity getById(int id) {
        return get(ManagerEntity.class, id);
    }

    public ManagerEntity getByUsername(String username){
        String hql = "from ManagerEntity as manager where manager.username = ?";
        Query query = query(hql);
        query.setString(0,username);
        List<ManagerEntity> list = query.list();
        if(list.size()==0)
            return null;
        return list.get(0);
    }
}
