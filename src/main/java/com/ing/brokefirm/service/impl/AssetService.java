package com.ing.brokefirm.service.impl;

import com.ing.brokefirm.exception.InsufficientBalanceException;
import com.ing.brokefirm.mapper.AssetMapper;
import com.ing.brokefirm.model.Asset;
import com.ing.brokefirm.model.Customer;
import com.ing.brokefirm.model.Deposit;
import com.ing.brokefirm.model.Withdraw;
import com.ing.brokefirm.repository.AssetRepository;
import com.ing.brokefirm.repository.entity.AssetEntity;
import com.ing.brokefirm.service.IAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AssetService implements IAssetService {
    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;
    private final CustomerService customerService;

    public void depositMoney(Deposit deposit, Principal principal) {
        Customer customer = customerService.findByUsername(principal.getName());
        if (!customer.getRole().equals("ADMIN")) {
            deposit.setCustomerId(customer.getId());
        }

        if (deposit.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientBalanceException("Deposit money should be positive");
        }

        AssetEntity tryAsset = getOrCreateTryAsset(deposit.getCustomerId());
        tryAsset.setSize(tryAsset.getSize().add(deposit.getAmount()));
        tryAsset.setUsableSize(tryAsset.getUsableSize().add(deposit.getAmount()));
        assetRepository.save(tryAsset);
    }

    public void withdrawMoney(Withdraw withdraw, Principal principal) {
        Customer customer = customerService.findByUsername(principal.getName());
        if (!customer.getRole().equals("ADMIN")) {
            withdraw.setCustomerId(customer.getId());
        }

        if (withdraw.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdraw money should be positive");
        }

        AssetEntity tryAsset = getOrCreateTryAsset(withdraw.getCustomerId());

        if (tryAsset.getUsableSize().compareTo(withdraw.getAmount()) < 0) {
            throw new IllegalArgumentException("Customer do not have enough usable money");
        }

        tryAsset.setSize(tryAsset.getSize().subtract(withdraw.getAmount()));
        tryAsset.setUsableSize(tryAsset.getUsableSize().subtract(withdraw.getAmount()));
        assetRepository.save(tryAsset);
    }

    public List<Asset> listAssetsByCustomerId(Long customerId, Principal principal) {
        Customer customer = customerService.findByUsername(principal.getName());
        if (!customer.getRole().equals("ADMIN")) {
            customerId = customer.getId();
        }
        return assetRepository.findByCustomerId(customerId).stream().map(assetMapper::assetEntityToAsset).toList();
    }

    public List<Asset> listAssetsByCustomerIdAndAssetName(Long customerId, String assetName) {
        return assetRepository.findByCustomerIdAndAssetName(customerId, assetName).stream().map(assetMapper::assetEntityToAsset).toList();
    }

    private AssetEntity getOrCreateTryAsset(Long customerId) {
        Optional<AssetEntity> optionalTryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY");
        return optionalTryAsset.orElseGet(() -> {
            AssetEntity newTryAsset = new AssetEntity();
            newTryAsset.setCustomerId(customerId);
            newTryAsset.setAssetName("TRY");
            newTryAsset.setSize(BigDecimal.ZERO);
            newTryAsset.setUsableSize(BigDecimal.ZERO);
            return assetRepository.save(newTryAsset);
        });
    }
}

