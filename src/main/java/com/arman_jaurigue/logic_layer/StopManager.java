package com.arman_jaurigue.logic_layer;

import com.arman_jaurigue.data_access.database.StopAccessor;
import com.arman_jaurigue.data_objects.Stop;

import java.util.List;

public class StopManager {
    private StopAccessor stopAccessor;
    public StopManager() {
        stopAccessor = new StopAccessor();
    }

    public List<Stop> getAllStopsByPlanId(int planId) {
        List<Stop> stops;
        try {
            stops = stopAccessor.SelectAllStopsByPlanId(planId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load stops", ex);
        }
        return stops;
    }
}
