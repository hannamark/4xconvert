package gov.nih.nci.coppa.services.pa.grid.dto.pa.faults;

import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.wsrf.utils.AnyHelper;
import org.oasis.wsrf.faults.BaseFaultTypeErrorCode;

import gov.nih.nci.coppa.services.grid.faults.CoppaFaultHelper;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ErrorCode;

/**
 * Transforms PAFault types.
 */
public final class PAFaultTransformer
    extends AbstractTransformer<PAFault, PAException>
    implements Transformer<PAFault, PAException> {
    /**
     * Public singleton.
     */
    public static final PAFaultTransformer INSTANCE = new PAFaultTransformer();

    private PAFaultTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public PAException toDto(PAFault input) throws DtoTransformException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public PAFault toXml(PAException input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        PAFault fault = CoppaFaultHelper.toFault(new PAFault(), input);

        BaseFaultTypeErrorCode errorCode = new BaseFaultTypeErrorCode();
        URI axisUri = null;
        try {
            axisUri = new URI(ErrorCode.getErrorCodeLocation().toString());
        } catch (MalformedURIException e) {
            throw new RuntimeException(e);
        }
        errorCode.setDialect(axisUri);
        errorCode.set_any(AnyHelper.toText(input.getErrorCode().name()));
        fault.setErrorCode(errorCode);

        return fault;
    }

    /**
     * {@inheritDoc}
     */
    public PAFault[] createXmlArray(int arg0) throws DtoTransformException {
        return new PAFault[arg0];
    }
}
