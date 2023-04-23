package com.arman_jaurigue.logic_layer;

import com.arman_jaurigue.data_access.database.PlanAccessor;
import com.arman_jaurigue.data_objects.Plan;
import com.arman_jaurigue.data_objects.User;

import java.util.List;

public class PlanManager {
    private PlanAccessor planAccessor;
    public PlanManager()
    {
        planAccessor = new PlanAccessor();
    }

    public List<Plan> getAllPlansByUserId(int userId) {
        List<Plan> plans;

        try {
            plans = planAccessor.SelectAllPlansByUserId(userId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load plans", ex);
        }

        return plans;
    }

    public boolean addPlan(User user, Plan plan) {
        boolean success = false;
        plan.setUserId(user.getId());

        try {
            plan.setPlanId(planAccessor.InsertPlan(plan));
            success = true;
        } catch (Exception e)
        {
            throw new RuntimeException("Failed to add the plan", e);
        }

        return success;
    }
}
