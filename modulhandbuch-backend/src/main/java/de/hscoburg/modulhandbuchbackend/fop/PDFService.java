package de.hscoburg.modulhandbuchbackend.fop;

import lombok.Data;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URISyntaxException;

@Service
@Data
public class PDFService {
    // TODO Pfade überarbeiten
    //FopFactory fopFactory = FopFactory.newInstance(new File("C:\\Users\\ChristophEuskirchen\\IdeaProjects\\SwEPr-Modulhandbuch\\modulhandbuch-backend\\src\\main\\resources\\fop\\fop.xconf"));
    FopFactory fopFactory = FopFactory.newInstance(new File(new ClassPathResource("/fop.xconf").getURI()));
    //FopFactory fopFactory = FopFactory.newInstance(new File(this.getClass().getResource("/fop/fop.xconf").toURI()));
    //FopFactory fopFactory = FopFactory.newInstance(new File("src/main"));

    public PDFService() throws IOException, SAXException, URISyntaxException {
    }


    public OutputStream processPDF(File xslFile, Document xml) throws FOPException, TransformerException {
        OutputStream out = new ByteArrayOutputStream();

        Source xslSource = new StreamSource(xslFile);
        Source xmlSource = new DOMSource(xml);

        //Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(xslSource);

        Result res = new SAXResult(fop.getDefaultHandler());

        transformer.transform(xmlSource, res);

        return out;
    }
}
