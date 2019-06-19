package uk.nhs.digital.common.components;

import org.hippoecm.hst.container.*;
import org.hippoecm.hst.core.component.*;
import org.onehippo.forge.selection.hst.contentbean.*;
import org.onehippo.forge.selection.hst.util.*;

public class PersonComponent extends BaseGaContentComponent {
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        final ValueList lawfulBasisValueList =
            SelectionUtil.getValueListByIdentifier("lawfulbasis", RequestContextProvider.get());
        if (lawfulBasisValueList != null) {
            request.setAttribute("lawfulbasis", SelectionUtil.valueListAsMap(lawfulBasisValueList));
        }
    }
}
