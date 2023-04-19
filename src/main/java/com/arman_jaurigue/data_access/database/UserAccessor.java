package com.arman_jaurigue.data_access.database;

import com.arman_jaurigue.data_objects.User;

import java.sql.*;

public class UserAccessor {
    public int addUser(User user, String password) throws SQLException {
        int numRowsAffected = 0;

        try(Connection connection = DbConnection.getConnection()) {
            if(connection.isValid(2)) {
                CallableStatement callableStatement = connection.prepareCall("{CALL sp_add_user(?,?,?,?)}");
                callableStatement.setString(1, user.getFirstName());
                callableStatement.setString(2, user.getLastName());
                callableStatement.setString(3, user.getEmail());
                callableStatement.setString(4, password);
                numRowsAffected = callableStatement.executeUpdate();
                callableStatement.close();
            }
        } catch(SQLException e) {
            throw e;
        }


        return numRowsAffected;
    }

    public int selectEmailCountByEmail(String email) throws SQLException {
        int emails = 0;

        try(Connection connection = DbConnection.getConnection()) {
            if(connection.isValid(2)) {
                CallableStatement callableStatement = connection.prepareCall("{CALL sp_select_email_count_by_email(?)}");
                callableStatement.setString(1, email);
                ResultSet resultSet = callableStatement.executeQuery();
                resultSet.next();
                emails = resultSet.getInt(1);
                resultSet.close();
                callableStatement.close();
            }
        } catch(SQLException e) {
            throw e;
        }

        return emails;
    }

    public int selectUserCountByEmailAndPassword(String email, String password) {
        int rows = 0;



        return rows;
    }

    public User selectUserById(int id) {
        User user = null;


        return user;
    }

    public User selectUserByEmail(String email) {
        User user = null;

        return user;
    }
}
