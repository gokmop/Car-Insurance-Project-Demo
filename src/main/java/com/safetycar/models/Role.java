package com.safetycar.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "authorities")
@Entity(name = "Role")
@IdClass(value = RoleId.class)
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "username")
    private String userName;

    @Id
    @Column(name = "authority")
    private String authority;

    public Role() {
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role that = (Role) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, authority);
    }

    @Override
    public String toString() {
        return authority;
    }
}
