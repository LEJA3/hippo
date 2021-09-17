<#ftl output_format="HTML">
<#include "../../include/imports.ftl">
<#include "metaTags.ftl">
<#include "component/showAll.ftl">
<#include "contentPixel.ftl">
<#include "../macro/gridColumnGenerator.ftl">

<#macro hubArticles latestArticles>
    <div class="nhsd-t-grid nhsd-t-grid--nested">
        <div class="nhsd-t-row">
            <#list latestArticles as latest>
                <#if latest?is_first>
                    <div class="nhsd-t-col nhsd-!t-margin-bottom-6">
                        <@hubArticleItem item=latest feature=true />
                    </div>
                <#else>
                    <#assign hideItem = (latest?index gte 13)?then('js-hide-article', '') />
                    <div class="${hideItem} nhsd-t-col-xs-12 nhsd-t-col-s-6 nhsd-t-col-l-4 nhsd-!t-margin-bottom-6">
                        <@hubArticleItem item=latest />
                    </div>

                    <#if latest?index == 13>
                        <div class="nhsd-!t-display-hide js-show-all-articles-btn nhsd-t-col nhsd-!t-margin-bottom-6 nhsd-!t-text-align-center">
                            <button class="nhsd-a-button">Show All (${latestArticles?size})</button>
                        </div>
                    </#if>
                </#if>
            </#list>
            <#if latestArticles?size gte 13>
                <div class="nhsd-!t-display-hide js-show-less-articles-btn nhsd-t-col nhsd-!t-margin-bottom-6 nhsd-!t-text-align-center">
                    <button class="nhsd-a-button">Show Less</button>
                </div>
            </#if>
        </div>
    </div>
</#macro>

