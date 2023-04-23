package com.arman_jaurigue.data_access.database;

import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.data_objects.enumerations.Privileges;
import com.arman_jaurigue.data_objects.enumerations.Status;
import org.jetbrains.annotations.NotNull;

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

    public int selectUserCountByEmailAndPassword(String email, String password) throws SQLException {
        int count = 0;

        try(Connection connection = DbConnection.getConnection()) {
            if(connection.isValid(2)) {
                CallableStatement callableStatement = connection.prepareCall("{CALL sp_select_user_count_by_email_and_password(?,?)}");
                callableStatement.setString(1, email);
                callableStatement.setString(2, password);
                ResultSet resultSet = callableStatement.executeQuery();
                resultSet.next();
                count = resultSet.getInt(1);
                resultSet.close();
                callableStatement.close();
            }
        } catch(SQLException e) {
            throw e;
        }

        return count;
    }

    public User selectUserById(int id) throws SQLException {
        User user = null;

        try(Connection connection = DbConnection.getConnection()) {
            if(connection.isValid(2)) {
                CallableStatement callableStatement = connection.prepareCall("{CALL sp_select_user_by_id(?)}");
                callableStatement.setInt(1, id);
                user = getUser(callableStatement);
            }
        } catch(SQLException e) {
            throw e;
        }

        return user;
    }

    @NotNull
    private User getUser(CallableStatement callableStatement) throws SQLException {
        User user;
        ResultSet resultSet = callableStatement.executeQuery();
        if (resultSet.next())
        {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));
            user.setStatus(Enum.valueOf(Status.class,resultSet.getString("status")));
            user.setPrivileges(Enum.valueOf(Privileges.class, resultSet.getString("privileges")));
            resultSet.close();
            callableStatement.close();
        }
        else {
            throw new RuntimeException("No user with that email");
        }
        return user;
    }

    public User selectUserByEmail(String email) throws SQLException {
        User user = null;

        try(Connection connection = DbConnection.getConnection()) {
            if(connection.isValid(2)) {
                CallableStatement callableStatement = connection.prepareCall("{CALL sp_select_user_by_email(?)}");
                callableStatement.setString(1, email);
                user = getUser(callableStatement);
            }
        } catch(SQLException e) {
            throw e;
        }

        return user;
    }
}
