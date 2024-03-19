package com.trixi.hw.service;

import com.trixi.hw.error.CommonError;
import com.trixi.hw.integration.SmartFormIntegration;
import com.trixi.hw.integration.model.Village;
import com.trixi.hw.mapper.Mapper;
import com.trixi.hw.model.VillageResponse;
import com.trixi.hw.repository.KopidlnoRepository;
import com.trixi.hw.repository.model.VillageDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static com.trixi.hw.error.ValidationCode.ID_NOT_FOUND;

@Service
public class KopidlnoService {

    private final SmartFormIntegration smartFormIntegration;

    private final Mapper mapper;

    private final KopidlnoRepository kopidlnoRepository;

    @Autowired
    public KopidlnoService(SmartFormIntegration smartFormIntegration,
                           Mapper mapper,
                           KopidlnoRepository kopidlnoRepository) {
        this.smartFormIntegration = smartFormIntegration;
        this.mapper = mapper;
        this.kopidlnoRepository = kopidlnoRepository;
    }

    public VillageResponse download() throws IOException, XMLStreamException {
        Village village = smartFormIntegration.getDataFromSmartForm();

        VillageResponse villageResponse = mapper.mapToResponse(village);

        //map and save just empty DB
        if (kopidlnoRepository.findAll().isEmpty()) {
            VillageDB villageDB = mapper.mapToDB(villageResponse);
            kopidlnoRepository.save(villageDB);
        }

        return villageResponse;
    }

    public VillageResponse getKopidlnoInfo(String code) {
        VillageDB villageDB = kopidlnoRepository.findByCode(code);

        if (villageDB == null) {
            throw new CommonError(ID_NOT_FOUND, null, null, HttpStatus.NOT_FOUND);
        }

        return mapper.mapToResponse(villageDB);
    }
}
