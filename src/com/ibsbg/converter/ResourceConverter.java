package com.ibsbg.converter;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.ibsbg.entity.Resource;
import com.ibsbg.entity.ResourceProperty;
import com.ibsbg.managedbeans.ResourcePropertyController;

@FacesConverter(forClass=Resource.class, value="resourceConverter")
public class ResourceConverter implements Converter {

	@Inject
	private ResourcePropertyController rpController;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		int resourceId = Integer.parseInt(value);
		
		for (Resource res : rpController.getAvailableResources()) {
			if (res.getResId() == resourceId) {
				return res;
			}
		}
				
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
	        return "";
	    }
		
		Integer id = null;
		
		try {
			id = ((Resource)value).getResId();
		} catch (ClassCastException cce) {
			cce.printStackTrace();
		}
		
		return (id != null) ? String.valueOf(id) : null;
	}
}
