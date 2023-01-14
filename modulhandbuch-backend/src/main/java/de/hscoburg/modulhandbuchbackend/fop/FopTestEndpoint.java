package de.hscoburg.modulhandbuchbackend.fop;

import de.hscoburg.modulhandbuchbackend.dto.CollegeEmployeeDTO;
import de.hscoburg.modulhandbuchbackend.model.entities.CollegeEmployeeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/generatePDF")
public class FopTestEndpoint {

    ModuleManualService moduleManualService;
    ModuleManualRepository moduleManualRepository;

    @GetMapping("")
    void generatePDF() {
        ModuleManualEntity moduleManual = moduleManualRepository.findById(1).orElse(null);
        try {
            moduleManualService.generateModuleManualPDF(moduleManual);
        } catch (PDFGenerationException e) {
            e.printStackTrace();
        }
    }
}
