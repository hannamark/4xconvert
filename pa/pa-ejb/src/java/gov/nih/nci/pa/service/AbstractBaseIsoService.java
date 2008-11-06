package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.iso.convert.AbstractConverter;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 09/30/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * @param <DTO> data transfer object
 * @param <BO> domain object
 * @param <CONVERTER> conveter class
 */
public abstract class AbstractBaseIsoService<DTO extends BaseDTO, BO extends AbstractEntity,
                                            CONVERTER extends AbstractConverter<DTO, BO>>
            implements BasePaService<DTO> {
    
    private static final String UNCHECKED = "unchecked";
    private final Class<BO> typeArgument;
    private final Class<CONVERTER> converterArgument;
    @SuppressWarnings("PMD.LoggerIsNotStaticFinal")
    private final Logger logger;

    /**
     * default constructor.
     */
    @SuppressWarnings(UNCHECKED)
    public AbstractBaseIsoService() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        typeArgument = (Class) parameterizedType.getActualTypeArguments()[1];
        converterArgument = (Class) parameterizedType.getActualTypeArguments()[2];
        logger = Logger.getLogger(typeArgument);
    }
    /**
     * @return log4j Logger
     */
    protected  Logger getLogger() {
        return logger;
    }
    
    /**
     * Get class of the implementation.
     *
     * @return the class
     */
    protected Class<BO> getTypeArgument() {
        return typeArgument;
    }
    /**
     * Get class of the implementation.
     *
     * @return the class
     */
    protected Class<CONVERTER> getConverterArgument() {
        return converterArgument;
    }
    /**
     * @param ii index of object
     * @return null
     * @throws PAException exception
     */
    @SuppressWarnings(UNCHECKED)
    public DTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        DTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            BO bo = (BO) session.get(getTypeArgument(), IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = "
                        + IiConverter.convertToString(ii) + ".  ");
            }
            resultDto = (DTO) Converters.get(getConverterArgument()).convertFromDomainToDto(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }
    
    /**
     * @param ii index of object
     * @throws PAException exception
     */
    @SuppressWarnings(UNCHECKED)
    public void delete(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        getLogger().info("Entering delete().  ");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            BO bo = (BO) session.get(getTypeArgument(), IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting ii = "
                + IiConverter.convertToString(ii) + ".  ", hbe);
        }
        getLogger().info("Leaving delete().  ");
    }

    @SuppressWarnings(UNCHECKED)
    private DTO createOrUpdate(DTO dto) throws PAException {
        if ((StConverter.convertToString(dto.getUserLastUpdated()) == null)
               || (StConverter.convertToString(dto.getUserLastUpdated()).trim().length() < 1)) {
            serviceError("All creates and updates must have a userLastUpdated.  ");
        }
        BO bo = null;
        DTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            bo = (BO) Converters.get(getConverterArgument()).convertFromDtoToDomain(dto);
            if (PAUtil.isIiNull(dto.getIdentifier())) {
                bo.setDateLastCreated(new Date());
                bo.setDateLastUpdated(bo.getDateLastCreated());
                bo.setUserLastCreated(StConverter.convertToString(dto.getUserLastUpdated()));
                bo.setUserLastUpdated(bo.getUserLastCreated());
            } else {
                BO oldBo = (BO) session.get(getTypeArgument(), IiConverter.convertToLong(dto.getIdentifier()));
                bo.setDateLastCreated(oldBo.getDateLastCreated());
                bo.setDateLastUpdated(new Date());
                bo.setUserLastCreated(oldBo.getUserLastCreated());
                bo.setUserLastUpdated(StConverter.convertToString(dto.getUserLastUpdated()));
            }
            session.merge(bo);
            session.flush();
            resultDto = (DTO) Converters.get(getConverterArgument()).convertFromDomainToDto(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in createOrUpdate().  ", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto arm to create
     * @return the created planned activity
     * @throws PAException exception.
     */
    public DTO create(DTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError("Update method should be used to modify existing.  ");
        }
       return createOrUpdate(dto);
    }

    /**
     * @param dto arm to update
     * @return the updated planned activity
     * @throws PAException exception.
     */
    public DTO update(DTO dto) throws PAException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError("Create method should be used to modify existing.  ");
        }
        return createOrUpdate(dto);
    }

    /**
     * @param errMsg error string
     * @throws PAException exception
     */
    protected void serviceError(String errMsg) throws PAException {
        getLogger().error(errMsg);
        throw new PAException(errMsg);
    }
    
    /**
     * @param errMsg error string
     * @param t throwable error
     * @throws PAException exception
     */
    protected void serviceError(String errMsg, Throwable t) throws PAException {
        getLogger().error(errMsg, t);
        throw new PAException(errMsg, t);
    }    
}
