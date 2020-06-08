package uk.nhs.digital.website.beans;

import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "website:visualinternallink")
@Node(jcrType = "website:visualinternallink")
public class VisualInternallink extends Internallink {

    @HippoEssentialsGenerated(internalName = "website:shortsummary")
    public String getSummary() {
        return getSingleProperty("website:shortsummary");
    }

    @HippoEssentialsGenerated(internalName = "website:icon")
    public CorporateWebsiteImageset getIcon() {
        return getLinkedBean("website:icon", CorporateWebsiteImageset.class);
    }

}
