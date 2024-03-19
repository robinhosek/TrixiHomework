package com.trixi.hw.integration;

import com.trixi.hw.integration.model.PartVillage;
import com.trixi.hw.integration.model.Village;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.ZipFile;

import javax.xml.stream.*;
import javax.xml.stream.events.*;

@Configuration
@Slf4j
public class SmartFormIntegration {

    private static final String PATH_FOLDER = "src/main/resources/kopidlno";
    public static final String ZIP_PATH = "src/main/resources/kopidlno/kopidlno.zip";
    public static final String ZIP_FILE_PATH = "src/main/resources/kopidlno";
    public static final String XML_FILE_PATH = "src/main/resources/kopidlno/20210331_OB_573060_UZSZ.xml";
    private static final String KOD = "Kod";
    private static final String NAZEV = "Nazev";
    private static final String OBEC = "Obec";
    private static final String CAST_OBCE = "CastObce";
    private static final String CASTI_OBCE = "CastiObci";

    @Value("${smartform.url}")
    private String smartFormUrl;

    public Village getDataFromSmartForm() throws IOException, XMLStreamException {

        downloadAndUnzipFile();

        return parseXmlFile();
    }

    private void downloadAndUnzipFile() throws IOException {
        File imagesPath = new File(PATH_FOLDER);
        boolean exists = imagesPath.exists();
        if (!exists) {
            // create directory
            imagesPath.mkdir();
            // download
            String zipPath = ZIP_PATH;
            FileUtils.copyURLToFile(new URL(smartFormUrl), new File(zipPath));
            // unzip
            try {
                new ZipFile(zipPath).extractAll(ZIP_FILE_PATH);
            } catch (ZipException e) {
                e.printStackTrace();
            }
        }
    }

    private Village parseXmlFile() throws XMLStreamException, IOException {
        File file = new File(XML_FILE_PATH);

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));

        Village village = new Village();
        Village xmlVillage = getVillage(eventReader, village);

        List<PartVillage> partVillages = new ArrayList<>();
        xmlVillage.setPartVillages(getPartVillages(partVillages, eventReader));

        return xmlVillage;
    }

    private Village getVillage(XMLEventReader eventReader, Village village) throws XMLStreamException {
        boolean killWhile = false;
        while (eventReader.hasNext() && !killWhile) {
            XMLEvent xmlEvent = eventReader.nextEvent();

            switch (xmlEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    StartElement startElement = xmlEvent.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase(OBEC)) {
                        village = new Village();
                    }
                    if (village != null) {
                        if (qName.equalsIgnoreCase(KOD) && !village.isLockCode()) {
                            village.setCodeFill(true);
                            village.setLockCode(true);
                        }
                        if (qName.equalsIgnoreCase(NAZEV)) {
                            village.setNameFill(true);
                        }
                    }
                }

                case XMLStreamConstants.CHARACTERS -> {
                    Characters characters = xmlEvent.asCharacters();

                    if (village != null) {
                        if (village.isCodeFill()) {
                            village.setCode(characters.getData());
                            village.setCodeFill(false);
                        }

                        if (village.isNameFill()) {
                            village.setName(characters.getData());
                            village.setNameFill(false);
                        }
                    }
                }

                case XMLStreamConstants.END_ELEMENT -> {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equalsIgnoreCase(OBEC)) {
                        killWhile = true;
                    }
                }
            }
        }

        return village;
    }

    private List<com.trixi.hw.integration.model.PartVillage> getPartVillages(List<com.trixi.hw.integration.model.PartVillage> partVillages,
                                                                             XMLEventReader eventReader) throws XMLStreamException {

        com.trixi.hw.integration.model.PartVillage partVillage = null;

        boolean killWhile = false;
        while (eventReader.hasNext() && !killWhile) {
            XMLEvent xmlEvent = eventReader.nextEvent();

            switch (xmlEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    StartElement startElement = xmlEvent.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase(CAST_OBCE)) {
                        partVillage = new com.trixi.hw.integration.model.PartVillage();
                    }
                    if (partVillage != null) {
                        if (qName.equalsIgnoreCase(KOD) && !partVillage.isLockCode()) {
                            partVillage.setCodeFill(true);
                            partVillage.setLockCode(true);
                        }
                        if (qName.equalsIgnoreCase(NAZEV)) {
                            partVillage.setNameFill(true);
                        }
                        if (qName.equalsIgnoreCase(KOD) && partVillage.isPossibleFillVillageId()) {
                            partVillage.setVillageIdFill(true);
                            partVillage.setLockVillageId(true);
                        }
                    }
                }

                case XMLStreamConstants.CHARACTERS -> {
                    Characters characters = xmlEvent.asCharacters();

                    if (partVillage != null) {
                        if (partVillage.isCodeFill()) {
                            partVillage.setCode(characters.getData());
                            partVillage.setCodeFill(false);
                        }

                        if (partVillage.isNameFill()) {
                            partVillage.setName(characters.getData());
                            partVillage.setNameFill(false);
                        }
                        if (partVillage.isVillageIdFill()) {
                            partVillage.setVillageId(characters.getData());
                            partVillage.setVillageIdFill(false);
                        }
                    }
                }


                case XMLStreamConstants.END_ELEMENT -> {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equalsIgnoreCase(CAST_OBCE)) {
                        partVillages.add(partVillage);
                        partVillage.setLockVillageId(false);
                        partVillage.setLockCode(false);
                    }
                    if (endElement.getName().getLocalPart().equalsIgnoreCase(CASTI_OBCE)) {
                        killWhile = true;
                    }
                }
            }
        }

        return partVillages;
    }
}
