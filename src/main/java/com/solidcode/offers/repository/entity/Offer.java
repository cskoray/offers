package com.solidcode.offers.repository.entity;

import java.sql.Timestamp;

import com.solidcode.offers.util.OfferType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "offer_key", nullable = false)
    private String offerKey;

    @Column(name = "offer_description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_type")
    private OfferType type;

    @Column(name = "discount_amount")
    private double discountAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_key", referencedColumnName = "merchant_key")
    private Merchant merchant;

    @Column(name = "expiry_date", nullable = false, updatable = false)
    private Timestamp expiryDate;
}
