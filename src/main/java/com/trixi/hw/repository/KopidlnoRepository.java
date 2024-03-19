package com.trixi.hw.repository;

import com.trixi.hw.repository.model.VillageDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KopidlnoRepository extends JpaRepository<VillageDB, Long> {

    VillageDB findByCode(String code);
}
