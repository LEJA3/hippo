package uk.nhs.digital.website.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

import java.util.*;

@HippoEssentialsGenerated(internalName = "website:general")
@Node(jcrType = "website:general")
public class General extends CommonFieldsBean {
    @HippoEssentialsGenerated(internalName = "website:sections")
    public List<HippoBean> getSections() {
        return getChildBeansByName("website:sections");
    }

    @HippoEssentialsGenerated(internalName = "website:component")
    public HippoHtml getComponent() {
        return getHippoHtml("website:component");
    }

    @HippoEssentialsGenerated(internalName = "website:items")
    public List<?> getBlocks() {
        return getChildBeansByName("website:items");
    }

    @HippoEssentialsGenerated(internalName = "website:type")
    public String getType() {
        return getProperty("website:type");
    }

    @HippoEssentialsGenerated(internalName = "website:gossid")
    public Long getGossid() {
        return getProperty("website:gossid");
    }

    @HippoEssentialsGenerated(internalName = "website:htmlCode")
    public String getHtmlCode() {
        return getProperty("website:htmlCode");
    }

    @HippoEssentialsGenerated(internalName = "website:metadata")
    public String[] getMetadata() {
        return getProperty("website:metadata");
    }

    @HippoEssentialsGenerated(internalName = "website:pageicon")
    public HippoGalleryImageSet getPageIcon()  {
        return getLinkedBean("website:pageicon", HippoGalleryImageSet.class);
    }

}
