package org.ethan.demo.jvm.d03;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestLoader5 {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mytest", "username", "password");
    }
}
