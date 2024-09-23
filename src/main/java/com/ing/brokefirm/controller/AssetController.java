package com.ing.brokefirm.controller;

import com.ing.brokefirm.controller.resource.AssetResource;
import com.ing.brokefirm.controller.command.DepositCommand;
import com.ing.brokefirm.controller.command.WithdrawCommand;
import com.ing.brokefirm.mapper.AssetMapper;
import com.ing.brokefirm.model.Asset;
import com.ing.brokefirm.service.IAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {
    private final IAssetService assetService;
    private final AssetMapper mapper;

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('ADMIN')")
    public void depositMoney(@RequestBody DepositCommand depositCommand) {
        assetService.depositMoney(mapper.depositCommandToDeposit(depositCommand));
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('ADMIN')")
    public void withdrawMoney(@RequestBody WithdrawCommand withdrawCommand) {
        assetService.withdrawMoney(mapper.withdrawCommandToDeposit(withdrawCommand));
    }

    @GetMapping
    public List<AssetResource> listAssets(@RequestParam(required = false) Long customerId, UsernamePasswordAuthenticationToken principal) {
        List<Asset> assets = assetService.listAssetsByCustomerId(customerId, principal);
        return assets.stream().map(mapper::assetToAssetResource).toList();
    }
}

