package org.kuali.ext.mm.b2b.cxml.demo;

import java.io.File;

import javax.xml.bind.JAXBElement;

import org.kuali.ext.mm.b2b.cxml.transform.CxmlToShoppingCart;
import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.b2b.cxml.types.Message;
import org.kuali.ext.mm.b2b.cxml.types.PunchOutOrderMessage;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;

/**
 * @author harsha07
 */
public class B2bCxmlParser {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            CXML cxml = CxmlUtil.unmarshal(new File(
                "C:\\java\\projects\\mm-dev\\doc\\Sample_Request_cXML.xml"));
            System.out.println(cxml);
            Message message = cxml.getMessage();
            if (message != null) {
                JAXBElement<?> cxmlMessages = message.getCxmlMessages();
                if (cxmlMessages != null) {
                    Object orderMessage = cxmlMessages.getValue();
                    if (PunchOutOrderMessage.class.isAssignableFrom(orderMessage.getClass())) {
                        new CxmlToShoppingCart().transform((PunchOutOrderMessage) orderMessage);
                    }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
