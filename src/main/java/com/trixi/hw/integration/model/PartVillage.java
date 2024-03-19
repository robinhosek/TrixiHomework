package com.trixi.hw.integration.model;

import lombok.Data;

@Data
public class PartVillage {

    String code;

    String name;

    String villageId;

    boolean codeFill;
    boolean nameFill;
    boolean villageIdFill;

    boolean lockCode;
    boolean lockVillageId;

    public boolean isPossibleFillVillageId() {
        return !this.isLockVillageId() && this.code != null;
    }
}
