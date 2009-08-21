package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.AbstractTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;

/**
 * Transforms OversightCommittee instances.
 */
public class OversightCommitteeTransformer
    extends AbstractTransformer<OversightCommittee, OversightCommitteeDTO>
    implements Transformer<OversightCommittee, OversightCommitteeDTO> {
    /**
    * Public singleton.
    */
    public static final OversightCommitteeTransformer INSTANCE = new OversightCommitteeTransformer();

    /**
     * {@inheritDoc}
     */
     public OversightCommitteeDTO toDto(OversightCommittee input)
          throws DtoTransformException {
          if (input == null) {
            return null;
        }
          OversightCommitteeDTO dto = new OversightCommitteeDTO();
          dto.setIdentifier(LegacyIITransformer.INSTANCE.toDto(input.getIdentifier()));
          dto.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
          dto.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
          dto.setTypeCode(CDTransformer.INSTANCE.toDto(input.getTypeCode()));
          return dto;
     }

    /**
     * {@inheritDoc}
     */
     public OversightCommittee toXml(OversightCommitteeDTO input)
          throws DtoTransformException {
          if (input == null) {
          return null;
          }
          OversightCommittee xml = new OversightCommittee();
          xml.setIdentifier(LegacyIITransformer.INSTANCE.toXml(input.getIdentifier()));
          xml.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
          xml.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
          xml.setTypeCode(CDTransformer.INSTANCE.toXml(input.getTypeCode()));
          return xml;
     }

     /**
      * {@inheritDoc}
      */
    public OversightCommittee[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new OversightCommittee[arg0];
    }

}
