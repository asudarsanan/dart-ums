package com.dart.model;

public enum UserRole {
    ADMIN("Admin"),
    STAFF("Staff"),
    DRIVER("Driver"),
    USER("User");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
