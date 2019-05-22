package com.third.enterprise.bean.request;

public class OrderListRequest {

    private String productName;

    private String type;

    private String attr;

    private String commitCheck;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getCommitCheck() {
        return commitCheck;
    }

    public void setCommitCheck(String commitCheck) {
        this.commitCheck = commitCheck;
    }
}
