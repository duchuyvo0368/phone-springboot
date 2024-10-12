package com.windshop.phone.enums;

public enum StatusOrder {

    BEING_PREPARE(0),
    SHIPPING(1),
    RECEIVED(2),
    CANCELLED(3);

    private final Integer code;

    StatusOrder(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static StatusOrder findByCode(Integer code) {
        for (StatusOrder s : values()) {
            if (s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
}
