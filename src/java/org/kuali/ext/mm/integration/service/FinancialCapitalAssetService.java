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

package org.kuali.ext.mm.integration.service;

/**
 * @author harsha07
 */
public interface FinancialCapitalAssetService {

    /**
     * Validates the asset number to be a valid existing asset that is not retired
     *
     * @param capitalAssetNumber Asset Number
     * @return true for valid non-retired asset
     */
    public boolean isValidActiveAsset(Long capitalAssetNumber);

    /**
     * Validates the capital asset type code
     *
     * @param capitalAssetTypeCode Asset Type code
     * @return true for a valid asset type
     */
    public boolean isValidAssetType(String capitalAssetTypeCode);

    /**
     * Is tag already used by an active asset
     *
     * @param tagNumber Tag No
     * @return true if an active asset is already using it
     */
    public boolean isTagAlreadyInUse(String tagNumber);

    public boolean isCapitalAssetObjectSubType(Integer fiscalYear, String chartCode,
            String finObjectCode);
}
