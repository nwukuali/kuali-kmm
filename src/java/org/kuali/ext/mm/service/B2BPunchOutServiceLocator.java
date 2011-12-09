/**
 * 
 */
package org.kuali.ext.mm.service;


/**
 * @author schneppd
 *
 */
public interface B2BPunchOutServiceLocator {

    public abstract B2BPunchOutService getDefaultB2BPunchOutService();

    public abstract B2BPunchOutService getB2BPunchOutService(String vendorCredentialDomain,
            String vendorIdentity);

}