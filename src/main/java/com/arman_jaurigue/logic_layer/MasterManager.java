package com.arman_jaurigue.logic_layer;

public class MasterManager {
    private static MasterManager masterManager;
    private UserManager userManager;
    private PlanManager planManager;

    public static MasterManager getMasterManager()
    {
        if (masterManager == null)
        {
            masterManager = new MasterManager();
        }
        return masterManager;
    }

    private MasterManager()
    {
        userManager = new UserManager();
        planManager = new PlanManager();
    }

    public UserManager getUserManager() {
        return userManager;
    }
    public PlanManager getPlanManager() {
        return planManager;
    }
}
