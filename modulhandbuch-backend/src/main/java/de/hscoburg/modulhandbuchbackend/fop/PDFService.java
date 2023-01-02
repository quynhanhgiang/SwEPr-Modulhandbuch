package de.hscoburg.modulhandbuchbackend.fop;

import lombok.Data;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Service
@Data
public class PDFService {
    // TODO Pfade überarbeiten
    FopFactory fopFactory = FopFactory.newInstance(new File("C:\\Users\\ChristophEuskirchen\\IdeaProjects\\SwEPr-Modulhandbuch\\modulhandbuch-backend\\src\\main\\resources\\fop\\fop.xconf"));
    OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("C:\\tmp\\test.pdf")));

    public PDFService() throws IOException, SAXException {
    }

    public void processPDF(File xslFile, Document xml) throws FOPException, TransformerException, IOException {
        Source xslSource = new StreamSource(xslFile);
        //Source xmlSource = new StreamSource(xmlFile);
        Source xmlSource = new DOMSource(xml);

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xslSource);

            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(xmlSource, res);
        }finally {
            out.close();
        }
    }
}
