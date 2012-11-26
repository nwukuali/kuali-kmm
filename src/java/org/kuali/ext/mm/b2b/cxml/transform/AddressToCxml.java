/**
 * 
 */
package org.kuali.ext.mm.b2b.cxml.transform;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.b2b.cxml.types.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.AddressService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.location.api.country.CountryService;

import java.lang.Object;


/**
 * @author harsha07
 *
 */
public class AddressToCxml implements CxmlTransformer<Address, org.kuali.ext.mm.businessobject.Address>{

    /**
     * @see org.kuali.ext.mm.b2b.cxml.transform.CxmlTransformer#transform(java.lang.Object, java.lang.Object[])
     */
    @Override
    public Address transform(org.kuali.ext.mm.businessobject.Address address, Object... options) {
        boolean includePhone = false;
        
        if(options != null && options.length > 0 &&  ((Boolean)options[0]).booleanValue()){
            includePhone = true;
        }
        org.kuali.ext.mm.b2b.cxml.types.Address cxmlAddress = new org.kuali.ext.mm.b2b.cxml.types.Address();
        cxmlAddress.setName(new Name());
        cxmlAddress.getName().setLang("en");
        cxmlAddress.getName().setContent(KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(MMKeyConstants.B2B_COMPANY_NAME));
        cxmlAddress.setAddressID(address.getAddressId());

        Email email = new Email();
        email.setContent(address.getCustomerProfile().getProfileEmail());
        email.setName(MMConstants.B2BCxml.ADDRESS_EMAIL_NAME);
        cxmlAddress.setEmail(email);
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setName(MMConstants.B2BCxml.POSTAL_ADDRESS_NAME);
        postalAddress.getDeliverTo().add(address.getAddressName());
        postalAddress.getStreet().add(address.getAddressLine1());
        if (StringUtils.isNotBlank(address.getAddressLine2()))
            postalAddress.getStreet().add(address.getAddressLine2());
        postalAddress.setCity(address.getAddressCityName());
        postalAddress.setState(address.getAddressStateCode());
        postalAddress.setPostalCode(address.getAddressPostalCode());
        Country country = new Country();
				//TODO: NWU - Not sure if getCountry is the same as findByPrimaryKey
        String countryName = SpringContext.getBean(CountryService.class).getCountry(
                address.getAddressCountryCode()).getName();
        country.setContent(countryName);
        country.setIsoCountryCode(address.getAddressCountryCode());
        postalAddress.setCountry(country);
        cxmlAddress.setPostalAddress(postalAddress);

        if (includePhone) {
            Phone phone = new Phone();
            TelephoneNumber telephoneNumber = new TelephoneNumber();
            AddressService addressService = SpringContext.getBean(AddressService.class);
            telephoneNumber.setAreaOrCityCode(addressService.getPhoneNumberAreaCode(address
                    .getAddressPhoneNumber()));
            telephoneNumber.setNumber(addressService.getPhoneNumberNoAreaCode(address
                    .getAddressPhoneNumber()));
            telephoneNumber.setExtension(addressService.getPhoneNumberExtension(address
                    .getAddressPhoneNumber()));
            CountryCode countryCode = new CountryCode();
            countryCode.setIsoCountryCode(country.getIsoCountryCode());
            countryCode.setContent(country.getContent());
            telephoneNumber.setCountryCode(countryCode);
            phone.setTelephoneNumber(telephoneNumber);
            cxmlAddress.setPhone(phone);
        }


        return cxmlAddress;
    
    }

}
