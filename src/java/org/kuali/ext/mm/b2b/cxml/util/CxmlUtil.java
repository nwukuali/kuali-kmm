/**
 *
 */
package org.kuali.ext.mm.b2b.cxml.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.ext.mm.b2b.cxml.types.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMPropertyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import static org.kuali.ext.mm.common.sys.MMConstants.LF;

import java.lang.Object;


/**
 * @author harsha07
 */
public final class CxmlUtil {
    private static final int ERROR_STACK_MAX_LEN = 1000;
    private static final String JAXB_PKG_CXML_TYPES = "org.kuali.ext.mm.b2b.cxml.types";
    private static final Logger log = Logger.getLogger(CxmlUtil.class);
    private static JAXBContext context = null;
    private static Schema schema;
    private static Unmarshaller unmarshaller;
    private static Marshaller marshaller;

    private CxmlUtil() {
        // block object creation using private constructor
    }

    private static final JAXBContext getContext() throws JAXBException {
        if (context == null) {
            context = JAXBContext.newInstance(JAXB_PKG_CXML_TYPES);
        }
        return context;

    }

    public static final Schema getSchema() throws MalformedURLException, SAXException {
        //
        if (schema == null) {
            schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
                    new URL(SpringContext.getBean(ConfigurationService.class)
                            .getPropertyValueAsString(MMPropertyConstants.APPLICATION_URL)
                            + "/static/xsd/cxml/cXML.xsd"));
        }
        return schema;
    }

    public static final Unmarshaller createUnmarshaller() throws Exception {
        if (unmarshaller == null) {
            unmarshaller = getContext().createUnmarshaller();
            // unmarshaller.setSchema(getSchema());
        }
        return unmarshaller;
    }

    public static final Marshaller createMarshaller() throws Exception {
        if (marshaller == null) {
            marshaller = getContext().createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // marshaller.setSchema(getSchema());
        }
        return marshaller;
    }

    public static final CXML unmarshal(InputSource source) throws Exception {
        String input = readAsString(source.getCharacterStream());
        return (CXML) createUnmarshaller().unmarshal(new StringReader(input));
    }

    /**
     * @param source
     * @param input
     * @return
     * @throws IOException
     */
    public static final String readAsString(Reader reader) throws IOException {
        String input = "";
        BufferedReader rdr = new BufferedReader(reader);
        try {
            String line = null;
            while ((line = rdr.readLine()) != null) {
                input = input + line + LF;
            }
        }
        finally {
            rdr.close();
        }
        if (!isProductionEnvironment()) {
            log.info(input);
        }
        return unwrap(input);
    }

    public static final CXML unmarshal(File file) throws Exception {
        String input = readAsString(new FileReader(file));
        CXML cxml = (CXML) createUnmarshaller().unmarshal(new StringReader(input));
        return cxml;
    }

    public static final CXML unmarshal(InputStream is) throws Exception {
        String input = readAsString(new InputStreamReader(is));
        CXML cxml = (CXML) createUnmarshaller().unmarshal(new StringReader(input));
        return cxml;
    }

    public static String unwrap(String line) {
        if (line != null) {
            return line.replace(MMConstants.LF, "").replace(MMConstants.TAB, "").replace(
                    MMConstants.CR, "").trim();
        }
        return line;
    }

    /**
     * Cleanses a string of non-displayable characters, while leaving spaces, new lines, and tabs.
     * 
     * @param line - a string to clean
     * @return a copy of line with all invisible (non-displayable) characters removed, excluding whitespace.
     */
    public static final String cleanString(String line) {
        StringBuffer regexString = new StringBuffer("[^");
        regexString.append("\\p{Graph}");
        regexString.append("\\p{Space}");
        regexString.append("]*");

        return line.replaceAll(regexString.toString(), "");
    }

    public static final Money createMoneyElement(String amount, String currency) {
        Money money = new Money();
        money.setContent(amount);
        money.setCurrency(currency);
        return money;
    }

    public static final String cxmlToString(CXML cxml) {
        String retVal = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            createMarshaller().marshal(cxml, baos);
            retVal = baos.toString("UTF-8");
        }
        catch (Exception e) {
            log.error("Error", e);
            throw new RuntimeException("Could not write CXML object to ByteArrayOutputStream.");
        }
        return retVal;
    }

    public static void logRequestInfo(HttpServletRequest request) throws Exception {
        BufferedWriter writer = null;
        try {
            StringWriter stringWriter = new StringWriter();
            writer = new BufferedWriter(stringWriter);
            writer.append("AuthType: " + request.getAuthType());
            writer.newLine();
            writer.append("Content Length: " + request.getContentLength());
            writer.newLine();
            writer.append("Content Type: " + request.getContentType());
            writer.newLine();
            writer.append("Context Path: " + request.getContextPath());
            writer.newLine();
            writer.append("Path Info: " + request.getPathInfo());
            writer.newLine();
            writer.append("QueryString: " + request.getQueryString());
            writer.newLine();
            writer.append("RequestURI: " + request.getRequestURI());
            writer.newLine();
            writer.append("Parameters:");
            writer.newLine();
            for (Object parameter : request.getParameterMap().keySet()) {
                writer.append(String.valueOf(parameter) + ": "
                        + request.getParameter(String.valueOf(parameter)));
                writer.newLine();
            }
            Enumeration<String> attributeNames = request.getAttributeNames();
            writer.append("Attributes:");
            writer.newLine();
            while (attributeNames.hasMoreElements()) {
                String attr = attributeNames.nextElement();
                writer.append(attr + ": " + request.getAttribute(attr));
                writer.newLine();
            }
            writer.append("Headers:");
            writer.newLine();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                Enumeration<String> headerValues = request.getHeaders(headerNames.nextElement());
                while (headerValues.hasMoreElements()) {
                    writer.append(headerValues.nextElement());
                    writer.newLine();
                }
            }
            // LOG the contents of the request
            log.info(stringWriter.getBuffer().toString());
        }
        catch (IOException e) {
            throw new RuntimeException("Error while logging InputStream", e);
        }
        finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * Returns the shared secret from the senders header credentials
     * 
     * @param cxml - A populated cXML object
     * @return the shared secret in the header credentials
     */
    public static final String getSharedSecret(CXML cxml) {
        SharedSecret sharedSecret = cxml.getHeader().getSender().getCredential().get(0)
                .getSharedSecret();
        return sharedSecret != null ? sharedSecret.getFirstContent() : null;
    }

    public static CXML sendRequest(CXML cxml, String toUrl) {
        CXML cxmlResponse = null;
        HttpURLConnection connection = null;
        try {
            java.net.URL vendorUrl = new java.net.URL(toUrl);
            connection = (HttpURLConnection) vendorUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
            marshal(cxml, connection.getOutputStream());
            String content = readAsString(new InputStreamReader(connection.getInputStream()));
            cxmlResponse = unmarshal(new InputSource(new StringReader(content)));
        }
        catch (Throwable e) {
            log.error("Error!!!", e);
            ObjectFactory factory = new ObjectFactory();
            cxmlResponse = factory.createCXML();
            createCxmlHeader(cxmlResponse, "RSP");
            cxmlResponse.setResponse(createResponse("500", "Internal Server Error",
                    buildErrorMessage("Failed to send the request, please contact technical team.",
                            e), factory));
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return cxmlResponse;
    }

    public static final void sendResponse(OutputStream outputStream, String code, String text,
            String content) {
        ObjectFactory factory = new ObjectFactory();
        CXML cxml = factory.createCXML();
        createCxmlHeader(cxml, "RSP");
        cxml.setResponse(createResponse(code, text, content, factory));

        try {
            marshal(cxml, outputStream);
        }
        catch (Exception e) {
            log.error("Error!!", e);
            throw new RuntimeException("Punchout response failed to send.", e);
        }
    }

    /**
     * @param cxml
     * @param outputStream
     */
    public static final void marshal(CXML cxml, OutputStream outputStream) throws Exception {
        createMarshaller().marshal(cxml, outputStream);
        if (!isProductionEnvironment()) {
            StringWriter stringWriter = new StringWriter();
            createMarshaller().marshal(cxml, stringWriter);
            log.info(stringWriter.getBuffer().toString());
        }
    }

    public static final void marshal(CXML cxml, Writer writer) throws Exception {
        createMarshaller().marshal(cxml, writer);
        if (!isProductionEnvironment()) {
            StringWriter stringWriter = new StringWriter();
            createMarshaller().marshal(cxml, stringWriter);
            log.info(stringWriter.getBuffer().toString());
        }
    }

    public static final void createCxmlHeader(CXML cxml, String idPart) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        cxml.setPayloadID(fmt.format(new Date()) + "." + idPart + "@kmm.com");
        cxml.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
        cxml.setVersion("1.2.021");
        cxml.setLang("enUS");
    }

    public static final MMDecimal extractDollar(Money money) {
        MMDecimal amount = MMDecimal.ZERO;
        if (money != null) {
            String dollar = money.getContent();
            if (StringUtils.isNotBlank(dollar)) {
                amount = new MMDecimal(dollar);
            }
        }
        return amount;
    }

	public static final boolean isProductionEnvironment() {
		String productionEnvironmentCode = org.kuali.kfs.sys.context.SpringContext.getBean(ConfigurationService.class).getPropertyValueAsString(KFSConstants.PROD_ENVIRONMENT_CODE_KEY);
		String environmentCode = org.kuali.kfs.sys.context.SpringContext.getBean(ConfigurationService.class).getPropertyValueAsString(KFSConstants.ENVIRONMENT_KEY);
		return StringUtils.equals(productionEnvironmentCode, environmentCode);
	}

    public static final boolean canAccept(String deploymentMode) {
        // This check is to block any accidental commit from a TEST system into PRODUCTION
        if (isProductionEnvironment()) {
            if (StringUtils.isBlank(deploymentMode)
                    || "production".equalsIgnoreCase(deploymentMode)) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * @return
     */
    public static final String getDeploymentMode() {
        if (isProductionEnvironment()) {
            return "production";
        }
        return "test";
    }

    public static final Response createResponse(String code, String text, String content,
            ObjectFactory factory) {
        Response response = factory.createResponse();
        Status status = factory.createStatus();
        status.setCode(code);
        status.setText(text);
        status.setContent(content);
        response.setStatus(status);
        return response;
    }

    public static final void log(CXML cxml) throws Exception {
        // Only production needs explicit logging, in other environments all cxmls are written to logs
        if (isProductionEnvironment()) {
            StringWriter stringWriter = new StringWriter();
            createMarshaller().marshal(cxml, stringWriter);
            log.info(stringWriter.getBuffer().toString());
        }
    }

    public static final String buildErrorMessage(String msg, Throwable e) {
        StringBuilder sb = new StringBuilder(ERROR_STACK_MAX_LEN);
        sb.append(msg);
        sb.append(LF);
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace != null) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(stackTraceElement.toString());
                sb.append(LF);
            }
        }
        String string = sb.toString();
        if (string.length() > ERROR_STACK_MAX_LEN) {
            return string.substring(1, ERROR_STACK_MAX_LEN);
        }
        return string;
    }
}
