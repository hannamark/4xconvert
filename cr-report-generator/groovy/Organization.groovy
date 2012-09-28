import org.apache.commons.lang.StringUtils

/**
* @author Monish
*
*/
public class Organization {
    def orgPOId
    def name
    def cityOrMunicipality
    def deliveryAddressLine
    def postalCode
    def stateOrProvince
    def streetAddressLine
    def country
    def email
    def crId
    
    Organization(orgPOId,name,cityOrMunicipality,deliveryAddressLine,postalCode,stateOrProvince,streetAddressLine,country,email,crId){
        this.orgPOId = orgPOId;
        this.name = name;
        this.cityOrMunicipality = cityOrMunicipality;
        this.deliveryAddressLine = deliveryAddressLine;
        this.postalCode = postalCode;
        this.stateOrProvince = stateOrProvince;
        this.streetAddressLine = streetAddressLine;
        this.country = country;
        this.email = email;
        this.crId = crId;        
    }
    
    def public String getCompleteAddress() {
        
        def completeAddress = getName()
        completeAddress <<= ","
        completeAddress <<= getStreetAddressLine()
        if(StringUtils.isNotEmpty(getDeliveryAddressLine())) {
            completeAddress <<= ","
            completeAddress <<= getDeliveryAddressLine()
        }
        completeAddress <<= ","
        completeAddress <<= getCityOrMunicipality()
        completeAddress <<= ","
        completeAddress <<= getPostalCode()
        completeAddress <<= ","
        completeAddress <<= getStateOrProvince()
        completeAddress <<= ","
        completeAddress <<= getCountry()
        completeAddress <<= ","
        completeAddress <<= getEmail();
        return completeAddress;
    }
    
    def public boolean equals(other){
        boolean result = false;
        if(other instanceof Organization) {
            Organization o = (Organization) other
            
            if(!StringUtils.equals(getEmail(),o.getEmail())) {
                if ( !(Constants.EMAIL_IDS.contains(getEmail()) && Constants.EMAIL_IDS.contains(o.getEmail()))) {
                    return false;
                }
            }
            result = StringUtils.equals(getName(), o.getName()) &&
                     StringUtils.equals(getCityOrMunicipality(), o.getCityOrMunicipality()) &&
                     StringUtils.equals(getDeliveryAddressLine(), o.getDeliveryAddressLine()) &&
                     StringUtils.equals(getPostalCode(), o.getPostalCode()) &&
                     StringUtils.equals(getStateOrProvince(), o.getStateOrProvince()) &&
                     StringUtils.equals(getStreetAddressLine(), o.getStreetAddressLine()) &&
                     StringUtils.equals(getCountry(), o.getCountry())
        }
        return result
    }
}
