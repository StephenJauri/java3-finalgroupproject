package com.arman_jaurigue.data_access.database;

import com.arman_jaurigue.data_objects.Plan;
import com.arman_jaurigue.data_objects.Stop;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StopAccessor {
    public List<Stop> selectAllStopsByPlanId(int planId) {
        List<Stop> stops = new ArrayList<Stop>();
        try(Connection connection = DbConnection.getConnection()) {
            if(connection.isValid(2)) {
                CallableStatement callableStatement = connection.prepareCall("{CALL sp_select_stops_by_planId(?)}");
                callableStatement.setInt(1, planId);
                ResultSet resultSet = callableStatement.executeQuery();
                while(resultSet.next()) {
                    Stop stop = new Stop();
                    stop.setStopId(resultSet.getInt("StopId"));
                    stop.setPlanId(resultSet.getInt("PlanId"));
                    stop.setUserId(resultSet.getInt("UserId"));
                    stop.setName(resultSet.getString("Name"));
                    stop.setLocation(resultSet.getString("Location"));
                    stop.setTime(resultSet.getTimestamp("Time"));
                    stop.setDescription(resultSet.getString("Description"));
                    boolean bool = resultSet.getBoolean("Status");
                    if(resultSet.wasNull()) {
                        stop.setStatus(null);
                    } else {
                        stop.setStatus(bool);
                    }

//                    Stop stop = new Stop(resultSet.getInt("StopId"), resultSet.getInt("PlanId"), resultSet.getInt("UserId"),
//                            resultSet.getString("Name"), resultSet.getString("Location"), resultSet.getTimestamp("Time"),
//                            resultSet.getString("Description"), resultSet.getBoolean("Status"));
                    stops.add(stop);
                }
                resultSet.close();
                callableStatement.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stops;
    }
}
