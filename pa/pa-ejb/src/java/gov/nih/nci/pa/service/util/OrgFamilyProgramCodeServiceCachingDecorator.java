/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.OrgFamilyProgramCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.CacheUtils.Closure;

import java.util.List;

/**
 * Decorates {@link OrgFamilyProgramCodeService} and adds caching capability.
 * 
 * @author vinodh
 *
 */
public class OrgFamilyProgramCodeServiceCachingDecorator implements
        OrgFamilyProgramCodeService {

    private OrgFamilyProgramCodeService orgFamPrgCdSrvc;

    /**
     * @param orgFamPrgCdSrvc instance of OrgFamilyProgramCodeService
     */
    public OrgFamilyProgramCodeServiceCachingDecorator(
            OrgFamilyProgramCodeService orgFamPrgCdSrvc) {
        super();
        this.orgFamPrgCdSrvc = orgFamPrgCdSrvc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.pa.service.util.OrgFamilyProgramCodeService#
     * createOrgFamilyProgramCode(gov.nih.nci.pa.domain.OrgFamilyProgramCode)
     */
    @Override
    public OrgFamilyProgramCode createOrgFamilyProgramCode(
            OrgFamilyProgramCode orgFamProgCode) throws PAException {
        OrgFamilyProgramCode ofPrgCd = orgFamPrgCdSrvc
                .createOrgFamilyProgramCode(orgFamProgCode);
        CacheUtils.getOrgFamilyProgramCodesCache().remove(
                ofPrgCd.getFamilyPoId());
        return ofPrgCd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.pa.service.util.OrgFamilyProgramCodeService#
     * updateOrgFamilyProgramCode(gov.nih.nci.pa.domain.OrgFamilyProgramCode)
     */
    @Override
    public OrgFamilyProgramCode updateOrgFamilyProgramCode(
            OrgFamilyProgramCode orgFamProgCode) throws PAException {
        OrgFamilyProgramCode ofPrgCd = orgFamPrgCdSrvc
                .updateOrgFamilyProgramCode(orgFamProgCode);
        CacheUtils.getOrgFamilyProgramCodesCache().remove(
                ofPrgCd.getFamilyPoId());
        return ofPrgCd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.pa.service.util.OrgFamilyProgramCodeService#
     * getProgramCodesByFamilyPOId(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgFamilyProgramCode> getProgramCodesByFamilyPOId(
            final String famPoId) throws PAException {
        return (List<OrgFamilyProgramCode>) CacheUtils.getFromCacheOrBackend(
                CacheUtils.getOrgFamilyProgramCodesCache(), famPoId,
                new Closure() {
                    public Object execute() throws PAException {
                        return orgFamPrgCdSrvc
                                .getProgramCodesByFamilyPOId(famPoId);
                    }
                });
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.pa.service.util.OrgFamilyProgramCodeService#
     * getOrganizationFamilyProgramCodeById(java.lang.Long)
     */
    @Override
    public OrgFamilyProgramCode getOrganizationFamilyProgramCodeById(
            Long progCodeId) throws PAException {
        return orgFamPrgCdSrvc.getOrganizationFamilyProgramCodeById(progCodeId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.OrgFamilyProgramCodeService#search(gov.nih
     * .nci.pa.domain.OrgFamilyProgramCode)
     */
    @Override
    public List<OrgFamilyProgramCode> search(OrgFamilyProgramCode orgFamProgCode)
            throws PAException {
        return orgFamPrgCdSrvc.search(orgFamProgCode);
    }

}
