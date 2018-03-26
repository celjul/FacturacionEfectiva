package com.entich.ezfact.web.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateObjectMapper extends ObjectMapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1168346846090019166L;

	public HibernateObjectMapper() {
		registerModule(new Hibernate4Module());
	}

}
