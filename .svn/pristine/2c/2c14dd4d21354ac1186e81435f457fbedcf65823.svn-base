package com.ibsbg.converter;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.ibsbg.entity.Departments;
import com.ibsbg.managedbeans.UserController;

@Named(value="departmentConverter")
@ApplicationScoped
public class DepartmentConverter implements Converter{

	@Inject
	private UserController usrController;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		int depId = Integer.parseInt(arg2);
		
		for (Departments d : usrController.getAvailableDepartments()) {
			if (d.getDepId() == depId) {
				return d;
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ((Departments)arg2).getDepId() + "";
	}
}
