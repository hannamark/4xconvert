package gov.nih.nci.pa.iso.util;



import static org.junit.Assert.*;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelPhone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;



public class DSetConverterTest {

    
    @Test
    public void convertPhoneListToDSetTest () {
        List<String> phones = new ArrayList<String>();
        phones.add("7037071111");
        phones.add("7037071112");
        phones.add("7037071113");
        DSet<Tel> master = new DSet<Tel>();
        DSet<Tel> dset = DSetConverter.convertListToDSet(phones, "PHONE",master);
        assertNotNull(dset);
        Iterator it = dset.getItem().iterator();
        while(it.hasNext()){
           Object o = it.next();
           if( o instanceof TelPhone) {
               String telNumber = ((TelPhone)o).getValue().toString().substring(4,14);
               assertTrue(phones.contains(telNumber));
               
           }
        }
    }
    
    @Test
    public void convertDsetPhoneToList(){
        List<String> in = new ArrayList<String>();
        in.add("7037071111");
        in.add("7037071112");
        in.add("7037071113");
        DSet<Tel> master = new DSet<Tel>();
        DSet<Tel> dset = DSetConverter.convertListToDSet(in, "PHONE",master);        
        List dsetTel = DSetConverter.convertDSetToList(dset, "PHONE");
        for (int i=0 ; i<dsetTel.size(); i++) {
               assertTrue(in.contains(dsetTel.get(i))); 
           }
        }
}
