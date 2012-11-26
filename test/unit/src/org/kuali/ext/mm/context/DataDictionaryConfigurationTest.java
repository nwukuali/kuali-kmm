/*
 * Copyright 2007 The Kuali Foundation.
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
package org.kuali.ext.mm.context;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.ext.mm.ConfigureContext;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.suite.AnnotationTestSuite;
import org.kuali.ext.mm.suite.PreCommitSuite;
import org.kuali.rice.krad.datadictionary.BusinessObjectEntry;
import org.kuali.rice.krad.datadictionary.DataDictionary;
import org.kuali.rice.krad.datadictionary.DocumentEntry;
import org.kuali.rice.krad.service.DataDictionaryService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@AnnotationTestSuite(PreCommitSuite.class)
@ConfigureContext
public class DataDictionaryConfigurationTest extends KualiTestBase {
    private DataDictionary dataDictionary;

    @Override
    @Before
    public void setUp() throws Exception {
        dataDictionary = SpringContext.getBean(DataDictionaryService.class).getDataDictionary();
    }

    @Test
    public void testAllDataDicitionaryDocumentTypesExistInWorkflowDocumentTypeTable()
            throws Exception {
        HashSet<String> workflowDocumentTypeNames = new HashSet<String>();
        DataSource mySource = (DataSource) SpringContext.getBean("dataSource");
        Connection dbCon = null;
        try {

            dbCon = mySource.getConnection();
            Statement dbAsk = dbCon.createStatement();
            ResultSet dbAnswer = dbAsk
                    .executeQuery("select DOC_TYP_NM from KREW_DOC_TYP_T where CUR_IND = 1");
            while (dbAnswer.next()) {
                String docName = dbAnswer.getString(1);
                if (StringUtils.isNotBlank(docName)) {
                    workflowDocumentTypeNames.add(docName);
                }
            }

        }
        catch (Exception e) {
            throw (e);
        }
        // Using HashSet since duplicate objects would otherwise be returned
        HashSet<DocumentEntry> documentEntries = new HashSet(dataDictionary.getDocumentEntries()
                .values());
        List<String> ddEntriesWithMissingTypes = new ArrayList<String>();
        for (DocumentEntry documentEntry : documentEntries) {
            String name = documentEntry.getDocumentTypeName();
            String testName = new String(" ");
            // if (documentEntry instanceof StoresMaintenanceDocumentEntry){
            // testName=((StoresMaintenanceDocumentEntry)documentEntry).getBusinessObjectClass().getName();
            // }else{
            // testName=documentEntry.getDocumentClass().getName();
            // }
            if (!workflowDocumentTypeNames.contains(name)
                    && !"RiceUserMaintenanceDocument".equals(name) && !testName.contains("rice")) {
                ddEntriesWithMissingTypes.add(name);
            }
            else {
                workflowDocumentTypeNames.remove(name);
            }
        }

        if (workflowDocumentTypeNames.size() > 0) {
            try {
                // If documents are parent docs, then they aren't superfluous.
                String queryString = "select distinct doc_typ_nm from krew_doc_typ_t"
                        + " where doc_typ_id in (select parnt_id from krew_doc_typ_t"
                        + " where actv_ind = 1" + " and cur_ind = 1)";
                Statement dbAsk = dbCon.createStatement();
                ResultSet dbAnswer = dbAsk.executeQuery(queryString);
                while (dbAnswer.next()) {
                    String docName = dbAnswer.getString(1);
                    if (StringUtils.isNotBlank(docName)) {
                        workflowDocumentTypeNames.remove(docName);
                    }
                }
            }
            catch (Exception e) {
                throw (e);
            }

            System.err.print("superfluousTypesDefinedInWorkflowDatabase: "
                    + workflowDocumentTypeNames);
        }
        assertEquals("documentTypesNotDefinedInWorkflowDatabase: " + ddEntriesWithMissingTypes, 0,
                ddEntriesWithMissingTypes.size());
    }

    private final static Class[] INACTIVATEABLE_LOOKUP_IGNORE_CLASSES = new Class[] {};

    // org.kuali.kfs.coa.businessobject.Account is excepted from testActiveFieldExistInLookupAndResultSection because it uses the
    // active-derived Closed? indicator instead (KFSMI-1393)

    @Test
		@Ignore
    public void testActiveFieldExistInLookupAndResultSection() throws Exception {
			//TODO: NWU - Re-enable test once get lookup is resolved to new structure
//        List<Class> noActiveFieldClassList = new ArrayList<Class>();
//
//        List<Class> ignoreClasses = Arrays.asList(INACTIVATEABLE_LOOKUP_IGNORE_CLASSES);
//
//        for (BusinessObjectEntry businessObjectEntry : dataDictionary.getBusinessObjectEntries()
//                .values()) {
//            if (!businessObjectEntry.getBusinessObjectClass().getName()
//                    .startsWith("org.kuali.rice")
//                    && !ignoreClasses.contains(businessObjectEntry.getBusinessObjectClass())) {
//                List<Class<?>> iList = Arrays.asList(businessObjectEntry.getBusinessObjectClass()
//                        .getInterfaces());
//                try {
//                    if (iList.contains(Class.forName("org.kuali.rice.kns.bo.Inactivateable"))) {
//                        LookupDefinition lookupDefinition = businessObjectEntry.getLookupDefinition();
//                        if (lookupDefinition != null
//                                && !(lookupDefinition.getLookupFieldNames().contains("active") && lookupDefinition
//                                        .getLookupFieldNames().contains("active"))) {
//                            noActiveFieldClassList
//                                    .add(businessObjectEntry.getBusinessObjectClass());
//                        }
//                    }
//                }
//                catch (ClassNotFoundException e) {
//                    throw (e);
//                }
//            }
//        }
//        assertEquals(noActiveFieldClassList.toString(), 0, noActiveFieldClassList.size());
    }

    @Test
    public void testAllBusinessObjectsHaveObjectLabel() throws Exception {
        List<Class> noObjectLabelClassList = new ArrayList<Class>();
        for (BusinessObjectEntry businessObjectEntry : dataDictionary.getBusinessObjectEntries()
                .values()) {
            if (StringUtils.isBlank(businessObjectEntry.getObjectLabel())) {
                noObjectLabelClassList.add(businessObjectEntry.getBusinessObjectClass());
            }
        }
        assertEquals(noObjectLabelClassList.toString(), 0, noObjectLabelClassList.size());
    }

}
