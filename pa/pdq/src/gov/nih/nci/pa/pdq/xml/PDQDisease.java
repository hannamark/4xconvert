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
*/
package gov.nih.nci.pa.pdq.xml;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.pdq.PDQConstants;
import gov.nih.nci.pa.pdq.PDQException;
import gov.nih.nci.pa.pdq.dml.DiseaseScript;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Hugh Reinhart
 * @since 7/13/2009
 */
public class PDQDisease extends AbstractPDQProcessor {
    private static final Logger LOG = Logger.getLogger(PDQDisease.class);


    /**
     * {@inheritDoc}
     */
    @Override
    public void process(Document doc, Rule rule, String user) throws PDQException {
        this.doc = doc;
        this.rule = rule;
        this.user = user;
        if (rule.equals(Rule.RULE1)) {
            rule1or4();
        }
        if (rule.equals(Rule.RULE4)) {
            rule1or4();
        }
    }

    private void rule1or4() throws PDQException {
        Disease d = new Disease();
        List<DiseaseAltername> danList = new ArrayList<DiseaseAltername>();

        Node node = doc.getDocumentElement();

        // set disease code (PDQ ID)
        NamedNodeMap attributes = node.getAttributes();
        for (int x = 0; x < attributes.getLength(); x++) {
            Node attribute = attributes.item(x);
            if(Rule.ATTR_NAME_ID.equals(attribute.getNodeName())) {
                d.setDiseaseCode(attribute.getNodeValue());
            }
        }

        NodeList children = node.getChildNodes();

        // set preferred name
        for (int x = 0; x < children.getLength(); x++) {
            Node child = children.item(x);

            if (child.getNodeName().equals(Rule.NODE_NAME_PREFERRED_NAME)) {
                d.setPreferredName(child.getTextContent());
            }
        }
        for (int x = 0; x < children.getLength(); x++) {
            Node child = children.item(x);

            // set menu display name
            if (child.getNodeName().equals(Rule.NODE_NAME_MENU_INFO)) {
                NodeList ddd = child.getChildNodes();
                for (int y = 0; y < ddd.getLength(); y++) {
                    Node eee = ddd.item(y);
                    if (eee.getNodeName().equals(Rule.NODE_NAME_MENU_ITEM)) {
                        NodeList fff = eee.getChildNodes();
                        for (int z = 0; z < fff.getLength(); z++) {
                            Node ggg = fff.item(z);
                            if (ggg.getNodeName().equals(Rule.NODE_NAME_MENU_DISPLAY_NAME)) {
                                d.setMenuDisplayName(ggg.getTextContent());
//                                LOG.info("Found menu DisplayName.");
                            }
                        }
                    }
                }
                if(PAUtil.isEmpty(d.getMenuDisplayName())) {
                    d.setMenuDisplayName(d.getPreferredName());
//                    LOG.info("Had to use preferred name for menu display name.");
                }
            }

            // find parent association
            if (child.getNodeName().equals(Rule.NODE_NAME_TERM_RELATIONSHIP)) {
                String parentCode = null;
                String childCode = d.getDiseaseCode();
                String type = null;
                NodeList pars = child.getChildNodes();
                for (int y = 0; y < pars.getLength(); y++) {
                    Node aaa = pars.item(y);
                    if (aaa.getNodeName().equals(Rule.NODE_NAME_PARENT_TERM)) {
                        NodeList bbb = aaa.getChildNodes();
                        for (int z = 0; z < bbb.getLength(); z++) {
                            Node ccc = bbb.item(z);
                            if(ccc.getNodeName().equals(Rule.NODE_NAME_PARENT_TYPE)) {
                                type = ccc.getTextContent();
                            }
                            if(ccc.getNodeName().equals(Rule.NODE_NAME_PARENT_TERM_NAME)) {
                                NamedNodeMap attrs = ccc.getAttributes();
                                for (int xx = 0; xx < attrs.getLength(); xx++) {
                                    Node attr = attrs.item(xx);
                                    if(Rule.ATTR_NAME_REF.equals(attr.getNodeName())) {
                                        parentCode = attr.getNodeValue();
                                    }
                                }
                            }
                        }
                    }
                }
                if(PAUtil.isNotEmpty(parentCode) && PAUtil.isNotEmpty(childCode)) {
                    DiseaseScript.get().addParent(childCode, parentCode, type);
                }
            }
            // find alternate names
            if (child.getNodeName().equals(Rule.NODE_NAME_OTHER_NAME)) {
                NodeList others = child.getChildNodes();
                for (int y = 0; y < others.getLength(); y++) {
                    Node other = others.item(y);
                    if(other.getNodeName().equals(Rule.NODE_NAME_OTHER_TERM_NAME)) {
                        DiseaseAltername dan = new DiseaseAltername();
                        dan.setAlternateName(other.getTextContent());
                        danList.add(dan);
                    }
                }
            }
        }
        if(PAUtil.isEmpty(d.getMenuDisplayName())) {
            d.setMenuDisplayName(PDQConstants.NOT_MENU);
        }
        if (PAUtil.isEmpty(d.getPreferredName())) {
            LOG.error("Error determining name from: ");
            XMLFileParser.getParser().writeDocumentToOutput(doc.getDocumentElement(), 0);
            System.exit(0);
        }
        DiseaseScript.get().add(d, danList, user);
//        XMLFileParser.getParser().writeDocumentToOutput(doc.getDocumentElement(), 0);
    }
}
