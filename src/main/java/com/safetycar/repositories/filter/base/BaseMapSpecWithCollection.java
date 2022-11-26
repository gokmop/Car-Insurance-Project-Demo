package com.safetycar.repositories.filter.base;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public abstract class BaseMapSpecWithCollection<T> extends BaseMapSpec<T> {

    private final Collection<T> collection;

    protected BaseMapSpecWithCollection() {
        super();
        this.collection = new LinkedList<>();
    }

    protected BaseMapSpecWithCollection(Map<String, String> specs) {
        super(specs);
        this.collection = new LinkedList<>();
    }

    protected BaseMapSpecWithCollection(Collection<T> collection, Map<String, String> specs) {
        super(specs);
        this.collection = collection;
    }

    protected BaseMapSpecWithCollection(Collection<T> collection) {
        super();
        this.collection = collection;
    }

    public abstract Predicate toPredicate(
            Root<T> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    );

    public abstract String getSortParam(String param);

    public final Collection<T> getCollection() {
        return new LinkedList<>(collection);
    }
}
