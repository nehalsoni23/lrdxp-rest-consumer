package com.cignex.restconsumer.configuration.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import com.cignex.configurable.restconsumer.constants.CignexRestConsumerPortletKeys;
import com.cignex.restconsumer.configuration.WebServiceConfiguration;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;

/**
 * @author Nehal Soni
 */

@Component(
		configurationPid = "com.cignex.restconsumer.configuration.WebServiceConfiguration",
		configurationPolicy = ConfigurationPolicy.OPTIONAL,
		immediate = true,
		property = {
			"javax.portlet.name=" + CignexRestConsumerPortletKeys.CignexRestConsumer,
		},
		service = ConfigurationAction.class
	)
public class WebServiceConfigurationAction extends DefaultConfigurationAction {


	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		String serviceUrl = ParamUtil.getString(actionRequest, "serviceUrl");
		setPreference(actionRequest, "serviceUrl", serviceUrl);
		
		super.processAction(portletConfig, actionRequest, actionResponse);
	}
	
	@Override
	public void include(PortletConfig portletConfig, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		request.setAttribute(WebServiceConfiguration.class.getName(), _webServiceConfiguration);
		super.include(portletConfig, request, response);
	}
	
	private volatile WebServiceConfiguration _webServiceConfiguration;
}
