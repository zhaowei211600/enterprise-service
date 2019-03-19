package com.third.trading.service;

import com.third.trading.bean.OperationMenu;
import com.third.trading.util.Tree;
import com.third.trading.util.TreeNode;

import java.util.List;

public interface IOperationMenuService {

    List<TreeNode> getAllMenus(Integer roleId);

    List<Tree> loadMenus(Integer userId);

    List<OperationMenu> findMenusByUserId(Integer userId);

}
