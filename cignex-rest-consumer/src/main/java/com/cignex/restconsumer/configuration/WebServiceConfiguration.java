package com.cignex.restconsumer.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Nehal Soni
 */

@Meta.OCD(id = "com.cignex.restconsumer.configuration.WebServiceConfiguration")
public interface WebServiceConfiguration {

	@Meta.AD(required = false)
	public String serviceUrl();
	
}
