package com.trixi.hw.integration.model;

import lombok.Data;

import java.util.List;

@Data
public class Village {

    private String code;

    private String name;

    List<PartVillage> partVillages;

    boolean codeFill;
    boolean nameFill;
    boolean lockCode;
}
