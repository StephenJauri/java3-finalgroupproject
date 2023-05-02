package com.arman_jaurigue.models;

import com.arman_jaurigue.data_objects.data_annotations.Required;
import com.arman_jaurigue.data_objects.enumerations.Roles;

public class InviteUserModel {
    @Required
    private int planId;
    @Required
    private String userEmail;
    private String userEmailError;
    @Required
    private Roles role;
    private String roleError;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getUserEmailError() {
        return userEmailError;
    }

    public void setUserEmailError(String userEmailError) {
        this.userEmailError = userEmailError;
    }

    public String getRoleError() {
        return roleError;
    }

    public void setRoleError(String roleError) {
        this.roleError = roleError;
    }
}
