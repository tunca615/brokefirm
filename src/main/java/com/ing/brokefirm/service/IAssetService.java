package com.ing.brokefirm.service;

import com.ing.brokefirm.model.Asset;
import com.ing.brokefirm.model.Deposit;
import com.ing.brokefirm.model.Withdraw;

import java.security.Principal;
import java.util.List;

public interface IAssetService {
    void depositMoney(Deposit deposit, Principal principal);

    void withdrawMoney(Withdraw withdraw, Principal principal);

    List<Asset> listAssetsByCustomerId(Long customerId, Principal principal);
}
