package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;

/**
 *
 * @author mshestopalov
 *
 */
public final class HealthCareProviderTransformer
    extends AbstractTransformer<HealthCareProvider, HealthCareProviderDTO>
    implements Transformer<HealthCareProvider, HealthCareProviderDTO> {
    /**
     * Public singleton.
     */
    public static final HealthCareProviderTransformer  INSTANCE = new HealthCareProviderTransformer();

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderDTO toDto(HealthCareProvider input)
                 throws DtoTransformException {
            if (input == null) {
                return null;
            }
            HealthCareProviderDTO d = new HealthCareProviderDTO();
            d.setCertificateLicenseText(STTransformer.INSTANCE.toDto(input.getCertificateLicenseText()));
            d.setPostalAddress(DSETADTransformer.INSTANCE.toDto(input.getPostalAddress()));
            d.setScoperIdentifier(IITransformer.INSTANCE.toDto(input.getScoperIdentifier()));
            d.setTelecomAddress(DSETTELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
            d.setIdentifier(DSETIITransformer.INSTANCE.toDto(input.getIdentifier()));
            d.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
            d.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
          return d;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public HealthCareProvider toXml(HealthCareProviderDTO input)
                 throws DtoTransformException {
              if (input == null) {
                 return null;
              }
              HealthCareProvider d = new HealthCareProvider();
              d.setIdentifier(DSETIITransformer.INSTANCE.toXml(input.getIdentifier()));
              d.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
              d.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
              d.setCertificateLicenseText(STTransformer.INSTANCE.toXml(input.getCertificateLicenseText()));
              d.setPostalAddress(DSETADTransformer.INSTANCE.toXml(input.getPostalAddress()));
              d.setScoperIdentifier(IITransformer.INSTANCE.toXml(input.getScoperIdentifier()));
              d.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
              return d;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProvider[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new HealthCareProvider[arg0];
    }
}
