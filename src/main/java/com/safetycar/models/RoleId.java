package com.safetycar.models;

import java.io.Serializable;
import java.util.Objects;

public class RoleId implements Serializable {

    private String userName;
    private String authority;

    public RoleId() {
    }

    public RoleId(String userName, String authority) {
        this.userName = userName;
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleId that = (RoleId) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, authority);
    }
}
