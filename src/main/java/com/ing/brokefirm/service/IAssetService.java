package com.ing.brokefirm.service;

import com.ing.brokefirm.model.Asset;
import com.ing.brokefirm.model.Deposit;
import com.ing.brokefirm.model.Withdraw;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

public interface IAssetService {
    void depositMoney(Deposit deposit);

    void withdrawMoney(Withdraw withdraw);

    List<Asset> listAssetsByCustomerId(Long customerId, UsernamePasswordAuthenticationToken principal);
}
