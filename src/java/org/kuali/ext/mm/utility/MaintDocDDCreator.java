/*
 * Copyright 2008 The Regents of the University of California
 *
 */
package org.kuali.ext.mm.utility;

import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.kuali.rice.krad.bo.BusinessObject;

import java.beans.PropertyDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MaintDocDDCreator {

	/**
     * Use this application to generate maintenance document Data dictionary drafts for the Materiel Management project.
     * Files will be placed in org.kuali.ext.mm.document.datadictionary.  This generator
     * will produce an incomplete draft of the data dictionary and will require replacement of
     * certain fields, especially those pre-populated with "FILL ME IN".  Also there may be
     * additional property configurations required such as maxLength, control size, and attribute descriptions.
     * @param args	name of a file containing the list of BOs to be generated; in this case use boGen.Conf.xml located in the root of the project directory
     */
    public static void main( String[] args ) throws Exception {
    	String filename = args[0];


    	try{
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(filename);

			List<Node> boList = document.selectNodes("//bo");

	    	for(Node bo : boList)
	    	{
	    		String boName = bo.selectSingleNode("@name").getStringValue();
		        String className = bo.selectSingleNode("@class").getStringValue();
		        Class boClass = Class.forName( className );
		        PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors( boClass );

		        StringBuffer sb = new StringBuffer( 4000 );
		        sb.append( "<beans xmlns=\"http://www.springframework.org/schema/beans\"\r\n" +
		                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" +
		                "    xmlns:p=\"http://www.springframework.org/schema/p\"\r\n" +
		                "    xsi:schemaLocation=\"http://www.springframework.org/schema/beans\r\n" +
		                "        http://www.springframework.org/schema/beans/spring-beans-2.0.xsd\">\r\n" +
		                "\r\n" +
		                "  <bean id=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument\" parent=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-parentBean\" />\r\n" +
		                "\r\n" +
		                "  <bean id=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-parentBean\" abstract=\"true\" parent=\"MaintenanceDocumentEntry\"\r\n" +
		                "        p:businessObjectClass=\"" );
		        sb.append( boClass.getName() );
		        sb.append( "\"\r\n" );
		        sb.append( "        p:maintainableClass=\"" );
		        sb.append( "org.kuali.rice.kns.maintenance.KualiMaintainableImpl" );
		        sb.append( "\"\r\n" );
		        sb.append( "        p:documentTypeName=\"" );
		        sb.append( boClass.getSimpleName() + "MaintenanceDocument" );
		        sb.append( "\"\r\n" );
		        sb.append( "        p:documentAuthorizerClass=\"" );
		        sb.append( "org.kuali.rice.kns.document.authorization.MaintenanceDocumentAuthorizerBase" );
		        sb.append( "\" >\r\n" );
		        sb.append( "    <property name=\"lockingKeys\" >\r\n" +
		        		"      <list>\r\n" +
		        		"        <value>FILLMEIN</value>\r\n" +
		        		"      </list>\r\n" +
		        		"    </property>\r\n" +
		        		"");
		        sb.append( "    <property name=\"defaultExistenceChecks\" >\r\n" +
		        		"      <list>\r\n" +
		        		"" );
		        for ( PropertyDescriptor pd : props ) {
		            if ( isReferenceBoProperty(pd)) {
		                sb.append( "        <bean parent=\"ReferenceDefinition\"\r\n" +
		                		"              p:attributeName=\"" );
		                sb.append( pd.getName() );
		                sb.append( "\"\r\n" +
		                		"              p:attributeToHighlightOnFail=\"FILLMEIN\" />\r\n" +
		                		"" );
		            }
		        }
		        sb.append( "      </list>\r\n" +
		        		"    </property>\r\n" +
		        		"" );
		        sb.append( "    <property name=\"workflowAttributes\" >\r\n" +
		        		"        <ref bean=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-workflowAttributes\" />\r\n" +
		        		"    </property>\r\n");
		        sb.append( "    <property name=\"maintainableSections\" >\r\n" +
		        		"      <list>\r\n" +
		        		"        <ref bean=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-MainSection\" />\r\n" +
		        		"      </list>\r\n" +
		        		"    </property>\r\n" +
		        		"  </bean>\r\n\r\n");

		        sb.append(" <!-- Maintenance Section Definitions -->");
		        sb.append("\r\n");
		        sb.append( "  <bean id=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-MainSection\" parent=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-MainSection-parentBean\" />\r\n" +
		                "\r\n" +
		                "  <bean id=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-MainSection-parentBean\" abstract=\"true\" parent=\"MaintainableSectionDefinition\"\r\n" +
		                "        p:title=\"" );
		        sb.append( BeanDDCreator.camelCaseToString( boClass.getSimpleName() ) );
		        sb.append( " Maintenance\" >\r\n" +
		        		"    <property name=\"maintainableItems\" >\r\n" +
		        		"      <list>\r\n" +
		        		"" );
		        for ( PropertyDescriptor pd : props ) {
		            if ( BeanDDCreator.isNormalProperty(pd) && isMaintainableProperty(pd) ) {

		                sb.append( "        <bean parent=\"MaintainableFieldDefinition\"\r\n" +
		                		"              p:name=\"" );
		                sb.append( pd.getName() );
		                if ( pd.getName().endsWith("active" ) ) {
		                    sb.append( "\"\r\n" +
		                            "              p:defaultValue=\"true\" />\r\n" );
		                } else if ( pd.getName().equals("versionNumber" ) ) {
		                    sb.append( "\" />\r\n" );
		                } else {
		                    sb.append( "\"\r\n" +
		                    "              p:required=\"true\" />\r\n" );
		                }
		            }
		        }
		        sb.append( "      </list>\r\n" +
		        		"    </property>\r\n" +
		        		"  </bean>\r\n" +
		        		"" );

		        sb.append(" <!-- workflow attributes -->");
		        sb.append("\r\n");
		        sb.append( "  <bean id=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-workflowAttributes\" parent=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-workflowAttributes-parentBean\" />\r\n" +
		        		"\r\n" +
		        		"  <bean id=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "MaintenanceDocument-workflowAttributes-parentBean\" abstract=\"true\" class=\"org.kuali.rice.kns.datadictionary.WorkflowAttributes\" />\r\n");// +
		//        		"    <property name=\"workflowPropertyGroups\" >\r\n" +
		//        		"      <list>\r\n" +
		//        		"        <bean parent=\"WorkflowPropertyGroup\">\r\n" +
		//        		"          <property name=\"workflowProperties\" >\r\n" +
		//        		"            <list>\r\n" +
		//        		"              <bean parent=\"WorkflowProperty\" p:path=\"newMaintainableObject.businessObject\" />\r\n" +
		//        		"              <bean parent=\"WorkflowProperty\" p:path=\"oldMaintainableObject.businessObject\" />\r\n" +
		//        		"            </list>\r\n" +
		//        		"          </property>\r\n" +
		//        		"        </bean>\r\n" +
		//        		"      </list>\r\n" +
		//        		"    </property>\r\n" +
		//        		"  </bean>\r\n" +

		        sb.append( "</beans>" );


		        FileWriter outputfile = null;
		        try
		        {
		        	outputfile = new FileWriter(getOutputFilePath(className, true) + boName + "MaintenanceDocument.xml");
		        	outputfile.write(sb.toString());
		        	System.out.println("Created: " + getOutputFilePath(className, true) + boName + "MaintenanceDocument.xml");
		        }
		        catch(IOException e)
		        {
		        	System.err.println("Error writing bean data to file.");
		        }
		        finally {
		        	outputfile.close();
		        }
	    	}
    	}
    	catch(DocumentException e)
    	{
    		System.err.println("Error parsing xml document.");
    	}
    }

    public static String getOutputFilePath(String boClassName, boolean isMaintDoc)
    {
    	String filepath = "src/java/";

    	filepath += boClassName.substring(0, boClassName.lastIndexOf(".")).replace(".", "/");

    	if(isMaintDoc)	{
    		filepath = filepath.replace("businessobject", "document");
    	}

    	filepath += "/datadictionary/";

    	return filepath;
    }

    public static String camelCaseToHelpParm( String className ) {
        StringBuffer newName = new StringBuffer( className );
        // lower case the 1st letter
        newName.replace(0, 1, newName.substring(0, 1).toLowerCase());
        // loop through, inserting spaces when cap
        for ( int i = 0; i < newName.length(); i++ ) {
            if ( Character.isUpperCase(newName.charAt(i)) ) {
                newName.insert(i, '_');
                i++;
            }
        }
        return newName.toString().toUpperCase().trim();
    }

    public static boolean isReferenceBoProperty( PropertyDescriptor p ) {
        return p.getPropertyType()!= null
                && BusinessObject.class.isAssignableFrom( p.getPropertyType() )
                && !p.getName().startsWith( "boNote" )
                && !p.getName().startsWith( "extension" )
                && !p.getName().equals( "newCollectionRecord" );
    }

    public static boolean isMaintainableProperty( PropertyDescriptor p ) {
    	return p.getPropertyType()!= null
    		&&	!p.getName().equals( "versionNumber" )
    		&&	!p.getName().equals( "lastUpdateId" )
    		&&	!p.getName().equals( "lastUpdateDate" )
    		&&	!p.getPropertyType().equals(List.class)
    		&&	!p.getName().endsWith( "codeAndDescription" );
    }
}
