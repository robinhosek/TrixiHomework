package com.trixi.hw.model;

import lombok.Data;

import java.util.List;

@Data
public class VillageResponse {

    private String code;

    private String name;

    List<PartVillageResponse> partVillages;
}
