package com.third.enterprise.bean.response;

import java.math.BigDecimal;

public class ProductStatResponse {

    private Integer totalCount;

    private Integer doingCount;

    private Integer finishCount;

    private BigDecimal settleAmount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getDoingCount() {
        return doingCount;
    }

    public void setDoingCount(Integer doingCount) {
        this.doingCount = doingCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }


    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }
}
