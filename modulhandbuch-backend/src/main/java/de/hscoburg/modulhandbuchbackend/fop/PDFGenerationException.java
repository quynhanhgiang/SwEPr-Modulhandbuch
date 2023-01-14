package de.hscoburg.modulhandbuchbackend.fop;

import org.apache.fop.apps.FOPException;

public class PDFGenerationException extends Exception{

    public PDFGenerationException(){
        super();
    }

    public PDFGenerationException(Exception e){
        super(e);
    }
}
