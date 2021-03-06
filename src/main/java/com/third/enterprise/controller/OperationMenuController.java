package com.third.enterprise.controller;

import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.IOperationMenuService;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operation/menu")
public class OperationMenuController {

    private static final Logger logger = LoggerFactory.getLogger(OperationMenuController.class);

    @Autowired
    private IOperationMenuService menuService;

    @PostMapping(value = "/all")
    public UnifiedResult<List<Tree>> getAllMenus(Integer roleId) {
        List<Tree> allMenus = menuService.getAllMenus(roleId);
        if(allMenus != null && allMenus.size() > 0){
            return  UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, allMenus);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }
}
