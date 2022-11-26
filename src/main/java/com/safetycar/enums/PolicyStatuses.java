package com.safetycar.enums;

public enum PolicyStatuses {
    APPROVED(1),
    PENDING(2),
    ACTIVE(3),
    REJECTED(4),
    CANCELED(5),
    EXPIRED(6);
    private final int id;

    PolicyStatuses(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
