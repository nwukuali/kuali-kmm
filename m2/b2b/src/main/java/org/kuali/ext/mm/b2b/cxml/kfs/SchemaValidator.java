package org.kuali.ext.mm.b2b.cxml.kfs;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileInputStream;
import java.net.URL;


public class SchemaValidator {
    private static final String SERVER_URL = "https://fin.qual.ebsp.msu.edu/kfs-qa/";

    public static void main(String[] args) {
        try {
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new URL(SERVER_URL + "static/xsd/purap/electronicInvoice.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new FileInputStream("C:\\Temp\\798940.xml")));
            System.out.println("XML is valid");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
