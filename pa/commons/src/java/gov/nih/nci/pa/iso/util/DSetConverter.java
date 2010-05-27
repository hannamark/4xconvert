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

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 *
 * @author NAmiruddin, HArsha
 *
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })

public class DSetConverter {

    /** The base ii root value. */
    public static final String BASE_ROOT = "2.16.840.1.113883.3.26.4";

    /**
     * @param dsetList list of DSets
     * @param type denoting email, telephone, fax, url, etc.,
     * @param list of Tel objects
     * @return dSet collection
     */
    public static DSet<Tel> convertListToDSet(List<String> list, String type, DSet<Tel> dsetList) {
        Set<Tel> telSet = new HashSet<Tel>();
        DSet<Tel> dSet = null;
        if (dsetList == null) {
            dSet = new DSet<Tel>();
        } else {
            dSet = dsetList;
            if (dsetList.getItem() != null && dsetList.getItem().size() > 0) {
                Iterator<Tel> val = dsetList.getItem().iterator();
                while (val.hasNext()) {
                    telSet.add(val.next());
                }
            }
        }
        if (list == null || list.isEmpty()) {
            return dSet;
        }
        if ("PHONE".equalsIgnoreCase(type)) {
            TelPhone t = null;
            for (String phone : list) {
                if (phone == null) {
                    continue;
                }
                t = new TelPhone();
                try {
                    t.setValue(URI.create("tel:" + URLEncoder.encode(phone, "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    continue;
                }
                telSet.add(t);
            }
        } else if ("EMAIL".equalsIgnoreCase(type)) {
            TelEmail t = null;
            for (String email : list) {
                t = new TelEmail();
                if (email != null) {
                    email = email.trim();
                }
                t.setValue(URI.create("mailto:" + email));
                telSet.add(t);
            }
        }
        dSet.setItem(telSet);
        return dSet;
    }

    /**
     * @param type to be returned
     * @param dSet set of iso phones
     * @return phones
     */
    public static List<String> convertDSetToList(DSet<Tel> dSet, String type) {
        List<String> retList = new ArrayList<String>();
        if (dSet == null || dSet.getItem() == null) {
            return retList;
        }
        if (type.equalsIgnoreCase("PHONE")) {
            for (Tel t : dSet.getItem()) {
                if (t.getNullFlavor() != null) {
                    continue;
                }
                if (t instanceof TelPhone) {
                    try {
                        retList.add((URLDecoder.decode(t.getValue().getSchemeSpecificPart(), "UTF-8")));
                    } catch (UnsupportedEncodingException e) {
                        continue;
                    }
                } else {
                    String url = t.getValue().toString();
                    if (url != null && url.startsWith("tel")) {
                        try {
                            retList.add((URLDecoder.decode(t.getValue().getSchemeSpecificPart(), "UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            continue;
                        }
                    }
                }
            }
        }
        if (type.equalsIgnoreCase("EMAIL")) {
            for (Tel t : dSet.getItem()) {
                if (t.getNullFlavor() != null) {
                    continue;
                }
                if (t instanceof TelEmail) {
                    retList.add((t.getValue().getSchemeSpecificPart()));
                } else {
                    String url = t.getValue().toString();
                    if (url != null && url.startsWith("mailto")) {
                        retList.add((t.getValue().getSchemeSpecificPart()));
                    }
                }
            }
        }
        return retList;
    }

    /**
     *
     * @param cds cds
     * @return dSet
     */
    public static DSet<Cd> convertCdListToDSet(List<Cd> cds) {
        DSet<Cd> dSet = null;
        if (cds == null || cds.isEmpty()) {
            return dSet;
        }
        dSet = new DSet<Cd>();
        Set<Cd> cdSet = new HashSet<Cd>();
        cdSet.addAll(cds);
        dSet.setItem(cdSet);
        return dSet;
    }

    /**
     *
     * @param dset dset
     * @return cds
     */
    public static List<Cd> convertDsetToCdList(DSet<Cd> dset) {
        List<Cd> cds = null;
        if (dset == null || dset.getItem() == null || dset.getItem().isEmpty()) {
            return cds;
        }
        cds = new ArrayList<Cd>();
        Set<Cd> set = dset.getItem();
        cds.addAll(set);
        return cds;
    }

    /**
     * returns the first found from the DSET.
     * @param type to be returned
     * @param dSet set of iso phones
     * @return first element of phone/email
     */
    public static String getFirstElement(DSet<Tel> dSet, String type) {
        List<String> tels = convertDSetToList(dSet, type);
        String tel = null;
        if (tels != null && !tels.isEmpty()) {
            tel =  tels.get(0);
        }
        return tel;
    }


    /**
     * Extract the internal identifier from the set of identifiers.
     * @param identifier set of identifiers
     * @return internal identifier
     */
    public static Ii convertToIi(DSet<Ii> identifier) {
        if (identifier != null) {
            Set<Ii> iis = identifier.getItem();
            for (Ii ii : iis) {
                // Since PO only assigns one ID, our identifier will be the only ISS with our root
                if (IdentifierReliability.ISS == ii.getReliability() && ii.getRoot().startsWith(BASE_ROOT)) {
                    return ii;
                }
            }
        }
        return null;
    }

    /**
     * Extract the internal identifier from the set of identifiers.
     * @param identifier set of identifiers
     * @return internal identifier
     */
    public static DSet<Ii> convertIiToDset(Ii identifier) {

        DSet<Ii> dSet = null;
        if (identifier != null) {
            dSet = new DSet<Ii>();
            Set<Ii> iiSet = new HashSet<Ii>();
            iiSet.add(identifier);
            dSet.setItem(iiSet);
        }
        return dSet;
    }


    /**
     * Convert ii set to dset.
     * 
     * @param identifierList the identifier list
     * 
     * @return the d set< ii>
     */
    public static DSet<Ii> convertIiSetToDset(Set<Ii> identifierList) {

        DSet<Ii> dSet = null;
        if (identifierList != null) {
            dSet = new DSet<Ii>();
            dSet.setItem(identifierList);        
              
        }
        return dSet;
    }
    
    /**
     * Convert dset to ii set.
     * 
     * @param identifierList the identifier list
     * 
     * @return the set< ii>
     */
    public static Set<Ii> convertDsetToIiSet(DSet<Ii> identifierList) {

        Set<Ii> iiset = null;
        if (identifierList != null && identifierList.getItem() != null) {
            iiset = new HashSet<Ii>();
            iiset.addAll(identifierList.getItem());               
        }
        return iiset;
    }
}
