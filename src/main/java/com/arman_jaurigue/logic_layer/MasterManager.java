package com.arman_jaurigue.logic_layer;

public class MasterManager {
    private static MasterManager masterManager;
    private UserManager userManager;

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
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
