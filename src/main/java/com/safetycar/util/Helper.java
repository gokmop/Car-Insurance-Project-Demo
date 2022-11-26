package com.safetycar.util;

import com.safetycar.enums.UserRoles;
import com.safetycar.models.Role;
import com.safetycar.models.users.User;

import java.util.HashSet;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;

import static com.safetycar.util.Constants.OfferConstants.MILLI_SECONDS_IN_YEAR;

public final class Helper {

    private Helper() {
    }

    public static Set<Role> getUserRoles(User user) {
        Set<Role> result = new HashSet<>();
        Role role = new Role();
        role.setUserName(user.getUserName());
        role.setAuthority(UserRoles.ROLE_USER.name());
        result.add(role);
        return result;
    }

    public static Set<Role> getAdminRoles(User user) {
        Set<Role> result = new HashSet<>();
        Role role = new Role();
        role.setUserName(user.getUserName());
        role.setAuthority(UserRoles.ROLE_AGENT.name());
        result.add(role);
        return result;
    }

    public static int getCorrectCarAge(LocalDate dateRegistered) {

        Calendar registeredCalendar = getCalendar(dateRegistered);

        long nowMilliSecs = System.currentTimeMillis();
        long registeredMilliSecs = getMilliSecs(registeredCalendar);
        long carAgeMilliSecs = nowMilliSecs - registeredMilliSecs;

        return (int) (carAgeMilliSecs / MILLI_SECONDS_IN_YEAR);
    }

    public static Calendar getCalendar(LocalDate localDate) {
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(java.sql.Date.valueOf(localDate));
        return nowCalendar;
    }

    public static long getMilliSecs(Calendar calendar) {
        return calendar.getTime().getTime();
    }

}
