package com.jh.myweb.processor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jh.myweb.service.BalanceService;
import com.jh.myweb.service.DBService;
import com.jh.myweb.service.MerchantService;
import com.jh.myweb.vo.Balance;
import com.jh.myweb.vo.Merchant;

public class UpdateSettlementProcessor implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(UpdateSettlementProcessor.class);

    private DBService dbService;

    private MerchantService merchantService;

    private BalanceService balanceService;

    private ProcessorStates states;

    private long merchantId;

    public UpdateSettlementProcessor(DBService dbService, MerchantService merchantService, BalanceService balanceService, long merchantId,
            ProcessorStates states) {
        this.dbService = dbService;
        this.merchantService = merchantService;
        this.balanceService = balanceService;
        this.merchantId = merchantId;
        this.states = states;
    }

    void hold(int state) throws InterruptedException {
        int waitTimeout = 0;
        while (waitTimeout < 6 && this.states.getGetSettlementState() < state) {
            log.info("Hold for get settlement ... state=" + state);
            Thread.sleep(500);
            waitTimeout++;
        }
    }

    @Override
    public void run() {
        this.states.setUpdateSettlementState(ProcessorStates.INITIALIZED);
        try (Connection conn = this.dbService.getConnection()) {
            Thread.sleep(1000);
            conn.setAutoCommit(false);
            log.info("Default isolation level: " + conn.getTransactionIsolation());
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            log.info("After set isolation level: " + conn.getTransactionIsolation());
            Merchant merchant = this.merchantService.get(this.merchantId, conn);
            merchant.setRemark("Updated by settlement: " + new Date());
            this.merchantService.update(merchant, conn);

            this.states.setUpdateSettlementState(2);
            Thread.sleep(3000);

            List<Balance> balanceList = this.balanceService.getByMerchant(merchant.getId(), conn);
            for (Balance balance : balanceList) {
                balance.setBalance(balance.getBalance().add(BigDecimal.valueOf(50.0)));
                this.balanceService.update(balance, conn);
            }
            this.states.setUpdateSettlementState(3);
            conn.commit();
            this.states.setUpdateSettlementState(4);
        } catch (Exception e) {
            log.error("Update settlement failed!", e);
        } finally {
            this.states.setUpdateSettlementState(ProcessorStates.FINISHED);
        }
    }

}
