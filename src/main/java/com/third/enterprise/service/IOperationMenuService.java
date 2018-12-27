package com.third.enterprise.service;

import com.third.enterprise.bean.OperationMenu;
import com.third.enterprise.util.Tree;

import java.util.List;

public interface IOperationMenuService {

    List<Tree> getAllMenus(Integer roleId);

    List<Tree> loadMenus(Integer userId);

    List<OperationMenu> findMenusByUserId(Integer userId);

}
