package com.third.enterprise.service;

import com.third.enterprise.bean.OperationRole;

public interface IOperationRoleService {

    boolean checkExist(String roleName);

    Boolean save(OperationRole role);

    Boolean update(OperationRole role);

    Boolean checkRoleBind(Integer roleId);

    Boolean delete(Integer roleId);
}
