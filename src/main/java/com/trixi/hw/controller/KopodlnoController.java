package com.trixi.hw.controller;

import com.trixi.hw.model.VillageResponse;
import com.trixi.hw.service.KopidlnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = KopodlnoController.KOPIDLNO)
public class KopodlnoController {

    public static final String KOPIDLNO = "/api/v1";
    private final KopidlnoService kopidlnoService;

    @Autowired
    public KopodlnoController(KopidlnoService kopidlnoService) {
        this.kopidlnoService = kopidlnoService;
    }

    @PostMapping(value = "/kopidlno/download", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public VillageResponse download() throws IOException, XMLStreamException {
        return kopidlnoService.download();
    }

    @GetMapping(value = "/kopidlno/{code}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public VillageResponse getKopidlnoInfo(@PathVariable String code) {
        return kopidlnoService.getKopidlnoInfo(code);
    }
}
