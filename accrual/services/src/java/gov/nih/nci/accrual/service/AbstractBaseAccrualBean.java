package gov.nih.nci.accrual.service;

import gov.nih.nci.accrual.convert.AbstractConverter;
import gov.nih.nci.accrual.convert.Converters;
import gov.nih.nci.accrual.util.AccrualHibernateUtil;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.lang.reflect.ParameterizedType;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Aug 14, 2009
 *
 * @param <DTO> iso dto
 * @param <BO> domain object
 * @param <CONVERTER> converter
 */
public abstract class AbstractBaseAccrualBean<DTO extends BaseDTO, BO extends AbstractEntity,
        CONVERTER extends AbstractConverter<DTO, BO>> implements BaseAccrualService<DTO> {

    private static final String UNCHECKED = "unchecked";
    private final Class<BO> typeArgument;
    private final Class<CONVERTER> converterArgument;
    @SuppressWarnings("PMD.LoggerIsNotStaticFinal")
    private final Logger logger;
    private SessionContext ejbContext;

    @Resource
    void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }
    /**
     * default constructor.
     */
    @SuppressWarnings(UNCHECKED)
    public AbstractBaseAccrualBean() {
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
     * @param bo domain object
     * @return DTO iso transfer object
     * @throws DataFormatException exception
     */
    protected DTO convertFromDomainToDto(BO bo) throws DataFormatException {
        return Converters.get(getConverterArgument()).convertFromDomainToDto(bo);
    }
    /**
     * @param dto iso transfer object
     * @return DTO iso transfer object
     * @throws DataFormatException exception
     */
    protected BO convertFromDtoToDomain(DTO dto) throws DataFormatException {
        return Converters.get(getConverterArgument()).convertFromDtoToDomain(dto);
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
     * @throws RemoteException exception
     */
    @SuppressWarnings(UNCHECKED)
    public DTO get(Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            throw new RemoteException("Called get() with Ii == null.");
        }
        BO bo = null;
        DTO resultDto = null;
        Session session = null;
        try {
            session = AccrualHibernateUtil.getCurrentSession();
            bo = (BO) session.get(getTypeArgument(), IiConverter.convertToLong(ii));
            if (bo == null) {
                logger.error("Object not found using get() for id = "
                        + IiConverter.convertToString(ii) + ".");
                return resultDto;
            }
        } catch (HibernateException hbe) {
            throw new RemoteException("Hibernate exception in get().", hbe);
        }
        try {
            resultDto = convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in get().", e);
        }
        return resultDto;
    }

    /**
     * @param ii index of object
     * @throws RemoteException exception
     */
    @SuppressWarnings(UNCHECKED)
    public void delete(Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            throw new RemoteException("Called delete() with Ii == null.");
        }
        getLogger().info("Entering delete().  ");
        Session session = null;
        try {
            session = AccrualHibernateUtil.getCurrentSession();
            //session.beginTransaction();
            BO bo = (BO) session.get(getTypeArgument(), IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            throw new RemoteException("Hibernate exception while deleting ii = "
                    + IiConverter.convertToString(ii) + ".", hbe);
        }
        getLogger().info("Leaving delete().  ");
    }

    /**
     * @param dto arm to create
     * @return the created planned activity
     * @throws RemoteException exception.
     */
    public DTO create(DTO dto) throws RemoteException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.");
        }
        return createOrUpdate(dto);
    }

    /**
     * @param dto arm to update
     * @return the updated planned activity
     * @throws RemoteException exception.
     */
    public DTO update(DTO dto) throws RemoteException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to create new.");
        }
        return createOrUpdate(dto);
    }

    @SuppressWarnings(UNCHECKED)
    private DTO createOrUpdate(DTO dto) throws RemoteException {
        BO bo = null;
        try {
            bo = convertFromDtoToDomain(dto);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in createOrUpdate().", e);
        }
        DTO resultDto = null;
        Session session = null;
        try {
            session = AccrualHibernateUtil.getCurrentSession();
            bo.setUserLastUpdated(ejbContext != null ? ejbContext.getCallerPrincipal().getName() : "not logged");
            bo.setDateLastUpdated(new Date());
            if (PAUtil.isIiNull(dto.getIdentifier())) {
                bo.setUserLastCreated(bo.getUserLastUpdated());
                bo.setDateLastCreated(bo.getDateLastUpdated());
            } else {
                BO oldBo = (BO) session.get(getTypeArgument(), IiConverter.convertToLong(dto.getIdentifier()));
                bo.setDateLastCreated(oldBo.getDateLastCreated());
                bo.setUserLastCreated(oldBo.getUserLastCreated());
                session.evict(oldBo);
            }
            bo = (BO) session.merge(bo);
            session.flush();
        } catch (HibernateException hbe) {
            throw new RemoteException("Hibernate exception in createOrUpdate().", hbe);
        }
        try {
            resultDto = convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in createOrUpdate().", e);
        }
        return resultDto;
    }
}
