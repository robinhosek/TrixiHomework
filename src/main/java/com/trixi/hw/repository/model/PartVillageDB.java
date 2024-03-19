package com.trixi.hw.repository.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "partvillage")
public class PartVillageDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "code")
    String code;

    @Column(name = "name")
    String name;

    @Column(name = "villageCode", nullable = false)
    private String villageCode;

    @ManyToOne
    @JoinColumn(name = "village_id")
    private VillageDB village_id;

    /*@Column(name = "village_id")
    private Long villageId;*/

}
