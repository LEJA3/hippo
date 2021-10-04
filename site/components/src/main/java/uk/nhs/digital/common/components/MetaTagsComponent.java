package uk.nhs.digital.common.components;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.CommonComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

public class MetaTagsComponent extends CommonComponent {
    private static final Logger log = LoggerFactory.getLogger(MetaTagsComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        log.debug("--->>>   Inside  MetaTagsComponent<<<-- " + request.getPathInfo());
        String seoSummary = null;
        String title = null;
        String query = "/jcr:root/content/documents/corporate-website" + request.getPathInfo() + "/content/*";
        log.debug("Inside  MetaTagsComponent " + query);
        HstRequestContext requestContext = request.getRequestContext();
        try {
            QueryManager jcrQueryManager = requestContext.getSession().getWorkspace().getQueryManager();
            Query jcrQuery = jcrQueryManager.createQuery(query.replace("//", "/"), "xpath");
            QueryResult queryResult = jcrQuery.execute();
            NodeIterator nodeIterator = queryResult.getNodes();
            if (nodeIterator.hasNext()) {
                Node node = nodeIterator.nextNode();
                seoSummary = node.getProperty("website:seosummarytext").getValue().getString();
                title = node.getProperty("website:title").getValue().getString();
                log.debug("Value of title is " + title);
                log.debug("Value of summary  is " + seoSummary);
            }
            request.setAttribute("title", title);
            request.setAttribute("summary", seoSummary);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
