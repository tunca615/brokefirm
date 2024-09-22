package com.ing.brokefirm.mapper;

import com.ing.brokefirm.controller.OrderResource.AssetResource;
import com.ing.brokefirm.controller.command.DepositCommand;
import com.ing.brokefirm.controller.command.WithdrawCommand;
import com.ing.brokefirm.model.Asset;
import com.ing.brokefirm.model.Deposit;
import com.ing.brokefirm.model.Withdraw;
import com.ing.brokefirm.repository.entity.AssetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper( AssetMapper.class );

    AssetResource assetToAssetResource(Asset asset);

    AssetEntity assetToAssetEntity(Asset asset);

    Asset assetEntityToAsset(AssetEntity assetEntity);

    Deposit depositCommandToDeposit(DepositCommand depositCommand);
    Withdraw withdrawCommandToDeposit(WithdrawCommand withdrawCommand);
}
