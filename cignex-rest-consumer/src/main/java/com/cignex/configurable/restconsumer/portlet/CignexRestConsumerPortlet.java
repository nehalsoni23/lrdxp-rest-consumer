package com.cignex.configurable.restconsumer.portlet;

import com.cignex.configurable.restconsumer.constants.CignexRestConsumerPortletKeys;
import com.cignex.restconsumer.configuration.WebServiceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Nehal Soni
 */
@Component(
	configurationPid = "com.cignex.restconsumer.configuration.WebServiceConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Cignex Rest Consumer Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CignexRestConsumerPortletKeys.CignexRestConsumer,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CignexRestConsumerPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		String webServiceUrl = _webServiceConfiguration.serviceUrl();
		
		PortletPreferences preferences = renderRequest.getPreferences();
		String portletWebServiceUrl = preferences.getValue("serviceUrl", webServiceUrl);
		
		StringBuffer outputBuffer = new StringBuffer();
		List<Map<String, String>> resultList = null;
		JSONArray objKeys = null;
		if (Validator.isUri(portletWebServiceUrl) && Validator.isNotNull(portletWebServiceUrl)) {
			URL url = new URL(portletWebServiceUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String output = StringPool.BLANK;
			while((output = br.readLine()) != null) {
				outputBuffer.append(output);
			}
			System.out.println("Output of service:");
			String strOutput = StringPool.BLANK;
			if(outputBuffer != null) {
				strOutput = outputBuffer.toString();
			}
			System.out.println(strOutput);
			
			resultList = new ArrayList<>();
			try {
				JSONArray resultArray = JSONFactoryUtil.createJSONArray(strOutput);
				Map<String, String> row = null;
				if(resultArray.length() > 0) {
					for(int i = 0; i < resultArray.length(); i++) {
						JSONObject obj = resultArray.getJSONObject(i);
						objKeys = obj.names();
						
						for(int j = 0; j < objKeys.length(); j++) {
							row = new HashMap<>();
							System.out.println("Cell value: " + obj.getString((String) objKeys.get(j)));
							row.put((String) objKeys.get(j), obj.getString((String) objKeys.get(j)));
						}
						resultList.add(row);
					}
				}
				System.out.println(resultList);
			} catch (JSONException e) {				
				e.printStackTrace();
			}
		}
		renderRequest.setAttribute(WebServiceConfiguration.class.getName(), _webServiceConfiguration);
		renderRequest.setAttribute("resultList", resultList);
		renderRequest.setAttribute("numOfColumns", objKeys.length());
		renderRequest.setAttribute("columnNames", objKeys);
		super.doView(renderRequest, renderResponse);
	}
	
	public String getServiceUrl(Map config) {
		return (String) config.get(_webServiceConfiguration.serviceUrl());
	}
	
	@Activate
	@Modified
	protected void activate(Map<Object, Object> properties) {
		_webServiceConfiguration = ConfigurableUtil.createConfigurable(WebServiceConfiguration.class, properties);
	}
	
	private volatile WebServiceConfiguration _webServiceConfiguration;
}