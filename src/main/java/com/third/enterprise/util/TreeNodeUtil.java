package com.third.enterprise.util;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeNodeUtil {


    /**
     * 将父子关系集合转换成树结构(运营平台)
     * @param nodeList
     * @return
     */
    public static List<Tree> buildNodesTree(List<Tree> nodeList){
        if (nodeList == null) {
            throw new IllegalArgumentException("列表不能为空 ");
        }
        List<Tree> rootNodes = findRootNodes(nodeList);
        if (rootNodes == null || rootNodes.isEmpty()) {
            throw new IllegalArgumentException("无法获取一级目录");
        }
        for (Tree parent : rootNodes) {
            for (Tree it : nodeList) {
                if (it.getParentId().equals(parent.getId())) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<Tree>());
                    }
                    parent.getChildren().add(it);
                }
            }
        }
        return rootNodes;
    }

    /**
     * 运营平台树形菜单
     * @param nodeList
     * @return
     */
    private static List<Tree> findRootNodes(List<Tree> nodeList) {
        List nodeIds = (List) CollectionUtils.collect(
                nodeList,new Transformer() {
                    @Override
                    public Object transform(Object arg0) {
                        Tree node = (Tree) arg0;
                        return node.getId();
                    }
                });
        List<Tree> rootNodes = new LinkedList<Tree>();
        for(Tree node : nodeList){
            if(!nodeIds.contains(node.getParentId())){
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

    /**
     * 将父子关系集合转换成树结构
     * @param nodeList
     * @return
     */
    public static List<Tree> buildNodeTree(List<Tree> nodeList){
        if (nodeList == null) {
            throw new IllegalArgumentException("列表不能为空 ");
        }
        List<Tree> rootNodes = findRootNode(nodeList);
        if (rootNodes == null || rootNodes.isEmpty()) {
            throw new IllegalArgumentException("无法获取一级目录");
        }
        for (Tree parent : rootNodes) {
            for (Tree it : nodeList) {
                if (it.getParentId().equals(parent.getId())) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<Tree>());
                    }
                    parent.getChildren().add(it);
                }
            }
        }
        return rootNodes;
    }


    private static List<Tree> findRootNode(List<Tree> nodeList) {
        List nodeIds = (List) CollectionUtils.collect(
                nodeList,new Transformer() {
            @Override
            public Object transform(Object arg0) {
                Tree node = (Tree) arg0;
                return node.getId();
            }
        });
        List<Tree> rootNodes = new LinkedList<>();
        for(Tree node : nodeList){
            if(!nodeIds.contains(node.getParentId())){
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }


}
