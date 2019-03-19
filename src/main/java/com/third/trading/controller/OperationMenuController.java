package com.third.trading.controller;

import com.third.trading.bean.response.UnifiedResult;
import com.third.trading.bean.response.UnifiedResultBuilder;
import com.third.trading.service.IOperationMenuService;
import com.third.trading.util.Constants;
import com.third.trading.util.Tree;
import com.third.trading.util.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation/menu")
public class OperationMenuController {

    private static final Logger logger = LoggerFactory.getLogger(OperationMenuController.class);

    @Autowired
    private IOperationMenuService menuService;

    @PostMapping(value = "/all")
    public UnifiedResult<List<TreeNode>> getAllMenus(Integer roleId) {
        List<TreeNode> allMenus = menuService.getAllMenus(roleId);
        if(allMenus != null && allMenus.size() > 0){
            return  UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, allMenus);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    /**
     * 加载管理员菜单
     */
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public UnifiedResult findMenusByUserId(@RequestAttribute("userId") Integer userId) {
        List<Tree> userMenuTree = menuService.loadMenus(userId);
        if(userMenuTree != null && userMenuTree.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, userMenuTree);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

}
