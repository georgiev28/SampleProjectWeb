package com.ibsbg.converter;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.ibsbg.entity.Role;
import com.ibsbg.managedbeans.UserController;

@Named("roleConverter")
@ApplicationScoped
public class RoleConverter implements Converter{

	@Inject
	private UserController usrController;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		int rolId = Integer.parseInt(value);
		
		for (Role role : usrController.getAllRoles()) {
			if(role.getRoleId() == rolId){
				return role;
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((Role)value).getRoleId() + "";
	}

}
