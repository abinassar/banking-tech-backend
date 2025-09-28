package com.cursos.springsecuritycourse.util;

import java.util.Arrays;
import java.util.List;

public enum Role {

    REGULAR_USER(Arrays.asList()),
    ADMINISTRATOR(Arrays.asList(Permission.CREATE_USERS,
                                Permission.SEE_USER_SESSIONS));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
