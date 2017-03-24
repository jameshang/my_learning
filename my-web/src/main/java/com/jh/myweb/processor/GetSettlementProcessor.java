package com.jh.myweb.processor;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jh.myweb.service.BalanceService;
import com.jh.myweb.service.DBService;
import com.jh.myweb.service.MerchantService;
import com.jh.myweb.vo.Balance;
import com.jh.myweb.vo.Merchant;

public class GetSettlementProcessor implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(GetSettlementProcessor.class);

    private DBService dbService;

    private MerchantService merchantService;

    private BalanceService balanceService;

    private ProcessorStates states;

    private long merchantId;

    private final Map<String, Object> result = new LinkedHashMap<>();

    public GetSettlementProcessor(DBService dbService, MerchantService merchantService, BalanceService balanceService, long merchantId,
            ProcessorStates states) {
        this.dbService = dbService;
        this.merchantService = merchantService;
        this.balanceService = balanceService;
        this.merchantId = merchantId;
        this.states = states;
    }

    public Map<String, Object> getResult() {
        return this.result;
    }

    void hold(int state) throws InterruptedException {
        int waitTimeout = 0;
        while (waitTimeout < 6000 && this.states.getUpdateSettlementState() < state) {
            log.info("Hold for update settlement ... state=" + state);
            Thread.sleep(500);
            waitTimeout++;
        }
    }

    @Override
    public void run() {
        this.states.setGetSettlementState(ProcessorStates.INITIALIZED);
        try (Connection conn = this.dbService.getConnection()) {
            conn.setAutoCommit(false);
            log.info("Default isolation level: " + conn.getTransactionIsolation());
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            log.info("After set isolation level: " + conn.getTransactionIsolation());
            Merchant merchant = this.merchantService.get(this.merchantId, conn);
            this.states.setGetSettlementState(2);
            List<Balance> balanceListA = this.balanceService.getByMerchant(merchant.getId(), conn);
            this.states.setGetSettlementState(3);
            Thread.sleep(3000);
            List<Balance> balanceListB = this.balanceService.getByMerchant(merchant.getId(), conn);
            this.states.setGetSettlementState(4);
            conn.commit();
            this.states.setGetSettlementState(5);
            this.result.clear();
            this.result.put("merchant", merchant);
            this.result.put("balance A", balanceListA);
            this.result.put("balance B", balanceListB);
        } catch (Exception e) {
            log.error("Get settlement failed!", e);
        } finally {
            this.states.setGetSettlementState(ProcessorStates.FINISHED);
        }
    }

}
