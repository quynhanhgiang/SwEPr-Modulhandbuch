package de.hscoburg.modulhandbuchbackend.fop;

import de.hscoburg.modulhandbuchbackend.model.entities.*;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import lombok.Data;
import org.apache.fop.apps.FOPException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@Data
public class ModuleManualService {

    private final VariationRepository variationRepository;
    private final PDFService pdfService;


    public Document generateModuleManualXML(ModuleManualEntity moduleManual) throws PDFGenerationException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //root Element
            Element rootElement = document.createElement("moduleManual");
            document.appendChild(rootElement);

            // TODO Titelseite

            // TODO Inhaltsverzeichnis

            // Module
            Element modulesNode = document.createElement("modules");
            rootElement.appendChild(modulesNode);

            SectionEntity currentSection = moduleManual.getFirstSection();
            int sectionCounter = 0;
            while (currentSection != null) {
                Element section = document.createElement("section");
                modulesNode.appendChild(section);

                // section xml-Attribute
                Attr sectionAttr = document.createAttribute("section");
                sectionAttr.setValue((++sectionCounter) + ". " + currentSection.getValue());
                modulesNode.setAttributeNode(sectionAttr);

                TypeEntity currentType = moduleManual.getFirstType();
                int typeCounter = 0;
                while (currentType != null) {
                    Element moduleTypeNode = document.createElement("moduleType");
                    section.appendChild(moduleTypeNode);

                    // type xml-Attribute
                    Attr typeAttr = document.createAttribute("type");
                    typeAttr.setValue((sectionCounter) + "." + (++typeCounter) + ". " + currentType.getValue());
                    moduleTypeNode.setAttributeNode(typeAttr);

                    List<VariationEntity> variations = variationRepository.findBySegmentAndModuleType(currentSection, currentType);
                    variations.stream().sorted((a, b) -> a.getModule().getModuleName().compareTo(b.getModule().getModuleName())).forEach(variation -> {
                        ModuleEntity module = variation.getModule();
                        Element moduleNode = document.createElement("module");
                        moduleTypeNode.appendChild(moduleNode);

                        Attr moduleNameAttr = document.createAttribute("module");
                        moduleNameAttr.setValue(module.getModuleName());
                        moduleNode.setAttributeNode(moduleNameAttr);

                        createChildAttribute(document, moduleNode, "Modulbezeichnung", module.getModuleName());
                        createChildAttribute(document, moduleNode, "Kuerzel", module.getAbbreviation());
                        createChildAttribute(document, moduleNode, "Lehrform / SWS", variation.getSws().toString());
                        createChildAttribute(document, moduleNode, "Leistungspunkte", variation.getEcts().toString());
                        createChildAttribute(document, moduleNode, "Arbeitsaufwand", variation.getWorkLoad());
                        createChildAttribute(document, moduleNode, "Fachsemester", variation.getSemester().toString());
                        createChildAttribute(document, moduleNode, "Angebotsturnus", module.getCycle().toString());
                        createChildAttribute(document, moduleNode, "Dauer des Moduls", module.getDuration().toString());
                        createChildAttribute(document, moduleNode, "Modulverantwortlicher", module.getModuleOwner().toString());
                        createChildAttribute(document, moduleNode, "Dozent", module.getProfs().toString());
                        createChildAttribute(document, moduleNode, "Sprache", module.getLanguage().toString());
                        if (variation.getAdmissionRequirement() != null)
                            createChildAttribute(document, moduleNode, "Zulassungsvoraussetzungen", variation.getAdmissionRequirement().toString());
                        createChildAttribute(document, moduleNode, "Inhaltliche Voraussetzungen", module.getKnowledgeRequirements());
                        createChildAttribute(document, moduleNode, "Qualifikationsziele", module.getSkills());
                        createChildAttribute(document, moduleNode, "Lehrinhalte", module.getContent());
                        createChildAttribute(document, moduleNode, "Endnotenbildende Studien-/ Pruefungsleistungen", module.getExamType());
                        createChildAttribute(document, moduleNode, "Sonstige Leistungsnachweise", module.getCertificates());
                        createChildAttribute(document, moduleNode, "Medienformen", module.getMediaType());
                        createChildAttribute(document, moduleNode, "Literatur", module.getLiterature());
                    });

                    // currentType inkrementieren
                    currentType = currentType.getNext();
                }

                // currentSection inkrementieren
                currentSection = currentSection.getNext();
            }

            return document;
        }catch(ParserConfigurationException e){
            throw new PDFGenerationException(e);
        }
    }

    public byte[] generateModuleManualPDF(ModuleManualEntity moduleManual) throws PDFGenerationException {

        try{
            Document xml = generateModuleManualXML(moduleManual);
            return pdfService.processPDF(new ClassPathResource("fop/module_style.xsl").getFile(), xml);
        }catch(IOException e){
            throw new PDFGenerationException(e);
        }

    }

    private void createChildAttribute(Document document, Element parentNode, String title, String value){
        Element attributeNode = document.createElement("attribute");
        parentNode.appendChild(attributeNode);

        // attribute - title
        Attr titleAttr = document.createAttribute("title");
        titleAttr.setValue(title);
        attributeNode.setAttributeNode(titleAttr);

        // attribute - value
        Attr valueAttr = document.createAttribute("value");
        valueAttr.setValue(value);
        attributeNode.setAttributeNode(valueAttr);
    }
}
