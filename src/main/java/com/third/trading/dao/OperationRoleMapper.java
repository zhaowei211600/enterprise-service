package com.third.trading.dao;

import com.third.trading.bean.OperationRole;
import com.third.trading.bean.request.OperationRoleRequest;

import java.util.List;

public interface OperationRoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OperationRole record);

    int insertSelective(OperationRole record);

    OperationRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationRole record);

    int updateByPrimaryKey(OperationRole record);

    int checkExist(String roleName);

    List<OperationRole> listOperationRole(OperationRoleRequest request);

    Integer listOperationRoleCount(OperationRoleRequest request);

    OperationRole findRoleById(Integer roleId);

    List<OperationRole> allRoles();
}