package com.trixi.hw.mapper;

import com.trixi.hw.model.PartVillageResponse;
import com.trixi.hw.model.VillageResponse;
import com.trixi.hw.repository.model.PartVillageDB;
import com.trixi.hw.repository.model.VillageDB;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    public VillageDB mapToDB(VillageResponse village) {
        if (village == null) {
            return null;
        }

        VillageDB villageDB = new VillageDB();
        villageDB.setCode(village.getCode());
        villageDB.setName(village.getName());

        villageDB.setPartVillage(mapToPartVillageDB(village.getPartVillages(), villageDB));

        return villageDB;
    }

    public VillageResponse mapToResponse(com.trixi.hw.integration.model.Village village) {
        if (village == null) {
            return null;
        }

        VillageResponse villageResponse = new VillageResponse();
        villageResponse.setCode(village.getCode());
        villageResponse.setName(village.getName());
        villageResponse.setPartVillages(mapToPartVillageDownloadResponse(village.getPartVillages()));

        return villageResponse;
    }

    public VillageResponse mapToResponse(VillageDB villageDB) {
        if (villageDB == null) {
            return null;
        }

        VillageResponse villageResponse = new VillageResponse();
        villageResponse.setCode(villageDB.getCode());
        villageResponse.setName(villageDB.getName());
        villageResponse.setPartVillages(mapToPartVillageResponse(villageDB.getPartVillage()));

        return villageResponse;
    }

    private List<PartVillageResponse> mapToPartVillageDownloadResponse(List<com.trixi.hw.integration.model.PartVillage> xmlPartVillages) {
        if (xmlPartVillages.isEmpty()) {
            return null;
        }

        List<PartVillageResponse> partVillagesResponse = new ArrayList<>();

        for (com.trixi.hw.integration.model.PartVillage xmlPartVillage: xmlPartVillages) {
            PartVillageResponse partVillage = new PartVillageResponse();
            partVillage.setVillageId(xmlPartVillage.getVillageId());
            partVillage.setCode(xmlPartVillage.getCode());
            partVillage.setName(xmlPartVillage.getName());
            partVillagesResponse.add(partVillage);
        }

        return partVillagesResponse;
    }

    private List<PartVillageResponse> mapToPartVillageResponse(List<PartVillageDB> partVillageDB) {
        if (partVillageDB.isEmpty()) {
            return null;
        }

        List<PartVillageResponse> partVillagesResponse = new ArrayList<>();

        for (PartVillageDB partVillagesDB: partVillageDB) {
            PartVillageResponse partVillage = new PartVillageResponse();
            partVillage.setVillageId(partVillagesDB.getVillageCode());
            partVillage.setCode(partVillagesDB.getCode());
            partVillage.setName(partVillagesDB.getName());
            partVillagesResponse.add(partVillage);
        }

        return partVillagesResponse;
    }

    private List<PartVillageDB> mapToPartVillageDB(List<PartVillageResponse> partVillages, VillageDB villageDB) {
        if (partVillages.isEmpty()) {
            return null;
        }

        List<PartVillageDB> partVillageDBS = new ArrayList<>();

        for (PartVillageResponse partVillage: partVillages) {
            PartVillageDB partVillageDB = new PartVillageDB();
            partVillageDB.setCode(partVillage.getCode());
            partVillageDB.setName(partVillage.getName());
            partVillageDB.setVillageCode(villageDB.getCode());
            partVillageDB.setVillage_id(villageDB);
            partVillageDBS.add(partVillageDB);
        }

        return partVillageDBS;
    }
}
