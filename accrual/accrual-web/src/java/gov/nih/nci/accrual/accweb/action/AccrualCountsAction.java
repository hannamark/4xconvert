package gov.nih.nci.accrual.accweb.action;

import gov.nih.nci.accrual.dto.util.AccrualCountsDto;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

/**
 * @author Kalpana Guthikonda
 * @since 10/24/2012
 */
public class AccrualCountsAction extends AbstractListAccrualAction<AccrualCountsDto> {

    private static final long serialVersionUID = -8243314140150276938L;

    @Override
    public void loadDisplayList() {
        try {
            RegistryUser ru = PaServiceLocator.getInstance().getRegistryUserService().getUser(
                    CaseSensitiveUsernameHolder.getUser());
            List<AccrualCountsDto> countList = getSearchTrialSvc().getAccrualCountsForUser(ru);
            setDisplayTagList(countList);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
    }
}
