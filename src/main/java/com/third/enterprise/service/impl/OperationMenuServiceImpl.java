package com.third.enterprise.service.impl;

import com.third.enterprise.bean.OperationMenu;
import com.third.enterprise.dao.OperationMenuMapper;
import com.third.enterprise.service.IOperationMenuService;
import com.third.enterprise.util.MenuTreeUtil;
import com.third.enterprise.util.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("operationService")
public class OperationMenuServiceImpl implements IOperationMenuService{

    private static final Logger logger = LoggerFactory.getLogger(OperationMenuServiceImpl.class);

    @Resource
    private OperationMenuMapper menuMapper;

    @Override
    public List<Tree> getAllMenus(Integer roleId) {
        List<OperationMenu> allMenus = menuMapper.getAllMenus(roleId);
        if(!allMenus.isEmpty()){
            return MenuTreeUtil.generateMenuTree(allMenus);
        }
        return null;
    }


}
