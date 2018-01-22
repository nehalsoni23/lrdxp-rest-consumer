<%@ include file="init.jsp" %>

<p>
	<b><liferay-ui:message key="cignex-rest-consumer.caption"/></b>
</p>

<% 
	boolean noConfig = Validator.isNull(serviceUrl);
	List<Map<String, String>> resultList = null;
	int numOfColumns = 0;
	int index = 0;
	JSONArray columnNames = null;
	if(renderRequest.getAttribute("resultList") != null && renderRequest.getAttribute("numOfColumns") != null) {
		resultList = (List<Map<String, String>>) renderRequest.getAttribute("resultList");
		numOfColumns = (int) renderRequest.getAttribute("numOfColumns");
		columnNames = (JSONArray) renderRequest.getAttribute("columnNames");
	}
%>

<c:choose>
    <c:when test="<%= noConfig %>">
        <p>
            Please configure parameter to consume web service using portlet configuration
        </p>
    </c:when>

    <c:otherwise>
        WebService URL: <%= serviceUrl %>
    </c:otherwise>
</c:choose>

<%-- 
	Work in progress for search-container
	<liferay-ui:search-container emptyResultsMessage="No data was received from the url configured" total="resultList.size()">
	
	<liferay-ui:search-container-results results="resultList" />
	<liferay-ui:search-container-row>
	
	<c:forEach begin="0" end="<%= numOfColumns %>">
		<% Map<String, String> row = resultList.get(0); %>
		<liferay-ui:search-container-column-text align="center" name="<%= columnNames.get(index++) %>" value="<%= row.get(columnNames.get(index++)) %>"></liferay-ui:search-container-column-text>
	</c:forEach>
	</liferay-ui:search-container-row>
</liferay-ui:search-container> --%>