<#macro hubArticleItem item feature=false>
    <#assign cardClass = "" />
    <#assign imgClass = "" />
    <#if feature>
        <#assign cardClass = "nhsd-m-card--image-position-adjacent" />
        <#assign imgClass = "nhsd-a-image--cover" />
    </#if>
    <div class="nhsd-m-card nhsd-m-card--full-height nhsd-m-card--author ${cardClass}">
        <a href="<@hst.link hippobean=item/>" class="nhsd-a-box-link" aria-label="About NHS Digital">
            <div class="nhsd-a-box nhsd-a-box--bg-light-grey" itemprop="blogPost" itemscope itemtype="http://schema.org/BlogPosting">
                <div class="nhsd-m-card__image_container" itemprop="image" itemscope itemtype="http://schema.org/ImageObject">
                    <figure class="nhsd-a-image ${imgClass}">
                        <picture class="nhsd-a-image__picture">
                            <#if item.leadImage?has_content>
                                <@hst.link hippobean=item.leadImage.newsPostImage fullyQualified=true var="leadImage" />
                                <@hst.link hippobean=item.leadImage.newsPostImage2x fullyQualified=true var="leadImage2x" />

                                <meta itemprop="url" content="${leadImage}">
                                <img itemprop="contentUrl" srcset="${leadImage}, ${leadImage2x} 2x" src="${leadImage}" alt="">
                            <#else>
                                <meta itemprop="url" content="<@hst.webfile path="/images/fibre_57101102_med.jpg"/>"/>
                                <img itemprop="contentUrl" src="<@hst.webfile path="/images/fibre_57101102_med.jpg"/>" alt="">
                            </#if>
                        </picture>
                    </figure>
                </div>
                <div class="nhsd-m-card__content_container">
                    <div class="nhsd-m-card__content-box">
                        <h1 class="nhsd-t-heading-s">${item.title}</h1>
                        <div class="nhsd-t-body-s"><@hst.html hippohtml=item.summary /></div>
                    </div>

                    <div class="nhsd-m-card__button-box">
                        <span class="nhsd-a-icon nhsd-a-arrow nhsd-a-icon--size-s nhsd-a-icon--col-black">
                          <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" aria-hidden="true" focusable="false" viewBox="0 0 16 16"  width="100%" height="100%">
                            <path d="M8.5,15L15,8L8.5,1L7,2.5L11.2,7H1v2h10.2L7,13.5L8.5,15z"/>
                          </svg>
                        </span>
                    </div>
                </div>
            </div>
        </a>

        <div class="nhsd-m-card__author">
            <#if item.authors?size == 1>
                <#assign author = item.authors[0] />
                <@hst.link hippobean=author var="authorLink" />

                <div class="nhsd-m-author">
                    <#if author.personimages.picture?has_content>
                        <@hst.link hippobean=author.personimages.picture.authorPhoto fullyQualified=true var="authorImage" />
                        <@hst.link hippobean=author.personimages.picture.authorPhoto2x fullyQualified=true var="authorImage2x" />

                        <div class="nhsd-a-avatar" title="${author.title}" aria-label="${author.title}">
                            <figure class="nhsd-a-image nhsd-a-image--cover" aria-hidden="true">
                                <picture class="nhsd-a-image__picture">
                                    <img itemprop="image"
                                         class="bloghub__item__content__author__img"
                                         srcset="${authorImage} 1x, ${authorImage2x} 2x"
                                         src="${authorImage}"
                                         alt="${author.title}"/>
                                </picture>
                            </figure>
                        </div>
                    </#if>

                    <div class="nhsd-m-author__details">
                        <a href="${authorLink}" class="nhsd-a-link nhsd-t-body-s">${author.title}</a>
                        <p class="nhsd-t-body-s nhsd-!t-margin-top-2 nhsd-!t-margin-bottom-0 nhsd-!t-col-black">
                            <#if author.roles.primaryroles?has_content>${author.roles.firstprimaryrole}</#if><#if author.roles.primaryroleorg?has_content>, ${author.roles.primaryroleorg}</#if>
                        </p>
                    </div>
                </div>
            <#elseif item.authors?size gt 0>
                <div class="nhsd-m-avatar-list nhsd-!t-margin-right-2">
                    <#list item.authors as author>
                        <@hst.link hippobean=author var="authorLink" />

                        <#if author.personimages?has_content && author.personimages.picture?has_content>
                            <@hst.link hippobean=author.personimages.picture.authorPhoto fullyQualified=true var="authorImage" />
                            <@hst.link hippobean=author.personimages.picture.authorPhoto2x fullyQualified=true var="authorImage2x" />

                            <a href="${authorLink}" class="nhsd-a-avatar" title="${author.title}" aria-label="${author.title}">
                                <figure class="nhsd-a-image nhsd-a-image--cover" aria-hidden="true">
                                    <picture class="nhsd-a-image__picture">
                                        <img itemprop="image"
                                             class="bloghub__item__content__author__img"
                                             srcset="${authorImage} 1x, ${authorImage2x} 2x"
                                             src="${authorImage}"
                                             alt="${author.title}"/>
                                    </picture>
                                </figure>
                            </a>
                        <#else>
                            <a href="${authorLink}" class="nhsd-a-avatar nhsd-a-avatar--initials" title="${author.title}" aria-label="${author.title}">${author.initials}</a>
                        </#if>
                    </#list>
                </div>
            <#elseif item.authorName?has_content>
                <div class="nhsd-m-author">
                    <div class="nhsd-m-author__details">
                        <span class="nhsd-t-heading-xs nhsd-!t-margin-0 nhsd-!t-col-black">${item.authorName}</span>
                        <p class="nhsd-t-body-s nhsd-!t-margin-top-2 nhsd-!t-margin-bottom-0 nhsd-!t-col-black">
                            <#if item.authorJobTitle?has_content>${item.authorJobTitle}</#if><#if item.authororganisation?has_content>, ${item.authororganisation}</#if>
                        </p>
                    </div>
                </div>
            </#if>
        </div>
    </div>
</#macro>
