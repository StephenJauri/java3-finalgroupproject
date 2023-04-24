package com.arman_jaurigue.data_access.database;

import com.arman_jaurigue.data_objects.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanAccessor {
    public List<Plan> selectAllPlansByUserId(int userId) {
        List<Plan> plans = new ArrayList<Plan>();
        try(Connection connection = DbConnection.getConnection()) {
            if(connection.isValid(2)) {
                CallableStatement callableStatement = connection.prepareCall("{CALL sp_select_plans_by_userId(?)}");
                callableStatement.setInt(1, userId);
                ResultSet resultSet = callableStatement.executeQuery();
                while(resultSet.next()) {
                    Plan plan = new Plan();
                    plan.setPlanId(resultSet.getInt("PlanId"));
                    plan.setUserId(resultSet.getInt("UserId"));
                    plan.setName(resultSet.getString("Name"));
                    plan.setStartDate(resultSet.getDate("StartDate").toLocalDate().atTime(resultSet.getTime("StartDate").toLocalTime()));
                    plan.setStartDate(resultSet.getDate("EndDate").toLocalDate().atTime(resultSet.getTime("EndDate").toLocalTime()));

                    plans.add(plan);
                }
                resultSet.close();
                callableStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return plans;
    }

    public Plan selectPlanByPlanId(int planId) {
        Plan plan = new Plan();
        try(Connection connection = DbConnection.getConnection()) {
            if(connection.isValid(2)) {
                CallableStatement callableStatement = connection.prepareCall("{CALL sp_select_plan_by_planId(?)}");
                callableStatement.setInt(1, planId);
                ResultSet resultSet = callableStatement.executeQuery();
                while(resultSet.next()) {
                    plan.setPlanId(resultSet.getInt("PlanId"));
                    plan.setUserId(resultSet.getInt("UserId"));
                    plan.setName(resultSet.getString("Name"));
                    plan.setStartDate(resultSet.getDate("StartDate").toLocalDate().atTime(resultSet.getTime("StartDate").toLocalTime()));
                    plan.setStartDate(resultSet.getDate("EndDate").toLocalDate().atTime(resultSet.getTime("EndDate").toLocalTime()));
                }
                resultSet.close();
                callableStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return plan;
    }

    public int insertPlan(Plan plan) {
        int planId = 0;
        try(Connection connection = DbConnection.getConnection()) {
            if(connection.isValid(2)) {
                CallableStatement callableStatement = connection.prepareCall("{CALL sp_insert_plan(?,?,?,?)}");
                callableStatement.setInt(1, plan.getUserId());
                callableStatement.setString(2, plan.getName());
                callableStatement.setTimestamp(3, Timestamp.valueOf(plan.getStartDate()));
                callableStatement.setTimestamp(4, Timestamp.valueOf(plan.getEndDate()));
                ResultSet resultSet = callableStatement.executeQuery();
                while(resultSet.next()) {
                    planId = resultSet.getInt("LAST_INSERT_ID()");
                }

                callableStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return planId;
    }
}
