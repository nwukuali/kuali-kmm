/*
 * Copyright 2008 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.utility;

import java.beans.PropertyDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.kuali.rice.kns.bo.BusinessObject;



public class BeanDDCreator {

    /**
     * Use this application to generate Data dictionary drafts for the Materiel Management project.
     * Files will be placed in org.kuali.ext.mm.businessobject.datadictionary.  This generator
     * will produce an incomplete draft of the data dictionary and will require replacement of
     * certain fields, especially those pre-populated with "FILL ME IN".  Also there may be
     * additional property configurations required such as maxLength, control size, and attribute descriptions.
     * @param args	name of a file containing the list of BOs to be generated; in this case use boGen.Conf.xml located in the root of the project directory
     */
    public static void main(String[] args) throws Exception {
    	String filename = "boGen.conf.xml";

    	try{
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(filename);

			List<Node> boList = document.selectNodes("//bo");

	    	for(Node bo : boList)
	    	{
	    		String boName = bo.selectSingleNode("@name").getStringValue();
		        String className = bo.selectSingleNode("@class").getStringValue();

		        Class<? extends BusinessObject> boClass = (Class<? extends BusinessObject>)Class.forName( className );
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
		        sb.append( "\" parent=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "-parentBean\" />\r\n" +
		        		"\r\n" +
		        		"  <bean id=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "-parentBean\" abstract=\"true\" parent=\"BusinessObjectEntry\"\r\n" +
		        		"        p:businessObjectClass=\"" );
		        sb.append( boClass.getName() );
		        sb.append( "\"\r\n" );
		        sb.append( "        p:titleAttribute=\"" );
		        sb.append( "FILL ME IN" );
		        sb.append( "\"\r\n" );
		        sb.append( "        p:objectLabel=\"" );
		        sb.append(  camelCaseToString( boClass.getSimpleName() ) );
		        sb.append( "\"\r\n" );
		        sb.append( "        p:inquiryDefinition-ref=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "-inquiryDefinition\"\r\n" );
		        sb.append( "        p:lookupDefinition-ref=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "-lookupDefinition\" >\r\n" );
		        sb.append( "    <property name=\"attributes\" >\r\n" +
		        		"      <list>\r\n" );
		        for ( PropertyDescriptor p : props ) {
		            if ( isNormalProperty(p) ) {
		                sb.append( "        <ref bean=\"" ).append( boClass.getSimpleName() ).append( '-' );
		                sb.append( p.getName() );
		                sb.append( "\" />\r\n" );
		            }
		        }

		        sb.append( "      </list>\r\n" +
		        		"    </property>\r\n" +
		        		"  </bean>\r\n" +
		        		"\r\n" );
		        for ( PropertyDescriptor p : props ) {
		            if ( isNormalProperty(p) ) {

		                if ( p.getName().equals( "versionNumber" ) ) {
		                    sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    sb.append( getSimpleAbstractInheritanceBean(boClass, p.getName(), "AttributeReferenceDummy-versionNumber" ) );

		                } /*else if ( p.getName().endsWith("chartOfAccountsCode" ) ) {
		                    sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    sb.append( getSimpleAbstractInheritanceBean(boClass, p.getName(), "Chart-chartOfAccountsCode" ) );

		                } else if ( p.getName().endsWith("organizationCode" ) ) {
		                    sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    sb.append( getSimpleAbstractInheritanceBean(boClass, p.getName(), "Org-organizationCode" ) );

		                } else if ( p.getName().endsWith("accountNumber" ) ) {
		                    sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    sb.append( getSimpleAbstractInheritanceBean(boClass, p.getName(), "Account-accountNumber" ) );

		                } else if ( p.getName().equals("active" ) ) {
		                    sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    sb.append( getSimpleAbstractInheritanceBean(boClass, p.getName(), "GenericAttributes-activeIndicator" ) );

		                } else if ( p.getName().equals("codeAndDescription" ) ) {
		                    sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    sb.append( getSimpleAbstractInheritanceBeanWithLabel(boClass, p.getName(), "CommonField-CodeAndDescription", camelCaseToString(boClass.getSimpleName()) ) );

		                } else if ( p.getPropertyType() == Boolean.TYPE ) {
		                    sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    sb.append( getSimpleAbstractInheritanceBean(boClass, p.getName(), "GenericAttributes-genericBoolean" ) );

		                } else if ( p.getPropertyType() == Date.class && !p.getName().equals("lastUpdateDate")) {
		                    sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    sb.append( getSimpleAbstractInheritanceBeanWithLabel(boClass, p.getName(), "GenericAttributes-genericDate", camelCaseToString(p.getName()) ) );

		                } else if( p.getName().equals("lastUpdateDate")) {
		                	sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    sb.append( getSimpleAbstractInheritanceBeanWithLabel(boClass, p.getName(), "GenericAttributes-genericTimestamp", camelCaseToString(p.getName()) ) );
		                }
		                */else {
		                    // attribute bean
		                    sb.append( getSimpleParentBeanReference( boClass, p.getName() ) );
		                    // attribute parent bean
		                    sb.append( "  <bean id=\"" ).append( boClass.getSimpleName() ).append( '-' );
		                    sb.append( p.getName() ).append( "-parentBean\" parent=\"AttributeDefinition\" abstract=\"true\"\r\n" );
		                    sb.append( "        p:name=\"" ).append( p.getName() ).append( "\"\r\n" );
		                    sb.append( "        p:forceUppercase=\"false\"\r\n" );
		                    sb.append( "        p:label=\"" ).append( camelCaseToString(p.getName()) ).append( "\"\r\n" );
		                    sb.append( "        p:shortLabel=\"" ).append( camelCaseToString(p.getName()) ).append( "\"\r\n" );
		                    sb.append( "        p:maxLength=\"10\"\r\n" );
		                    sb.append( "        p:required=\"false\" >\r\n" );
		                    sb.append( "    <property name=\"validationPattern\" >\r\n" +
		                            "      <bean parent=\"AnyCharacterValidationPattern\"\r\n" +
		                            "            p:allowWhitespace=\"true\" />\r\n" +
		                            "    </property>\r\n" +
		                            "    <property name=\"control\" >\r\n");
		                    if(p.getName().equals("lastUpdateDate"))	{
		                    	sb.append( "      <bean parent=\"HiddenControlDefinition\" />\r\n");
		                    }
		                    else if (p.getName().equals("lastUpdateId"))	{
		                    	sb.append( "      <bean parent=\"HiddenControlDefinition\" />\r\n");
		                    }
		                    else {
		                    	sb.append( "      <bean parent=\"TextControlDefinition\"\r\n" +
		                            	"            p:size=\"10\" />\r\n");
		                    }
		                    sb.append( "    </property>\r\n" +
		                            "  </bean>\r\n" );

		                }
		                sb.append( "\r\n" );
		            }
		        }
		        // inquiry definition
		        sb.append("<!-- Business Object Inquiry Definition -->\r\n");
		        sb.append( "\r\n" );
		        sb.append( getSimpleParentBeanReference( boClass, "inquiryDefinition" ) );
		        sb.append( "\r\n" );
		        sb.append( "  <bean id=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "-inquiryDefinition-parentBean\" abstract=\"true\" parent=\"InquiryDefinition\"\r\n" +
		        		"        p:title=\"" );
		        sb.append( camelCaseToString(boClass.getSimpleName() ) );
		        sb.append( " Inquiry\" >\r\n" +
		        		"    <property name=\"inquirySections\" >\r\n" +
		        		"      <list>\r\n" +
		        		"        <bean parent=\"InquirySectionDefinition\"\r\n" +
		        		"              p:title=\"" );
		        sb.append( camelCaseToString(boClass.getSimpleName() ) );
		        sb.append( " Attributes\"\r\n" +
		        		"              p:numberOfColumns=\"1\" >\r\n" +
		        		"          <property name=\"inquiryFields\" >\r\n" +
		        		"            <list>\r\n" );
		        for ( PropertyDescriptor p : props ) {
		            if ( isNormalProperty(p) ) {
		                sb.append("              <bean parent=\"FieldDefinition\" p:attributeName=\"" );
		                sb.append( p.getName() ).append( "\" />\r\n" );
		            }
		        }
		        sb.append( "            </list>\r\n" +
		        		"          </property>\r\n" +
		        		"        </bean>\r\n" +
		        		"      </list>\r\n" +
		        		"    </property>\r\n" +
		        		"  </bean>\r\n" +
		        		"\r\n" );

		        sb.append("<!-- Business Object Lookup Definition -->");
		        sb.append( "\r\n" );
		        sb.append( getSimpleParentBeanReference( boClass, "lookupDefinition" ) );
		        sb.append( "\r\n" );
		        sb.append( "  <bean id=\"" );
		        sb.append( boClass.getSimpleName() );
		        sb.append( "-lookupDefinition-parentBean\" abstract=\"true\" parent=\"LookupDefinition\"\r\n" +
		                "        p:title=\"" );
		        sb.append( camelCaseToString(boClass.getSimpleName() ) );
		        sb.append( " Lookup\" \r\n" );
		        sb.append( " >\r\n" );
		        sb.append( "    <property name=\"defaultSort\" >\r\n" +
		        		"      <bean parent=\"SortDefinition\">\r\n" +
		        		"        <property name=\"attributeNames\" >\r\n" +
		        		"          <list>\r\n" +
		        		"            <value>FILL ME IN</value>\r\n" +
		        		"          </list>\r\n" +
		        		"        </property>\r\n" +
		        		"        <property name=\"sortAscending\" value=\"true\" />\r\n" +
		        		"      </bean>\r\n" +
		        		"    </property>\r\n" +
		        		"    <property name=\"lookupFields\" >\r\n" +
		        		"      <list>\r\n" );
		        for ( PropertyDescriptor p : props ) {
		            if ( isNormalProperty(p) ) {
		                sb.append("        <bean parent=\"FieldDefinition\" p:attributeName=\"" );
		                sb.append( p.getName() ).append( "\" />\r\n" );
		            }
		        }
		        sb.append( "      </list>\r\n" +
		        		"    </property>\r\n" +
		        		"    <property name=\"resultFields\" >\r\n" +
		        		"      <list>\r\n" );
		        for ( PropertyDescriptor p : props ) {
		            if ( isNormalProperty(p) ) {
		                sb.append("        <bean parent=\"FieldDefinition\" p:attributeName=\"" );
		                sb.append( p.getName() ).append( "\" />\r\n" );
		            }
		        }
		        sb.append( "      </list>\r\n" +
		        		"    </property>\r\n" +
		        		"  </bean>\r\n" );
		        sb.append( "\r\n</beans>" );

		        FileWriter outputfile = null;
		        try
		        {
		        	outputfile = new FileWriter(getOutputFilePath(className, false) + boName + ".xml");
		        	outputfile.write(sb.toString());
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

    public static String getSimpleAbstractInheritanceBean( Class<? extends BusinessObject> boClass, String propertyName, String parentBean ) {
        StringBuffer sb = new StringBuffer( 100 );
        sb.append( "  <bean id=\"" );
        sb.append( boClass.getSimpleName() );
        sb.append( "-" ).append( propertyName ).append( "-parentBean\" abstract=\"true\" parent=\"" );
        sb.append( parentBean ).append( "\" \r\n" );
        sb.append( "        p:name=\"" ).append( propertyName ).append( "\"\r\n" );
        sb.append( "        p:required=\"false\" />\r\n" );
        return sb.toString();
    }

    public static String getSimpleAbstractInheritanceBeanWithLabel( Class<? extends BusinessObject> boClass, String propertyName, String parentBean, String label ) {
        StringBuffer sb = new StringBuffer( 100 );
        sb.append( "  <bean id=\"" );
        sb.append( boClass.getSimpleName() );
        sb.append( "-" ).append( propertyName ).append( "-parentBean\" abstract=\"true\" parent=\"" );
        sb.append( parentBean ).append( "\" \r\n" );
        sb.append( "        p:name=\"" ).append( propertyName ).append( "\"\r\n" );
        sb.append( "        p:label=\"" ).append( label ).append( "\"\r\n" );
        sb.append( "        p:shortLabel=\"" ).append( label ).append( "\"\r\n" );
        sb.append( "        p:required=\"false\" />\r\n" );
        return sb.toString();
    }

    public static String getSimpleParentBeanReference( Class<? extends BusinessObject> boClass, String propertyName ) {
        StringBuffer sb = new StringBuffer( 100 );
        sb.append( "  <bean id=\"" );
        sb.append( boClass.getSimpleName() );
        sb.append( "-" ).append( propertyName ).append( "\" parent=\"" );
        sb.append( boClass.getSimpleName() );
        sb.append( "-" ).append( propertyName ).append( "-parentBean\" />\r\n" );
        return sb.toString();
    }

    public static boolean isNormalProperty( PropertyDescriptor p ) {
        return p.getPropertyType()!= null
                && !BusinessObject.class.isAssignableFrom( p.getPropertyType() )
                && !p.getName().equals( "objectId" )
                && !p.getName().equals( "class" )
                && !p.getName().startsWith( "boNote" )
                && !p.getName().startsWith( "autoIncrementSet" )
                && !p.getPropertyType().equals(List.class)
                && !p.getName().equals( "newCollectionRecord" );
    }

    public static String camelCaseToString( String className ) {
        StringBuffer newName = new StringBuffer( className );
        // upper case the 1st letter
        newName.replace(0, 1, newName.substring(0, 1).toUpperCase());
        // loop through, inserting spaces when cap
        for ( int i = 0; i < newName.length(); i++ ) {
            if ( Character.isUpperCase(newName.charAt(i)) ) {
                newName.insert(i, ' ');
                i++;
            }
        }

        return newName.toString().trim().replace( "Uc", "UC" );
    }
}
