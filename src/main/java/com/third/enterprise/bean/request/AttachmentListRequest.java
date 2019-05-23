package com.third.enterprise.bean.request;

public class AttachmentListRequest {

    private Integer userId;

    private Integer checkOrderId;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCheckOrderId() {
        return checkOrderId;
    }

    public void setCheckOrderId(Integer checkOrderId) {
        this.checkOrderId = checkOrderId;
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
}
