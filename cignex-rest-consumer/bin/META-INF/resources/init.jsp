<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.cignex.restconsumer.configuration.WebServiceConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<% 
	WebServiceConfiguration webServiceConfiguration = 
		(WebServiceConfiguration) renderRequest.getAttribute(WebServiceConfiguration.class.getName());

	String serviceUrl = StringPool.BLANK;
	
	if(Validator.isNotNull(webServiceConfiguration)) {
		serviceUrl = portletPreferences.getValue("serviceUrl", webServiceConfiguration.serviceUrl());
	}
%>