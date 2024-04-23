package com.solidcode.offers.util;

public enum OfferType {
    DISCOUNT,
    CASHBACK;

    public static boolean contains(final String type) {
        for (OfferType offerType : OfferType.values()) {
            if (offerType.name().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
