<#ftl output_format="HTML">

<#function isSocal linkName>
    <#assign ln = linkName?lower_case>
    <#return (ln?contains("facebook") || ln?contains("linkedin") || ln?contains("twitter") || ln?contains("youtube"))>
</#function>

<#macro getSocalLogoFor linkName>
    <#assign ln = linkName?lower_case>
    <#if ln?contains("facebook")>
        <@facebookLogo />
    <#elseif ln?contains("linkedin")>
        <@linkedinLogo />
    <#elseif ln?contains("twitter")>
        <@twiterLogo />
    <#elseif ln?contains("youtube")>
        <@youtubeLogo />
    </#if>
</#macro>

<#macro facebookLogo>
    <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" aria-hidden="true" focusable="false" viewBox="0 0 16 16">
        <path d="M8,16l-6.9-4V4L8,0l6.9,4v8L8,16z M2,11.5L8,15l6-3.5v-7L8,1L2,4.5V11.5z"></path>
        <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" aria-hidden="true" focusable="false" viewBox="0 0 16 16" width="42%" height="42%" x="29%" y="29%">
            <path d="M11.6,1.2v2.2h-1.3c-0.5,0-0.8,0.1-1,0.3C9.2,3.9,9.1,4.2,9.1,4.6v1.6h2.4l-0.3,2.5H9.1v6.3H6.5V8.6 H4.4V6.1h2.1V4.3c0-1,0.3-1.8,0.9-2.4C8,1.3,8.7,1.1,9.7,1.1C10.5,1.1,11.2,1.1,11.6,1.2z"></path>
        </svg>
    </svg>
</#macro>

<#macro linkedinLogo>
    <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" aria-hidden="true" focusable="false" viewBox="0 0 16 16">
        <path d="M8,16l-6.9-4V4L8,0l6.9,4v8L8,16z M2,11.5L8,15l6-3.5v-7L8,1L2,4.5V11.5z"></path>
        <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" aria-hidden="true" focusable="false" viewBox="0 0 16 16" width="42%" height="42%" x="29%" y="29%">
            <path d="M14.1,14.3V8.9c0-4.2-4.5-4-5.6-2V5.5H6.1v8.7h2.4V9.8c0-2.5,3.2-2.7,3.2,0v4.5H14.1z"></path>
            <path d="M3.3,1.7c-0.8,0-1.4,0.6-1.4,1.4s0.6,1.4,1.4,1.4c0.8,0,1.4-0.6,1.4-1.4S4.1,1.7,3.3,1.7z"></path>
            <rect x="2.1" y="5.5" width="2.4" height="8.7"></rect>
        </svg>
    </svg>
</#macro>

<#macro twiterLogo>
    <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet"
         aria-hidden="true" focusable="false" viewBox="0 0 16 16">
        <path d="M8,16l-6.9-4V4L8,0l6.9,4v8L8,16z M2,11.5L8,15l6-3.5v-7L8,1L2,4.5V11.5z"/>
        <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet"
             aria-hidden="true" focusable="false" viewBox="0 0 16 16" width="42%"
             height="42%" x="29%" y="29%">
            <path d="M15,3.7c-0.4,0.6-0.9,1.1-1.4,1.5c0,0.1,0,0.2,0,0.4c0,0.8-0.1,1.5-0.3,2.3 c-0.2,0.8-0.6,1.5-1,2.2c-0.5,0.7-1,1.3-1.6,1.9c-0.6,0.5-1.4,1-2.3,1.3c-0.9,0.3-1.8,0.5-2.9,0.5c-1.6,0-3.1-0.4-4.4-1.3 c0.2,0,0.4,0,0.7,0c1.3,0,2.5-0.4,3.6-1.2c-0.6,0-1.2-0.2-1.7-0.6c-0.5-0.4-0.8-0.8-1-1.4c0.2,0,0.4,0,0.5,0c0.3,0,0.5,0,0.8-0.1 C3.2,9,2.7,8.7,2.2,8.2C1.8,7.6,1.6,7,1.6,6.4v0C2,6.5,2.4,6.7,2.9,6.7c-0.4-0.3-0.7-0.6-0.9-1C1.7,5.2,1.6,4.8,1.6,4.3 c0-0.5,0.1-1,0.4-1.4C2.7,3.7,3.6,4.4,4.6,5s2.1,0.8,3.3,0.9c0-0.2-0.1-0.4-0.1-0.7c0-0.8,0.3-1.5,0.8-2c0.6-0.6,1.2-0.8,2-0.8 c0.8,0,1.5,0.3,2.1,0.9c0.6-0.1,1.3-0.4,1.8-0.7c-0.2,0.7-0.6,1.2-1.3,1.6C13.9,4.1,14.4,3.9,15,3.7z"/>
        </svg>
    </svg>
</#macro>

<#macro youtubeLogo>
  <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" aria-hidden="true" focusable="false" viewBox="0 0 16 16">
      <path d="M8,16l-6.9-4V4L8,0l6.9,4v8L8,16z M2,11.5L8,15l6-3.5v-7L8,1L2,4.5V11.5z"/>
      <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" aria-hidden="true" focusable="false" viewBox="0 0 16 16" width="42%" height="42%" x="29%" y="29%">
          <path d="M8,3.1c0,0-4.4,0-5.5,0.3C1.9,3.6,1.5,4,1.3,4.6C1,5.7,1,8,1,8s0,2.3,0.3,3.4 c0.2,0.6,0.6,1.1,1.2,1.2C3.6,12.9,8,12.9,8,12.9s4.4,0,5.5-0.3c0.6-0.2,1.1-0.6,1.2-1.2C15,10.3,15,8,15,8s0-2.3-0.3-3.4 c-0.2-0.6-0.6-1.1-1.2-1.2C12.4,3.1,8,3.1,8,3.1z M6.6,5.9L10.2,8l-3.6,2.1V5.9L6.6,5.9z"></path>
      </svg>
  </svg>
</#macro>

