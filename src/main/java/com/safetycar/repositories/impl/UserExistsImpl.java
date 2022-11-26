package com.safetycar.repositories.impl;

import com.safetycar.models.users.UserDetails;
import com.safetycar.models.wrappers.UserDuplicateWrapper;
import com.safetycar.repositories.UserExists;
import com.safetycar.util.Constants;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

import static com.safetycar.models.users.User.USERNAME;
import static com.safetycar.models.users.UserDetails.*;
import static com.safetycar.util.Constants.Views.QueryConstants.*;

@Component
public class UserExistsImpl implements UserExists {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsByUserDetails(UserDetails entity) {
        Class<UserDuplicateWrapper> wrapperClass = UserDuplicateWrapper.class;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserDuplicateWrapper> cq = cb.createQuery(wrapperClass);
        Root<UserDetails> userRoot = cq.from(UserDetails.class);

        List<Path<Object>> paths = new LinkedList<>();
        paths.add(userRoot.get(USERNAME));
        paths.add(userRoot.get(TELEPHONE));
        paths.add(userRoot.get(FIRST_NAME));
        paths.add(userRoot.get(LAST_NAME));
        paths.add(userRoot.get(ADDRESS));
        /*
        exclude the userDetails with this id
         */
        Predicate excludeThis = cb.notEqual(paths.get(0), entity.getUserName());
        cq.multiselect(paths.toArray(Path[]::new)).where(excludeThis);
        /*
        users are considered duplicates when their telephone is not unique
        or the combination of their firstName, lastName and address is not unique
         */
        List<UserDuplicateWrapper> wrappers = entityManager.createQuery(cq).getResultList();
        for (UserDuplicateWrapper wrapper : wrappers) {
            if (wrapper.duplicate(entity)) {
                return true;
            }
        }
        return false;
    }
}
