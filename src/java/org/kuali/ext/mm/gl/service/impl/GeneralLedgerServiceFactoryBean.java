/**
 * 
 */
package org.kuali.ext.mm.gl.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.springframework.beans.factory.FactoryBean;


/**
 * @author harsha07
 */
public class GeneralLedgerServiceFactoryBean implements FactoryBean {
    private String glImplementationType;
    private Map<String, GeneralLedgerBuilderService> serviceMap = new HashMap<String, GeneralLedgerBuilderService>();

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Object getObject() throws Exception {
        return this.serviceMap.get(this.glImplementationType);
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class getObjectType() {
        return GeneralLedgerBuilderService.class;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }


    /**
     * Gets the glImplementationType property
     * 
     * @return Returns the glImplementationType
     */
    public String getGlImplementationType() {
        return this.glImplementationType;
    }

    /**
     * Sets the glImplementationType property value
     * 
     * @param glImplementationType The glImplementationType to set
     */
    public void setGlImplementationType(String glImplementationType) {
        this.glImplementationType = glImplementationType;
    }

    /**
     * Gets the serviceMap property
     * 
     * @return Returns the serviceMap
     */
    public Map<String, GeneralLedgerBuilderService> getServiceMap() {
        return this.serviceMap;
    }

    /**
     * Sets the serviceMap property value
     * 
     * @param serviceMap The serviceMap to set
     */
    public void setServiceMap(Map<String, GeneralLedgerBuilderService> serviceMap) {
        this.serviceMap = serviceMap;
    }


}
