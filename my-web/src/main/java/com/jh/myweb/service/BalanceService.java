package com.jh.myweb.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jh.myweb.exception.MyWebException;
import com.jh.myweb.vo.Balance;

@RestController
@RequestMapping("/balance")
public class BalanceService {

    @Autowired
    private DBService dbService;

    @Value("${sql.balance.add}")
    private String sqlAddBalance;

    @Value("${sql.balance.delete}")
    private String sqlDeleteBalance;

    @Value("${sql.balance.update}")
    private String sqlUpdateBalance;

    @Value("${sql.balance.get}")
    private String sqlGetBalance;

    @Value("${sql.balance.getByMerchant}")
    private String sqlGetByMerchant;

    @Value("${sql.balance.clear}")
    private String sqlClearBalance;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String doAdd(@RequestBody Balance balance) throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            Long id = this.add(balance, conn);
            return "ID: " + id;
        } catch (Exception e) {
            throw MyWebException.create("Add balance failed!", e);
        }
    }

    public long add(Balance balance, Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlAddBalance, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            pstmt.setLong(index++, balance.getMerchantId());
            pstmt.setInt(index++, balance.getType());
            pstmt.setBigDecimal(index++, balance.getBalance());
            pstmt.setInt(index++, balance.getStatus());
            pstmt.setInt(index++, balance.getTotal());
            pstmt.setString(index++, balance.getDescription());
            int count = pstmt.executeUpdate();
            long id = 0;
            if (count > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
                rs.close();
            }
            return id;
        } catch (Exception e) {
            throw MyWebException.create("Add balance failed!", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String doDelete(@PathVariable("id") Long id) throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            int count = this.delete(id, conn);
            return "Balance rows effected: " + count;
        } catch (Exception e) {
            throw MyWebException.create("Delete balance failed!", e);
        }
    }

    public int delete(Long id, Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlDeleteBalance)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw MyWebException.create("Delete balance failed!", e);
        }
    }

    public int clear(Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlClearBalance)) {
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw MyWebException.create("Delete balance failed!", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String doUpdate(@RequestBody Balance balance) throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            int count = this.update(balance, conn);
            return "Balance rows effected: " + count;
        } catch (Exception e) {
            throw MyWebException.create("Update balance failed!", e);
        }
    }

    public int update(Balance balance, Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlUpdateBalance)) {
            int index = 1;
            pstmt.setLong(index++, balance.getMerchantId());
            pstmt.setInt(index++, balance.getType());
            pstmt.setBigDecimal(index++, balance.getBalance());
            pstmt.setInt(index++, balance.getStatus());
            pstmt.setInt(index++, balance.getTotal());
            pstmt.setString(index++, balance.getDescription());
            pstmt.setLong(index++, balance.getId());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw MyWebException.create("Update balance failed!", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Balance doGet(@PathVariable("id") Long id) throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            return this.get(id, conn);
        } catch (Exception e) {
            throw MyWebException.create("Get balance failed!", e);
        }
    }

    public Balance get(Long id, Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlGetBalance)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            Balance balance = null;
            if (rs.next()) {
                balance = this.buildBalance(rs);
            }
            rs.close();
            return balance;
        } catch (Exception e) {
            throw MyWebException.create("Get balance failed!", e);
        }
    }

    public List<Balance> getByMerchant(Long merchantId, Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlGetByMerchant)) {
            pstmt.setLong(1, merchantId);
            ResultSet rs = pstmt.executeQuery();
            List<Balance> list = new ArrayList<>();
            while (rs.next()) {
                list.add(this.buildBalance(rs));
            }
            rs.close();
            return list;
        } catch (Exception e) {
            throw MyWebException.create("Get balance failed!", e);
        }
    }

    private Balance buildBalance(ResultSet rs) throws MyWebException {
        try {
            Balance balance = new Balance();
            balance.setId(rs.getLong("id"));
            balance.setMerchantId(rs.getLong("merchant_id"));
            balance.setType(rs.getInt("type"));
            balance.setBalance(rs.getBigDecimal("balance"));
            balance.setTotal(rs.getInt("total"));
            balance.setDescription(rs.getString("description"));
            balance.setStatus(rs.getInt("status"));
            balance.setCreatedAt(rs.getTimestamp("created_at"));
            balance.setUpdateAt(rs.getTimestamp("created_at"));
            return balance;
        } catch (Exception e) {
            throw MyWebException.create("Build balance failed!", e);
        }
    }

}
