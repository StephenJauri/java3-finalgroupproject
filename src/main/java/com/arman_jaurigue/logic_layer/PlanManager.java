package com.arman_jaurigue.logic_layer;

import com.arman_jaurigue.data_access.database.PlanAccessor;
import com.arman_jaurigue.data_objects.Plan;

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

    public Plan getPlanByPlanId(int planId) {
        Plan plan;
        try {
            plan = planAccessor.SelectPlanByPlanId(planId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load the plan", ex);
        }
        return plan;
    }

    public int AddPlan(Plan plan) {
        int planId = 0;
        try {
            planId = planAccessor.InsertPlan(plan);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to add the plan", ex);
        }
        return planId;
    }
}
