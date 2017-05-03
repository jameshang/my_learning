package com.jh.myweb.processor;

public class ProcessorStates {

    public static final int INITIALIZED = 0;
    public static final int FINISHED = Integer.MAX_VALUE;

    private int updateSettlementState;
    private int getSettlementState;

    public int getUpdateSettlementState() {
        return updateSettlementState;
    }

    public void setUpdateSettlementState(int updateSettlementState) {
        this.updateSettlementState = updateSettlementState;
    }

    public int getGetSettlementState() {
        return getSettlementState;
    }

    public void setGetSettlementState(int getSettlementState) {
        this.getSettlementState = getSettlementState;
    }

}
