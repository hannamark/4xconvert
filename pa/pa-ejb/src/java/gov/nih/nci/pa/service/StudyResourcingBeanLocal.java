/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyResourcingConverter;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyResourcingBeanLocal extends
        AbstractStudyIsoService<StudyResourcingDTO, StudyResourcing, StudyResourcingConverter> implements
        StudyResourcingServiceLocal {

    private static final Logger LOG = Logger.getLogger(StudyResourcingBeanLocal.class);
    private static StudyResourcingConverter src = new StudyResourcingConverter();
    private SessionContext ejbContext;

    /**
     * Set the invocation context.
     * @param ctx EJB context
     */
    @Override
    @Resource
    public void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }

  /**
   * @param studyProtocolIi Ii
   * @return StudyProtocolDTO
   * @throws PAException PAException
   */
   @SuppressWarnings("unchecked")
   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
   public StudyResourcingDTO getSummary4ReportedResourcing(Ii studyProtocolIi) throws PAException {

    if (PAUtil.isIiNull(studyProtocolIi)) {
        throw new PAException("studyProtocol Identifer should not be null");
    }
    StudyResourcingDTO studyResourcingDTO = null;
    Session session = null;
    StudyResourcing studyResourcing = null;
    List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
    session = HibernateUtil.getCurrentSession();

    Query query = null;

    // step 1: form the hql
    String hql = " select sr from StudyResourcing sr join sr.studyProtocol sp "
        + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi)
        + " and sr.summary4ReportedResourceIndicator =  '" + Boolean.TRUE + "'";

    LOG.info(" query studyResourcing = " + hql);

    // step 2: construct query object
    query = session.createQuery(hql);
    queryList = query.list();

    if (queryList.size() > 1) {
        LOG.error(" Summary 4 Reported Sourcing should not be more than 1 record ");
        throw new PAException(" Summary 4 Reported Sourcing should not be more than 1 record ");

    }

    if (!queryList.isEmpty()) {
        studyResourcing = queryList.get(0);
        studyResourcingDTO = src.convertFromDomainToDto(studyResourcing);

    }
    return studyResourcingDTO;
 }

 /**
  *
  * @param studyResourcingDTO StudyResourcingDTO
  * @return StudyProtocolDTO
  * @throws PAException PAException
  */
  public StudyResourcingDTO updateStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {

    if (studyResourcingDTO == null) {
        throw new PAException("studyResourcingDTO should not be null");
    }
    enforceValidation(studyResourcingDTO);

    Session session = null;
    StudyResourcing studyResourcing = null;
    StudyResourcingDTO studyResourcingRetDTO = null;
    List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
    session = HibernateUtil.getCurrentSession();
    Query query = null;

    // step 1: form the hql
    String hql = " select sr "
                   + " from StudyResourcing sr "
                   + " where sr.id = " + IiConverter.convertToLong(studyResourcingDTO.getIdentifier());
   // step 2: construct query object
   query = session.createQuery(hql);
   queryList = query.list();
   studyResourcing = queryList.get(0);
   // set the values from paramter
   if (studyResourcingDTO.getTypeCode() != null) {
       studyResourcing.setTypeCode(SummaryFourFundingCategoryCode.getByCode(
           studyResourcingDTO.getTypeCode().getCode()));
   }
   studyResourcing.setOrganizationIdentifier(IiConverter.convertToString(
           studyResourcingDTO.getOrganizationIdentifier()));
   studyResourcing.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
   studyResourcing.setUserLastUpdated(CSMUserService.getInstance().lookupUser(ejbContext));
   studyResourcing.setFundingMechanismCode(CdConverter.convertCdToString(
            studyResourcingDTO.getFundingMechanismCode()));
   studyResourcing.setNciDivisionProgramCode(NciDivisionProgramCode.getByCode(
             studyResourcingDTO.getNciDivisionProgramCode().getCode()));
   studyResourcing.setNihInstituteCode(studyResourcingDTO.getNihInstitutionCode().getCode());
   studyResourcing.setSerialNumber(StConverter.convertToString(studyResourcingDTO.getSerialNumber()));
   session.update(studyResourcing);
   studyResourcingRetDTO = src.convertFromDomainToDto(studyResourcing);
   return studyResourcingRetDTO;
 }

 /**
  *
  * @param studyResourcingDTO StudyResourcingDTO
  * @return StudyProtocolDTO
  * @throws PAException PAException
  */
  public StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {

    if (studyResourcingDTO == null) {
        throw new PAException("studyResourcingDTO should not be null");
    }
    enforceValidation(studyResourcingDTO);

    Session session = null;
    StudyResourcing studyResourcing = src.convertFromDtoToDomain(studyResourcingDTO);
    java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
    studyResourcing.setDateLastCreated(now);
    studyResourcing.setUserLastCreated(CSMUserService.getInstance().lookupUser(ejbContext));
    // create Protocol Obj
    StudyProtocol studyProtocol = new StudyProtocol();
    studyProtocol.setId(IiConverter.convertToLong(studyResourcingDTO.getStudyProtocolIdentifier()));
    studyResourcing.setStudyProtocol(studyProtocol);
    studyResourcing.setActiveIndicator(true);
    session = HibernateUtil.getCurrentSession();
    session.save(studyResourcing);
    session.flush();
    return src.convertFromDomainToDto(studyResourcing);
  }

 /**
  * @param studyProtocolIi Ii
  * @return StudyResourcingDTO
  * @throws PAException PAException
  */
  @SuppressWarnings("unchecked")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<StudyResourcingDTO> getStudyResourcingByStudyProtocol(Ii studyProtocolIi)
  throws PAException {
    if (PAUtil.isIiNull(studyProtocolIi)) {
        LOG.error(" studyProtocol Identifer should not be null ");
        throw new PAException(" studyProtocol Identifer should not be null ");
    }
    Session session = null;
    List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
    session = HibernateUtil.getCurrentSession();

    Query query = null;

    // step 1: form the hql
    String hql = " select sr "
        + " from StudyResourcing sr "
        + " join sr.studyProtocol sp "
        + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi)
        + " and sr.summary4ReportedResourceIndicator =  '" + Boolean.FALSE + "'"
        + " and sr.activeIndicator =  '" + Boolean.TRUE + "'";

    LOG.info(" query getstudyResourceByStudyProtocol = " + hql);

    // step 2: construct query object
    query = session.createQuery(hql);
    queryList = query.list();

    ArrayList<StudyResourcingDTO> resultList = new ArrayList<StudyResourcingDTO>();
    for (StudyResourcing bo : queryList) {
        resultList.add(src.convertFromDomainToDto(bo));
    }
    return resultList;
  }
  /**
   * @param studyResourceIi Ii
   * @return StudyResourcingDTO
   * @throws PAException PAException
   */
   @SuppressWarnings("unchecked")
   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
   public StudyResourcingDTO getStudyResourcingById(Ii studyResourceIi)
   throws PAException {
     return get(studyResourceIi);
   }
  /**
   *
   * @param studyResourcingDTO StudyResourcingDTO
   * @return StudyResourcingDTO
   * @throws PAException PAException
   */
   @SuppressWarnings("unchecked")
   public Boolean deleteStudyResourcingById(StudyResourcingDTO studyResourcingDTO) throws PAException {
     if (studyResourcingDTO == null) {
        LOG.error(" studyResourcingDTO should not be null ");
        throw new PAException(" studyResourcingDTO should not be null ");
     }
     Boolean result = false;
     Session session = null;
     StudyResourcing studyResourcing = null;
     List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
     session = HibernateUtil.getCurrentSession();

    Query query = null;

    // step 1: form the hql
    String hql = " select sr "
        + " from StudyResourcing sr "
        + " where sr.id = " + IiConverter.convertToLong(studyResourcingDTO.getIdentifier());
    // step 2: construct query object
    query = session.createQuery(hql);
    queryList = query.list();
    studyResourcing = queryList.get(0);
    // set the values from paramter
    studyResourcing.setActiveIndicator(false);
    studyResourcing.setInactiveCommentText(StConverter.convertToString(
            studyResourcingDTO.getInactiveCommentText()));
    studyResourcing.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
    studyResourcing.setUserLastUpdated(CSMUserService.getInstance().lookupUser(ejbContext));

    session.update(studyResourcing);
    result = true;
    return result;
 }


    private boolean enforceNoDuplicate(StudyResourcingDTO dto) throws PAException {
      String newSerialNumber = dto.getSerialNumber().getValue();
      String newFundingMech = dto.getFundingMechanismCode().getCode();
      String newNciDivCode = dto.getNciDivisionProgramCode().getCode();
      String newNihInstCode = dto.getNihInstitutionCode().getCode();
      List<StudyResourcingDTO> spList = getStudyResourcingByStudyProtocol(dto.getStudyProtocolIdentifier());
      boolean duplicateExists = false;
      for (StudyResourcingDTO sp : spList) {
          boolean sameSerialNumber = newSerialNumber.equals(sp.getSerialNumber().getValue());
          boolean sameFundingMech = newFundingMech.equals(sp.getFundingMechanismCode().getCode());
          boolean sameNciDivCode = newNciDivCode.equals(sp.getNciDivisionProgramCode().getCode());
          boolean sameNihInstCode = newNihInstCode.equals(sp.getNihInstitutionCode().getCode());

          if (sameSerialNumber && sameFundingMech && sameNciDivCode && sameNihInstCode) {
              if (dto.getIdentifier() == null
                  || (!dto.getIdentifier().getExtension().equals(sp.getIdentifier().getExtension()))) {
                  duplicateExists = true;
                  break;
              }
          }
      }
      return duplicateExists;
  }

    private boolean isNumeric(String number) {
        boolean isValid = false;
        // Initialize reg ex for numeric data.
        String expression = "^[0-9]*[0-9]+$";
        CharSequence inputStr = number;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * @param studyResourcingDTO dto
     * @return
     * @throws PAException e
     */
    @Override
    public void validate(StudyResourcingDTO studyResourcingDTO) throws PAException {
        enforceValidation(studyResourcingDTO);
    }

    /**
     * @param dto StudyResourcingDTO to create
     * @return the created StudyResourcingDTO
     * @throws PAException exception.
     */
    @Override
    public StudyResourcingDTO create(StudyResourcingDTO dto) throws PAException {
        if (PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())) {
            throw new PAException("The summary4ReportedResourceIndicator is not set");
        }
        if (!PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())
                && BlConverter.convertToBoolean(dto.getSummary4ReportedResourceIndicator()).equals(Boolean.FALSE)) {
            return createStudyResourcing(dto);
        }
        return super.create(dto);
    }

    /**
     * @param dto StudyResourcingDTO to update
     * @return the updated StudyResourcingDTO
     * @throws PAException exception.
     */
    @Override
    public StudyResourcingDTO update(StudyResourcingDTO dto) throws PAException {
        if (PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())) {
            throw new PAException("The summary4ReportedResourceIndicator is not set");
        }
        if (!PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())
                && BlConverter.convertToBoolean(dto.getSummary4ReportedResourceIndicator()).equals(Boolean.FALSE)) {
            return updateStudyResourcing(dto);
        }
        return super.update(dto);

    }

  private void enforceValidation(StudyResourcingDTO studyResourcingDTO) throws PAException {
   StringBuffer errorBuffer =  new StringBuffer();
   final int serialNumMin = 5;
   final int serialNumMax = 6;
   if (!PAUtil.isBlNull(studyResourcingDTO.getSummary4ReportedResourceIndicator())
       && BlConverter.convertToBoolean(studyResourcingDTO.getSummary4ReportedResourceIndicator())
               .equals(Boolean.FALSE)) {
          //check if nih institute code exists
          if (!PAUtil.isCdNull(studyResourcingDTO.getNihInstitutionCode())) {
            boolean nihExists =
                PADomainUtils.checkIfValueExists(studyResourcingDTO.getNihInstitutionCode().getCode(),
                                  "NIH_INSTITUTE", "nih_institute_code");
              if (!nihExists) {
                 errorBuffer.append("Error while checking for value ")
                         .append(studyResourcingDTO.getNihInstitutionCode().getCode())
                         .append(" from table NIH_INSTITUTE\n");
              }
          }
          if (!PAUtil.isCdNull(studyResourcingDTO.getFundingMechanismCode())) {
             //check if Funding mechanism code exists
             boolean fmExists = PADomainUtils.checkIfValueExists(studyResourcingDTO.getFundingMechanismCode().getCode(),
                 "FUNDING_MECHANISM", "funding_mechanism_code");

             if (!fmExists) {
                errorBuffer.append("Error while checking for value ")
                           .append(studyResourcingDTO.getFundingMechanismCode().getCode())
                           .append(" from table FUNDING_MECHANISM\n");
             }
          }
          if (studyResourcingDTO.getSerialNumber() != null
                && studyResourcingDTO.getSerialNumber().getValue() != null) {
              String snValue = studyResourcingDTO.getSerialNumber().getValue().toString();
              if (snValue.length() < serialNumMin || snValue.length() > serialNumMax) {
                errorBuffer.append("Serial number can be numeric with 5 or 6 digits\n");
              }
              if (!isNumeric(snValue)) {
                errorBuffer.append("Serial number should have numbers from [0-9]\n");
              }
         }
         if (PAUtil.isIiNotNull(studyResourcingDTO.getStudyProtocolIdentifier())) {
             //this means it is original submission thats why not having Ii
             boolean dupExists = enforceNoDuplicate(studyResourcingDTO);
             if (dupExists) {
                 errorBuffer.append("Duplicate grants are not allowed\n");
             }
         }
   }
   if (errorBuffer.length() > 0) {
       throw new PADuplicateException("Validation Exception " + errorBuffer.toString());
   }
  }
 }
