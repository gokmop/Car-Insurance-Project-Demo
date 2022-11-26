package com.safetycar.repositories.impl;

import com.safetycar.models.Car;
import com.safetycar.models.Model;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import com.safetycar.repositories.CarRepository;
import com.safetycar.repositories.OfferExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.safetycar.util.Constants.BaseAmountConstants.CAPACITY;
import static com.safetycar.util.Constants.Views.QueryConstants.FIRST_REGISTRATION;

@Component
public class OfferExistsImpl implements OfferExists {

    public static final String MODEL_YEAR_BRAND = "modelYearBrand";

//    @PersistenceContext
//    private final EntityManager entityManager;
//
//    private final CarRepository carRepository;

    @Autowired
    public OfferExistsImpl(
//            CarRepository carRepository,
//                           EntityManager entityManager
    ) {
//        this.carRepository = carRepository;
//        this.entityManager = entityManager;
    }

    @Override
    public boolean exists(final Offer newOffer, final User userToCheckOffersFrom) {
        if (newOffer == null) return false;
        final Car newCar = newOffer.getCar();
        final Set<Car> usersCars = userToCheckOffersFrom
                .getOffers()
                .stream()
                .map(Offer::getCar)
                .collect(Collectors.toSet());
        userToCheckOffersFrom
                .getUserDetails()
                .getPolicies().stream()
                .map(Policy::getCar).forEach(usersCars::add);
        return usersCars.contains(newCar);

//        final int capacity = car.getCapacity();
//        final LocalDate registration = car.getFirstRegistration();
//        final Model model = car.getModelYearBrand();
//
//        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        final CriteriaQuery<Car> cq = cb.createQuery(Car.class);
//        final Root<Car> root = cq.from(Car.class);

//        final List<Predicate> predicates = new LinkedList<>();
//        predicates.add(cb.equal(root.get(CAPACITY), capacity));
//        predicates.add(cb.equal(root.get(FIRST_REGISTRATION), registration));
//        predicates.add(cb.equal(root.get(MODEL_YEAR_BRAND).get("id"), model.getId()));
//        predicates.add(root.in(usersCars));
//        predicates.add(cb.isMember(car,usersCars));
//        final Predicate and = cb.and(predicates.toArray(Predicate[]::new));
//        //deprecated
//        final TypedQuery<Car> resultQuery = entityManager.createQuery(cq.select(root).where(and));
//        final List<Car> resultList = resultQuery.getResultList();
//        return resultList.size() > 0;
    }
}
