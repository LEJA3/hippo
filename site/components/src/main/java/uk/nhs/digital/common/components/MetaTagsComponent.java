package uk.nhs.digital.common.components;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.CommonComponent;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

public class MetaTagsComponent extends CommonComponent {

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        String seoSummary = null;
        String title = null;
        String query = "/jcr:root/content/documents/corporate-website" + request.getPathInfo() + "/content//*[@jcr:primaryType='website:visualhub']";
        HstRequestContext requestContext = request.getRequestContext();
        try {
            QueryManager jcrQueryManager = requestContext.getSession().getWorkspace().getQueryManager();
            Query jcrQuery = jcrQueryManager.createQuery(query, "xpath");
            QueryResult queryResult = jcrQuery.execute();
            NodeIterator nodeIterator = queryResult.getNodes();
            if (nodeIterator.hasNext()) {
                Node node = nodeIterator.nextNode();
                seoSummary = node.getProperty("website:seosummarytext").getValue().getString();
                title = node.getProperty("website:title").getValue().getString();
            }
            request.setAttribute("title", title);
            request.setAttribute("summary", seoSummary);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
