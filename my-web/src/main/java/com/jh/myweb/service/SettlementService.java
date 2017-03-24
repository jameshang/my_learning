package com.jh.myweb.service;

import java.math.BigDecimal;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jh.myweb.exception.MyWebException;
import com.jh.myweb.vo.Balance;
import com.jh.myweb.vo.Merchant;

@RestController
@RequestMapping("/settlement")
public class SettlementService {

    @Autowired
    private DBService dbService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private BalanceService balanceService;

    @ResponseBody
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String doClear() throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            int[] count = this.clear(conn);
            conn.commit();
            return "Merchant rows effected: " + count[0] + ", Balance rows effected: " + count[1];
        } catch (Exception e) {
            throw MyWebException.create("Clear settlement failed!", e);
        }
    }

    public int[] clear(Connection conn) throws MyWebException {
        int[] rtn = new int[2];
        rtn[0] = this.merchantService.clear(conn);
        rtn[1] = this.balanceService.clear(conn);
        return rtn;
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String doCreate() throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            int mCount = 0;
            int bCount = 0;
            for (int i = 1; i <= 9; i++) {
                Merchant merchant = new Merchant();
                merchant.setName("Merchant " + i);
                merchant.setContact("+86 1391666880" + i);
                merchant.setAddress("Shanghai China " + i);
                merchant.setStatus(1);
                merchant.setRemark("This is merchant " + i);

                long merchantId = this.merchantService.add(merchant, conn);
                mCount++;

                for (int j = 1; j <= 9; j++) {
                    Balance balance = new Balance();
                    balance.setMerchantId(merchantId);
                    balance.setType((j - 1) % 3 + 1);
                    balance.setBalance(BigDecimal.valueOf(100.0));
                    balance.setTotal(100);
                    balance.setStatus(1);
                    balance.setDescription("This is my money " + j);

                    this.balanceService.add(balance, conn);
                    bCount++;
                }
            }
            conn.commit();
            return "Merchant rows effected: " + mCount + ", Balance rows effected: " + bCount;
        } catch (Exception e) {
            throw MyWebException.create("Create settlement failed!", e);
        }
    }

}
