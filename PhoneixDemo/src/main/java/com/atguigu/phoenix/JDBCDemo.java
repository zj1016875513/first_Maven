package com.atguigu.phoenix;

import java.sql.*;

/**
 * Created by VULCAN on 2021/4/23
 */
public class JDBCDemo {

    public static void main(String[] args) throws SQLException {

        String sql="select count(*) rowsnum from \"t1\"";

        Connection connection = DriverManager.getConnection("jdbc:phoenix:hadoop102:2181", "", "");

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        while(resultSet.next()){

            System.out.println(resultSet.getInt("rowsnum"));
//            System.out.println(resultSet.getInt(1));
        }

        resultSet.close();
        ps.close();
        connection.close();

    }
}
