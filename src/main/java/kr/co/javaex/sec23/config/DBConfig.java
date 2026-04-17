package kr.co.javaex.sec23.config; // 패키지명 config로 변경

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // 1. 지갑 경로
            String walletPath = "C:\\SQLDEV\\02_program\\Wallet_DinkDB";
            System.setProperty("oracle.net.tns_admin", walletPath);

            // 2. 접속 정보
            String tnsAlias = "dinkdb_medium";
            String dbUser = "DA2619";
            String dbPassword = "Data2619";

            // 3. 접속
            String jdbcUrl = "jdbc:oracle:thin:@dink_medium";
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}