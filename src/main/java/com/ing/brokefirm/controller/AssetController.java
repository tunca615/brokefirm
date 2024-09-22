package com.ing.brokefirm.controller;

import com.ing.brokefirm.controller.OrderResource.AssetResource;
import com.ing.brokefirm.controller.command.DepositCommand;
import com.ing.brokefirm.controller.command.WithdrawCommand;
import com.ing.brokefirm.mapper.AssetMapper;
import com.ing.brokefirm.model.Asset;
import com.ing.brokefirm.service.IAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {
    private final IAssetService assetService;
    private final AssetMapper mapper;

    @PostMapping("/deposit")
    public void depositMoney(@RequestBody DepositCommand depositCommand, Principal principal) {
        assetService.depositMoney(mapper.depositCommandToDeposit(depositCommand), principal);
    }

    @PostMapping("/withdraw")
    public void withdrawMoney(@RequestBody WithdrawCommand withdrawCommand, Principal principal) {
        assetService.withdrawMoney(mapper.withdrawCommandToDeposit(withdrawCommand), principal);
    }

    @GetMapping
    public List<AssetResource> listAssets(@RequestParam(required = false) Long customerId, Principal principal) {
        List<Asset> assets = assetService.listAssetsByCustomerId(customerId, principal);
        return assets.stream().map(mapper::assetToAssetResource).toList();
    }
}

