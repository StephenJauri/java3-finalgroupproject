package com.arman_jaurigue.logic_layer;

import com.arman_jaurigue.data_access.database.StopAccessor;
import com.arman_jaurigue.data_objects.Stop;
import com.arman_jaurigue.data_objects.User;

import java.util.List;

public class StopManager {
    private StopAccessor stopAccessor;
    public StopManager() {
        stopAccessor = new StopAccessor();
    }

    public List<Stop> getAllStopsByPlanId(int planId) {
        List<Stop> stops;
        try {
            stops = stopAccessor.selectAllStopsByPlanId(planId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load stops", ex);
        }
        return stops;
    }

    public Stop getStopByStopId(int stopId) {
        Stop stop;
        try {
            stop = stopAccessor.selectStopByStopId(stopId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load stops", ex);
        }
        return stop;
    }

    public boolean addStop(User user, Stop stop) {
        boolean success = false;

        stop.setUserId(user.getId());

        try {
            stop.setStopId(stopAccessor.InsertStop(stop));
            success = true;
        } catch (Exception e)
        {
            throw new RuntimeException("Failed to add the stop", e);
        }

        return success;
    }

    public boolean editStopStatusByStopId(int stopId, boolean status) {
        boolean result = false;
        try {
            result = stopAccessor.updateStopStatusByStopId(stopId, status) == 1;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load stops", ex);
        }
        return result;
    }
}
