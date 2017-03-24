package com.jh.myweb.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jh.myweb.exception.MyWebException;

@Service
public class DBService {

    @Value("${db.sauron.master.driver}")
    private String driver;

    @Value("${db.sauron.master.url}")
    private String url;

    @Value("${db.sauron.master.username}")
    private String uid;

    @Value("${db.sauron.master.password}")
    private String pwd;

    public Connection getConnection() throws MyWebException {
        try {
            Class.forName(this.driver);
            return DriverManager.getConnection(this.url, this.uid, this.pwd);
        } catch (ClassNotFoundException | SQLException e) {
            throw MyWebException.create("Connection create failed!", e);
        }
    }

}
