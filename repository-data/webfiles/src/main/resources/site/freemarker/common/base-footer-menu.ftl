<#ftl output_format="HTML">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->

<#include "../include/imports.ftl">
<#include "social-media/social-media.ftl">

<#if menu??>
<footer class="nhsd-o-global-footer" id="footer">
    <div class="nhsd-t-grid">
        <div class="nhsd-t-row">
            <div class="nhsd-t-col-12 nhsd-!t-no-gutters">
                <div class="nhsd-t-grid">
                    <div class="nhsd-t-row">
                        <#if menu.siteMenuItems??>
                            <#list menu.siteMenuItems as item>
                                <#if !item.hstLink?? && !item.externalLink??>
                                    <#if !item?is_first>
                                        </ul></nav>
                                    </#if>
                                    <nav class="nhsd-t-col-xs-12 nhsd-t-col-s-6 nhsd-t-col-l-3" aria-labelledby="footer-heading-${item.name?lower_case?replace(" ", "-")}">
                                        <div class="nhsd-t-body-s nhsd-!t-font-weight-bold nhsd-!t-margin-bottom-1" id="footer-heading-${item.name?lower_case?replace(" ", "-")}">${item.name}</div>
                                        <ul class="nhsd-t-list nhsd-t-list--links">
                                <#else>
                                    <#if item.hstLink??>
                                        <#assign href><@hst.link link=item.hstLink/></#assign>
                                    <#elseif item.externalLink??>
                                        <#assign href>${item.externalLink?replace("\"", "")}</#assign>
                                    </#if>
                                    <#if  item.selected || item.expanded>
                                        <li class="nhsd-t-body-s active">
                                            <a class="nhsd-a-link nhsd-a-link--col-dark-grey" href="${href}">${item.name}</a>
                                        </li>
                                    <#else>
                                        <li class="nhsd-t-body-s">
                                            <#if item.externalLink?? && isSocal(item.name)>
                                                <li class="nhsd-t-body-s">
                                                  <span class="nhsd-a-icon nhsd-a-icon--size-l nhsd-a-icon--col-dark-grey">
                                                      <@getSocalLogoFor item.name />
                                                  </span>
                                                    <a target="_blank" class="nhsd-a-link nhsd-a-link--col-dark-grey" href="${href}" rel="external">
                                                        ${item.name}
                                                        <span class="nhsd-t-sr-only">(external link, opens in a new tab)</span>
                                                    </a>
                                                </li>
                                            <#else>
                                                <a class="nhsd-a-link nhsd-a-link--col-dark-grey" href="${href}" rel="external">${item.name}</a>
                                            </#if>
                                        </li>
                                    </#if>
                                </#if>
                                <#if item?is_last>
                                    </ul></nav>
                                </#if>
                            </#list>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <@hst.cmseditmenu menu=menu/>
</footer>
</#if>
