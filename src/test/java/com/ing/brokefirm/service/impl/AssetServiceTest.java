package com.ing.brokefirm.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import com.ing.brokefirm.controller.resource.AssetResource;
import com.ing.brokefirm.mapper.AssetMapper;
import com.ing.brokefirm.model.Asset;
import com.ing.brokefirm.repository.entity.AssetEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

class AssetMapperTest {

    private final AssetMapper assetMapper = Mappers.getMapper(AssetMapper.class);

    @Test
    void assetToAssetResource_ShouldMapFieldsCorrectly() {
        // Given
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setCustomerId(2L);
        asset.setAssetName("AAPL");
        asset.setSize(new BigDecimal("100"));
        asset.setUsableSize(new BigDecimal("80"));

        // When
        AssetResource resource = assetMapper.assetToAssetResource(asset);

        // Then
        assertNotNull(resource);
        assertEquals(asset.getId(), resource.getId());
        assertEquals(asset.getCustomerId(), resource.getCustomerId());
        assertEquals(asset.getAssetName(), resource.getAssetName());
        assertEquals(asset.getSize(), resource.getSize());
        assertEquals(asset.getUsableSize(), resource.getUsableSize());
    }

    @Test
    void assetToAssetEntity_ShouldMapFieldsCorrectly() {
        // Given
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setCustomerId(2L);
        asset.setAssetName("AAPL");
        asset.setSize(new BigDecimal("100"));
        asset.setUsableSize(new BigDecimal("80"));

        // When
        AssetEntity entity = assetMapper.assetToAssetEntity(asset);

        // Then
        assertNotNull(entity);
        assertEquals(asset.getId(), entity.getId());
        assertEquals(asset.getCustomerId(), entity.getCustomerId());
        assertEquals(asset.getAssetName(), entity.getAssetName());
        assertEquals(asset.getSize(), entity.getSize());
        assertEquals(asset.getUsableSize(), entity.getUsableSize());
    }

    @Test
    void assetEntityToAsset_ShouldMapFieldsCorrectly() {
        // Given
        AssetEntity entity = new AssetEntity();
        entity.setId(1L);
        entity.setCustomerId(2L);
        entity.setAssetName("AAPL");
        entity.setSize(new BigDecimal("100"));
        entity.setUsableSize(new BigDecimal("80"));

        // When
        Asset asset = assetMapper.assetEntityToAsset(entity);

        // Then
        assertNotNull(asset);
        assertEquals(entity.getId(), asset.getId());
        assertEquals(entity.getCustomerId(), asset.getCustomerId());
        assertEquals(entity.getAssetName(), asset.getAssetName());
        assertEquals(entity.getSize(), asset.getSize());
        assertEquals(entity.getUsableSize(), asset.getUsableSize());
    }
}
