/*
 * Copyright 2007 The Kuali Foundation
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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *  This utility is used for generating OJB mapping descriptors from JPA annotated BO java classes.
 *  Utility will generate an OJB descriptor for the given classes in separate files located in
 *  org/kuali/ext/mm/ and will need to be combined in the ojb-mm.xml file.
 *
 * @param args	name of a file containing the list of BOs to be generated; in this case use boGen.Conf.xml located in the root of the project directory
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class JpaToOjbMetadata {

	public static void main( String[] args ) throws Exception {
		String filename = args[0];

    	try{
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(filename);

			List<Node> boList = document.selectNodes("//bo");

	    	for(Node bo : boList)
	    	{
//	    		String boName = bo.selectSingleNode("@name").getStringValue();
		        String className = bo.selectSingleNode("@class").getStringValue();

		        Class<? extends PersistableBusinessObject> boClass = (Class<? extends PersistableBusinessObject>)Class.forName( className );
//		        PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors( boClass );

				StringBuffer sb = new StringBuffer( 1000 );
				Table tableAnnotation = boClass.getAnnotation( Table.class );

				sb.append( "	<class-descriptor class=\"" ).append( boClass.getName() ).append( "\" table=\"" );
				sb.append( tableAnnotation.name() ).append( "\">\r\n" );

				getClassFields( boClass, sb, null );
				getReferences( boClass, sb );
				sb.append( "	</class-descriptor>\r\n" );

				System.out.println( sb.toString() );

//				FileWriter outputfile = null;
//		        try
//		        {
//		        	outputfile = new FileWriter(getOutputFilePath(className) + "ojb-" + boName + ".xml");
//		        	outputfile.write(sb.toString());
//		        	System.out.println("Created: " + getOutputFilePath(className) + "ojb-" + boName + ".xml");
//		        }
//		        catch(IOException e)
//		        {
//		        	System.err.println("Error writing bean data to file.");
//		        }
//		        finally {
//		        	outputfile.close();
//		        }
	    	}
    	}
    	catch(DocumentException e)
    	{
    		System.err.println("Error parsing xml document.");
    	}
    	catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static String getOutputFilePath(String boClassName)
    {
    	String filepath = "src/java/";

    	filepath += boClassName.substring(0, boClassName.lastIndexOf("businessobject")).replace(".", "/");

    	return filepath;
    }

	private static String javaToOjbDataType( Class<? extends Object> dataType ) {
		if ( dataType.equals( String.class ) ) {
			return "VARCHAR";
		} else if (dataType.equals(Long.class)) {
			return "BIGINT";
		} else if (dataType.equals(Integer.class)) {
			return "INTEGER";
		} else if (dataType.equals(KualiDecimal.class)) {
			return "DECIMAL";
		} else if (dataType.equals(java.util.Date.class) || dataType.equals(java.sql.Date.class)) {
			return "DATE";
		} else if (dataType.equals(java.sql.Timestamp.class)) {
			return "TIMESTAMP";
		}
		return "VARCHAR";
	}

	private static void getClassFields( Class<? extends Object> clazz, StringBuffer sb, Map<String,AttributeOverride> overrides ) {
		// first get annotation overrides
		if ( overrides == null ) {
			overrides = new HashMap<String,AttributeOverride>();
		}
		if ( clazz.getAnnotation( AttributeOverride.class ) != null ) {
			AttributeOverride ao = clazz.getAnnotation( AttributeOverride.class );
			if ( !overrides.containsKey(ao.name() ) ) {
				overrides.put(ao.name(), ao);
			}
		}
		if ( clazz.getAnnotation( AttributeOverrides.class ) != null ) {
			for ( AttributeOverride ao : (clazz.getAnnotation( AttributeOverrides.class )).value() ) {
				if ( !overrides.containsKey(ao.name() ) ) {
					overrides.put(ao.name(), ao);
				}
				overrides.put(ao.name(),ao);
			}
		}
		for ( Field field : clazz.getDeclaredFields() ) {
			Id id = field.getAnnotation( Id.class );
			Column column = field.getAnnotation( Column.class );
			if ( column != null ) {
				sb.append( "		<field-descriptor name=\"" );
				sb.append( field.getName() );
				sb.append( "\" column=\"" );
				if ( overrides.containsKey(field.getName() ) ) {
					sb.append( overrides.get(field.getName()).column().name() );
				} else {
					sb.append( column.name() );
				}
				sb.append( "\" jdbc-type=\"" );
				sb.append( javaToOjbDataType( field.getType() ) );
				sb.append( "\" " );
				if ( id != null ) {
					sb.append( "primarykey=\"true\" " );
				}
				if ( field.getName().equals( "objectId" ) ) {
					sb.append( "index=\"true\" " );
				}
				if ( field.getType() == boolean.class ) {
					sb.append( "conversion=\"org.kuali.rice.kns.util.OjbCharBooleanConversion\" " );
				}
				if ( field.getType() == KualiDecimal.class) {
					sb.append( "conversion=\"org.kuali.rice.kns.util.OjbKualiDecimalFieldConversion\" ");
				}
				if ( field.getName().equals( "versionNumber" ) ) {
					sb.append( "locking=\"true\" " );
				}
				sb.append( "/>\r\n" );
			}
		}
		if ( !clazz.equals( PersistableBusinessObject.class ) && clazz.getSuperclass() != null ) {
			getClassFields( clazz.getSuperclass(), sb, overrides );
		}
	}

	private static void getReferences( Class<? extends Object> clazz, StringBuffer sb ) {
		for ( Field field : clazz.getDeclaredFields() ) {
			JoinColumns multiKey = field.getAnnotation( JoinColumns.class );
			JoinColumn singleKey = field.getAnnotation( JoinColumn.class );
			if ( multiKey != null || singleKey != null ) {
				List<JoinColumn> keys = new ArrayList<JoinColumn>();
				if ( singleKey != null ) {
					keys.add( singleKey );
				}
				if ( multiKey != null ) {
					for ( JoinColumn col : multiKey.value() ) {
						keys.add( col );
					}
				}
				OneToOne oneToOne = field.getAnnotation( OneToOne.class );
				if ( oneToOne != null ) {
					sb.append( "		<reference-descriptor name=\"" );
					sb.append( field.getName() );
					sb.append( "\" class-ref=\"" );
					if ( !oneToOne.targetEntity().getName().equals( "void" ) ) {
						sb.append( oneToOne.targetEntity().getName() );
					} else {
						sb.append( field.getType().getName() );
					}
					sb.append( "\" auto-retrieve=\"true\" auto-update=\"none\" auto-delete=\"none\" proxy=\"true\">\r\n" );
					for ( JoinColumn col : keys ) {
						sb.append( "			<foreignkey field-ref=\"" );
						sb.append( getPropertyFromField( clazz, col.name() ) );
						sb.append( "\" />\r\n" );
					}
					sb.append( "		</reference-descriptor>\r\n" );
				}
				ManyToOne manyToOne = field.getAnnotation( ManyToOne.class );
				if ( manyToOne != null ) {
					sb.append( "		<reference-descriptor name=\"" );
					sb.append( field.getName() );
					sb.append( "\" class-ref=\"" );
					if ( !manyToOne.targetEntity().getName().equals( "void" ) ) {
						sb.append( manyToOne.targetEntity().getName() );
					} else {
						sb.append( field.getType().getName() );
					}
					sb.append( "\" auto-retrieve=\"true\" auto-update=\"none\" auto-delete=\"none\" proxy=\"true\">\r\n" );
					for ( JoinColumn col : keys ) {
						sb.append( "			<foreignkey field-ref=\"" );
						sb.append( getPropertyFromField( clazz, col.name() ) );
						sb.append( "\" />\r\n" );
					}
					sb.append( "		</reference-descriptor>\r\n" );
				}
				OneToMany oneToMany = field.getAnnotation( OneToMany.class );
				if ( oneToMany != null ) {
					sb.append( "		<collection-descriptor name=\"" );
					sb.append( field.getName() );
					sb.append( "\" element-class-ref=\"" );
					sb.append( oneToMany.targetEntity().getName() );
					sb.append( "\" collection-class=\"org.apache.ojb.broker.util.collections.ManageableArrayList\" auto-retrieve=\"true\" auto-update=\"object\" auto-delete=\"object\" proxy=\"true\">\r\n" );
					for ( JoinColumn col : keys ) {
						sb.append( "			<inverse-foreignkey field-ref=\"" );
						sb.append( getPropertyFromField( clazz, col.name() ) );
						sb.append( "\" />\r\n" );
					}
					sb.append( "		</collection-descriptor>\r\n" );
				}
			}
		}
	}

	private static String getPropertyFromField( Class<? extends Object> clazz, String colName ) {
		for ( Field field : clazz.getDeclaredFields() ) {
			Column column = field.getAnnotation( Column.class );
			if ( column != null ) {
				if ( column.name().equals( colName ) ) {
					return field.getName();
				}
			}
		}
		return "";
	}
}
