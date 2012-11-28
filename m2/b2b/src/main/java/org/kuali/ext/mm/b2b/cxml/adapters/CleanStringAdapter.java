/**
 * 
 */
package org.kuali.ext.mm.b2b.cxml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author harsha07
 */
public class CleanStringAdapter extends XmlAdapter<String, String> {

    /**
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public String marshal(String s) throws Exception {
        return unwrap(s);
    }

    /**
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @Override
    public String unmarshal(String s) throws Exception {
        return unwrap(s);
    }

    private static String unwrap(String line) {
        if (line == null) {
            return null;
        }
        return line.replace("\n", "").replace("\t", "").replace("\r", "").trim();
    }

}
