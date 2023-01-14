package de.hscoburg.modulhandbuchbackend.fop;

import lombok.Data;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.aspectj.apache.bcel.util.ClassPath;
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
    FopFactory fopFactory = FopFactory.newInstance(new ClassPathResource("fop/fop.xconf").getFile());

    public PDFService() throws IOException, SAXException, URISyntaxException {
    }


    public byte[] processPDF(File xslFile, Document xml) throws PDFGenerationException {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            Source xslSource = new StreamSource(xslFile);
            Source xmlSource = new DOMSource(xml);

            //Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xslSource);

            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(xmlSource, res);

            return out.toByteArray();
        } catch (IOException | FOPException | TransformerException e) {
            throw new PDFGenerationException(e);
        }


    }
}
