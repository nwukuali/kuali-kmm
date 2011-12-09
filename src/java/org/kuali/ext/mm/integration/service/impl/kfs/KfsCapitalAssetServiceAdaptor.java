/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.ext.mm.integration.service.impl.kfs;

import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialObjectCode;
import org.kuali.ext.mm.integration.service.FinancialBusinessObjectService;
import org.kuali.ext.mm.integration.service.FinancialCapitalAssetService;
import org.kuali.ext.mm.integration.service.FinancialObjectCodeService;
import org.kuali.ext.mm.integration.service.FinancialParameterService;
import org.kuali.kfs.module.cam.CamsConstants;
import org.kuali.kfs.module.cam.businessobject.Asset;
import org.kuali.kfs.module.cam.businessobject.AssetGlobal;
import org.kuali.kfs.module.cam.businessobject.AssetType;
import org.kuali.kfs.module.cam.document.service.AssetService;

/**
 * @author harsha07
 */
public class KfsCapitalAssetServiceAdaptor extends KfsServiceAdaptor<AssetService> implements
        FinancialCapitalAssetService {
    private static final Logger LOG = Logger.getLogger(KfsCapitalAssetServiceAdaptor.class);
    private FinancialBusinessObjectService businessObjectService;
    private FinancialObjectCodeService objectCodeService;
    private FinancialParameterService parameterService;

    /**
     * @param name
     */
    public KfsCapitalAssetServiceAdaptor(QName name) {
        super(name);
    }

    public KfsCapitalAssetServiceAdaptor(QName name,
            FinancialBusinessObjectService businessObjectService,
            FinancialObjectCodeService objectCodeService, FinancialParameterService parameterService) {
        super(name);
        this.businessObjectService = businessObjectService;
        this.objectCodeService = objectCodeService;
        this.parameterService = parameterService;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialCapitalAssetService#isTagAlreadyInUse(java.lang.String)
     */
    public boolean isTagAlreadyInUse(String tagNumber) {
        List<Asset> matches = getService().findActiveAssetsMatchingTagNumber(tagNumber);
        return matches != null && !matches.isEmpty();
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialCapitalAssetService#isValidActiveAsset(java.lang.Long)
     */
    public boolean isValidActiveAsset(Long capitalAssetNumber) {
        LOG.debug("Validate asset by checking existence and not retired");
        Asset asset = getBusinessObjectService().findBySinglePrimaryKey(Asset.class,
                capitalAssetNumber);
        return asset != null && !getService().isAssetRetired(asset);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialCapitalAssetService#isValidAssetType(java.lang.String)
     */
    public boolean isValidAssetType(String capitalAssetTypeCode) {
        HashMap<String, String> primaryKeys = new HashMap<String, String>();
        primaryKeys.put("capitalAssetTypeCode", capitalAssetTypeCode);
        AssetType assetType = (AssetType) getBusinessObjectService().findByPrimaryKey(
                AssetType.class, primaryKeys);
        return assetType != null && assetType.isActive();
    }

    /**
     * Gets the businessObjectService property
     *
     * @return Returns the businessObjectService
     */
    public FinancialBusinessObjectService getBusinessObjectService() {
        return this.businessObjectService;
    }

    /**
     * Sets the businessObjectService property value
     *
     * @param businessObjectService The businessObjectService to set
     */
    public void setBusinessObjectService(FinancialBusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialCapitalAssetService#isCapitalAssetObjectSubType(java.lang.Integer,
     *      java.lang.String, java.lang.String)
     */
    public boolean isCapitalAssetObjectSubType(Integer fiscalYear, String chartOfAccountsCode,
            String objectCode) {
        List<String> validCapitalObjSubTypes = getParameterService().getParameterValues(
                AssetGlobal.class, CamsConstants.Parameters.CAPITAL_OBJECT_SUB_TYPES);
        FinancialObjectCode objCode = getObjectCodeService().getByPrimaryId(fiscalYear,
                chartOfAccountsCode, objectCode);
        return validCapitalObjSubTypes != null && objCode != null
                && validCapitalObjSubTypes.contains(objCode.getFinancialObjectSubTypeCode());
    }

    /**
     * Gets the objectCodeService property
     *
     * @return Returns the objectCodeService
     */
    public FinancialObjectCodeService getObjectCodeService() {
        return this.objectCodeService;
    }

    /**
     * Sets the objectCodeService property value
     *
     * @param objectCodeService The objectCodeService to set
     */
    public void setObjectCodeService(FinancialObjectCodeService objectCodeService) {
        this.objectCodeService = objectCodeService;
    }

    /**
     * Gets the parameterService property
     *
     * @return Returns the parameterService
     */
    public FinancialParameterService getParameterService() {
        return this.parameterService;
    }

    /**
     * Sets the parameterService property value
     *
     * @param parameterService The parameterService to set
     */
    public void setParameterService(FinancialParameterService parameterService) {
        this.parameterService = parameterService;
    }


}
