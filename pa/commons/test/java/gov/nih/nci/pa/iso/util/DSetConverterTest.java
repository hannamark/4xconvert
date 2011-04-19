/*
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
* 
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
* 
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract 
* or otherwise,or
*  
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or 
* 
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to 
* 
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so; 
* 
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof); 
* 
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
* 
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
* 
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally 
* appear.
* 
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
* 
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
* 
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
* 
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN 
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
* 
*/
package gov.nih.nci.pa.iso.util;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelPerson;
import gov.nih.nci.iso21090.TelPhone;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;



public class DSetConverterTest {

    
    @Test
    public void convertDSetCdToListTest () {
        DSet<Cd> dSet = new DSet<Cd>();
        Set<Cd> items = new HashSet<Cd>();
        items.add(CdConverter.convertStringToCd("anatomicSite1"));
        items.add(CdConverter.convertStringToCd("anatomicSite2"));  
        dSet.setItem(items);
        List<String> strList = DSetConverter.convertDSetCdToList(dSet);
        assertEquals(2, strList.size());
        assertTrue(strList.get(0).contains("anatomicSite"));
    }
    
    @Test
    public void convertPhoneListToDSetTest () {
        List<String> phones = new ArrayList<String>();
        phones.add("7037071111");
        phones.add("7037071112");
        phones.add("7037071113");
        DSet<Tel> master = new DSet<Tel>();
        DSet<Tel> dset = DSetConverter.convertListToDSet(phones, "PHONE",master);
        assertNotNull(dset);
        Iterator<Tel> it = dset.getItem().iterator();
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
        List<?> dsetTel = DSetConverter.convertDSetToList(dset, "PHONE");
        for (int i=0 ; i<dsetTel.size(); i++) {
               assertTrue(in.contains(dsetTel.get(i))); 
           }
        }
    
    
    @Test
    public void getTelByTypeTest () {
        List<String> phones = new ArrayList<String>();
        phones.add("7037071111");
        phones.add("7037071112");
        phones.add("7037071113");
        List<String> email = new ArrayList<String>();
        email.add("aa@bbb.com");
        DSet<Tel> master = new DSet<Tel>();
        DSet<Tel> dset = DSetConverter.convertListToDSet(phones, "PHONE", master);
        dset = DSetConverter.convertListToDSet(email, "EMAIL", dset);
        assertEquals(3, DSetConverter.getTelByType(dset, "tel:").size());
        assertEquals(1, DSetConverter.getTelByType(dset, "mailto:").size());
        
    }    
    
    /**
     * test the isPhone method for the case of a TelPhone
     */
    @Test
    public void testIsPhoneCaseTelPhone() {
        assertTrue(DSetConverter.isPhone(new TelPhone()));
    }

    /**
     * test the isPhone method for the case of a TelUrl phone url
     * @throws UnsupportedEncodingException Should not happen. UTF-8 is supported
     */
    @Test
    public void testIsPhoneCaseTelUrlPhone() throws UnsupportedEncodingException {
        TelPerson telPerson = new TelPerson();
        telPerson.setValue(URI.create("tel:" + URLEncoder.encode("111-111-1111", "UTF-8")));
        assertTrue(DSetConverter.isPhone(telPerson));
    }

    /**
     * test the isPhone method for the case of a TelUrl email url
     * @throws UnsupportedEncodingException Should not happen. UTF-8 is supported
     */
    @Test
    public void testIsPhoneCaseTelUrlMail() throws UnsupportedEncodingException {
        TelPerson telPerson = new TelPerson();
        telPerson.setValue(URI.create("mailto:" + URLEncoder.encode("abc@gmail.com", "UTF-8")));
        assertFalse(DSetConverter.isPhone(telPerson));
    }
    
    /**
     * test the isPhone method for the case of a TelUrl email url
     * @throws UnsupportedEncodingException Should not happen. UTF-8 is supported
     */
    @Test
    public void testReplacePhones() throws UnsupportedEncodingException {
        DSet<Tel> dSet = new DSet<Tel>();
        Set<Tel> items = new HashSet<Tel>();
        dSet.setItem(items);
        TelPhone telPhone = new TelPhone();
        telPhone.setValue(URI.create("tel:" + URLEncoder.encode("111-111-1111", "UTF-8")));
        items.add(telPhone);
        TelPerson telPerson1 = new TelPerson();
        telPerson1.setValue(URI.create("tel:" + URLEncoder.encode("222-222-2222", "UTF-8")));
        items.add(telPerson1);
        TelPerson telPerson2 = new TelPerson();
        telPerson2.setValue(URI.create("mailto:" + URLEncoder.encode("abc@gmail.com", "UTF-8")));
        items.add(telPerson2);
        List<String> phones = Arrays.asList(new String[]{"333-333-3333"});
        DSet<Tel> result = DSetConverter.replacePhones(dSet, phones);
        assertEquals("Wrong DSet returned", result, dSet);
        assertEquals("Wrong DSet size", 2, result.getItem().size());
        Set<String> urls = new HashSet<String>();
        for (Tel tel : result.getItem()) {
            urls.add(URLDecoder.decode(tel.getValue().toString(), "UTF-8"));
        }
        assertTrue(urls.contains("mailto:abc@gmail.com"));
        assertTrue(urls.contains("tel:333-333-3333"));
    }
}
