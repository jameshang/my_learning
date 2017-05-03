package com.jh.myweb.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import com.jh.myweb.model.Balance;
import com.jh.myweb.model.Merchant;

@RestController
@RequestMapping("/merchant")
public class MerchantService {

    @Autowired
    private DBService dbService;

    @Autowired
    private BalanceService balanceService;

    @Value("${sql.merchant.add}")
    private String sqlAddMerchant;

    @Value("${sql.merchant.delete}")
    private String sqlDeleteMerchant;

    @Value("${sql.merchant.update}")
    private String sqlUpdateMerchant;

    @Value("${sql.merchant.get}")
    private String sqlGetMerchant;

    @Value("${sql.merchant.clear}")
    private String sqlClearMerchant;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String doAdd(@RequestBody Merchant merchant) throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            long id = this.add(merchant, conn);
            return "ID: " + id;
        } catch (Exception e) {
            throw MyWebException.create("Add merchant failed!", e);
        }
    }

    public long add(Merchant merchant, Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlAddMerchant, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            pstmt.setString(index++, merchant.getName());
            pstmt.setString(index++, merchant.getContact());
            pstmt.setString(index++, merchant.getAddress());
            pstmt.setInt(index++, merchant.getStatus());
            pstmt.setString(index++, merchant.getRemark());
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
            throw MyWebException.create("Add merchant failed!", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String doDelete(@PathVariable("id") Long id) throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            int count = this.delete(id, conn);
            return "Merchant rows effected: " + count;
        } catch (Exception e) {
            throw MyWebException.create("Delete merchant failed!", e);
        }
    }

    public int delete(Long id, Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlDeleteMerchant)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw MyWebException.create("Delete merchant failed!", e);
        }
    }

    public int clear(Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlClearMerchant)) {
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw MyWebException.create("Clear merchant failed!", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String doUpdate(@RequestBody Merchant merchant) throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            int count = this.update(merchant, conn);
            return "Merchant rows effected: " + count;
        } catch (Exception e) {
            throw MyWebException.create("Update merchant failed!", e);
        }
    }

    public int update(Merchant merchant, Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlUpdateMerchant)) {
            int index = 1;
            pstmt.setString(index++, merchant.getName());
            pstmt.setString(index++, merchant.getContact());
            pstmt.setString(index++, merchant.getAddress());
            pstmt.setInt(index++, merchant.getStatus());
            pstmt.setString(index++, merchant.getRemark());
            pstmt.setLong(index++, merchant.getId());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw MyWebException.create("Update merchant failed!", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Merchant doGet(@PathVariable("id") Long id) throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            return this.get(id, conn);
        } catch (Exception e) {
            throw MyWebException.create("Get merchant failed!", e);
        }
    }

    public Merchant get(Long id, Connection conn) throws MyWebException {
        try (PreparedStatement pstmt = conn.prepareStatement(this.sqlGetMerchant)) {
            Merchant merchant = new Merchant();
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                merchant.setId(rs.getLong("id"));
                merchant.setName(rs.getString("name"));
                merchant.setContact(rs.getString("contact"));
                merchant.setAddress(rs.getString("address"));
                merchant.setRemark(rs.getString("remark"));
                merchant.setStatus(rs.getInt("status"));
                merchant.setCreatedAt(rs.getTimestamp("created_at"));
                merchant.setUpdateAt(rs.getTimestamp("created_at"));
            }
            rs.close();
            return merchant;
        } catch (Exception e) {
            throw MyWebException.create("Get merchant failed!", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/balance/{id}", method = RequestMethod.GET)
    public List<Balance> doGetBalance(@PathVariable("id") Long merchantId) throws MyWebException {
        try (Connection conn = this.dbService.getConnection()) {
            return this.balanceService.getByMerchant(merchantId, conn);
        } catch (Exception e) {
            throw MyWebException.create("Get balance failed!", e);
        }
    }

}
