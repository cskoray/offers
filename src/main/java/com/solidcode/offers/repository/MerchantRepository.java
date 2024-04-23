package com.solidcode.offers.repository;

import java.util.Optional;

import com.solidcode.offers.repository.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Optional<Merchant> findByMerchantKey(String merchantKey);
}
