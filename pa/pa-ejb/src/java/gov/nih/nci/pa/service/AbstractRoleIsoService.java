package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.convert.AbstractConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Base class for services which implement the getByStudyProtocol method with functional roles.
 * @author Naveen Amiruddin
 * @since 09/30/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * @param <DTO> data transfer object
 * @param <BO> domain object
 * @param <CONVERTER> converter class
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public abstract class
    AbstractRoleIsoService<DTO extends StudyDTO, BO extends AbstractEntity,
    CONVERTER extends AbstractConverter<DTO, BO>>
    extends AbstractStudyIsoService<DTO, BO, CONVERTER>
    implements RolePaService<DTO> {

    /**
     * Get list of StudyParticipations for a given protocol having
     * functional codes from a list.
     * @param studyProtocolIi id of protocol
     * @param dtos List containing desired functional codes
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity", "unchecked" })
    public List<DTO> getByStudyProtocol(
            Ii studyProtocolIi , List<DTO> dtos) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("Ii is null ");
        }
        getLogger().info("Entering getByStudyProtocol(Ii, List<DTO>).  ");
        StringBuffer criteria = new StringBuffer();
        Session session = null;
        List<BO> queryList = new ArrayList<BO>();
        try {
            session = HibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer("select spart from ");
                               hql.append(getTypeArgument().getName());
                               hql.append(" spart join spart.studyProtocol spro where spro.id = :studyProtocolId");
                               boolean first = true;
                               boolean appended = false;
            for (DTO crit : dtos) {
            if (first && crit != null) {
                    hql.append(" and ");
                    first = false;
                } else {
                    criteria.append("or ");
                }

            if (getTypeArgument().getName().equals("gov.nih.nci.pa.domain.StudyContact")) {
                    StudyContactDTO spcDTO = (StudyContactDTO) crit;
                    criteria.append("spart.roleCode = '"
                        + StudyContactRoleCode.getByCode(spcDTO.getRoleCode().getCode()) + "' ");
                    appended = true;
                }
                if (getTypeArgument().getName().equals("gov.nih.nci.pa.domain.StudyParticipationContact")) {

                    StudyParticipationContactDTO spcDTO = (StudyParticipationContactDTO) crit;
                    criteria.append("spart.roleCode = '"
                        + StudyParticipationContactRoleCode.getByCode(spcDTO.getRoleCode().getCode()) + "' ");
                    appended = true;
                }
                if (getTypeArgument().getName().equals("gov.nih.nci.pa.domain.StudyParticipation")) {

                    StudyParticipationDTO spcDTO = (StudyParticipationDTO) crit;
                    criteria.append("spart.functionalCode = '"
                            + StudyParticipationFunctionalCode.getByCode(spcDTO.getFunctionalCode().getCode()) + "' ");
                    appended = true;
                }
            }
            if (appended) {
            hql.append('(');
            hql.append(criteria);
            hql.append(')');
            }
            hql.append(" order by spart.id ");
            getLogger().info(" query  = " + hql);

            Query query = session.createQuery(hql.toString());
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            throw new PAException(" Hibernate exception while retrieving "
                     + getTypeArgument().getName() + " for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<DTO> resultList = new ArrayList<DTO>();
        for (BO sc : queryList) {
            resultList.add(convertFromDomainToDto(sc));
        }
        getLogger().info("Leaving getByStudyProtocol() for (" + criteria + ").  ");
        getLogger().info("Returning " + resultList.size() + " object(s).  ");
        return resultList;
    }

    /**
     * Get list of StudyParticipations for a given a given functional code.
     * @param studyProtocolIi id of protocol
     * @param dto Object with the functional code criteria
     * @return list dtos
     * @throws PAException on error
     */
    public List<DTO> getByStudyProtocol(
            Ii studyProtocolIi , DTO dto) throws PAException {
        List <DTO> spDtoList = new ArrayList<DTO>();
        spDtoList.add(dto);
        return getByStudyProtocol(studyProtocolIi, spDtoList);
    }
}
