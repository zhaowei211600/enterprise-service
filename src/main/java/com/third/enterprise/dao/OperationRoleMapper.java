package com.third.enterprise.dao;

import com.third.enterprise.bean.OperationRole;

public interface OperationRoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OperationRole record);

    int insertSelective(OperationRole record);

    OperationRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationRole record);

    int updateByPrimaryKey(OperationRole record);

    int checkExist(String roleName);
}