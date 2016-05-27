package com.ibsbg.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.ibsbg.ejb.beans.ResourceBean;
import com.ibsbg.ejb.beans.ResourcePropertyBean;
import com.ibsbg.entity.Resource;
import com.ibsbg.entity.ResourceProperty;

@Named("propertiesController")
@SessionScoped
public class ResourcePropertyController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ResourcePropertyBean rpBean;
	
	@EJB
	private ResourceBean resourceBean;
	
	private ResourceProperty property;
	private Resource resource;
	private int propId;
	private int resId;
	private int counterFields;
	
	private List<ResourceProperty> properties;
	private List<ResourceProperty> propertiesByResource;
	private List<ResourceProperty> allProperties;
	private List<Resource> availableResources;
	
	@PostConstruct
	public void init(){
		counterFields = 0;
		resource = new Resource();
		property = new ResourceProperty();
		setAllProperties(rpBean.findAllProperties());
		setAvailableResources(resourceBean.findAllResources());
		properties = new ArrayList<ResourceProperty>();
	}
	
	public void saveProperty(){
		resource = resourceBean.findResource(getResId());
		property.setResource(resource);
		rpBean.create(property);
		allProperties.add(property);
	}
	
	public void updateProperty(ResourceProperty prop){
		property.setResPropId(prop.getResPropId());
		rpBean.update(property);
	}
	
	public void removeProperty(ResourceProperty prop){
		rpBean.remove(prop.getResPropId());
		properties.remove(property);
	}
	
	public void addPropertyField(){
		if(counterFields == 5){
			FacesContext.getCurrentInstance()
							.addMessage(null, new FacesMessage("Max property fields reached!"));
		}
		
		property = new ResourceProperty();
		properties.add(property);
		counterFields++;
	}
	
	public void removePropertyField(ResourceProperty prop){
		properties.remove(prop);
		counterFields--;
	}
	
//	public List<ResourceProperty> findPropertiesByResource(Resource resource){
//		return rpBean.findAllPropertiesByResource(resource);
//	}
	
//	public List<ResourceProperty> printAllProperties(){
//		return rpBean.findAllProperties();
//	}
	 
	public ResourceProperty getProperty() {
		return property;
	}

	public List<ResourceProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<ResourceProperty> properties) {
		this.properties = properties;
	}

	public void setProperty(ResourceProperty property) {
		this.property = property;
	}

	public List<ResourceProperty> getPropertiesByResource() {
		return propertiesByResource;
	}

	public void setPropertiesByResource(List<ResourceProperty> propertiesByResource) {
		this.propertiesByResource = propertiesByResource;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List<ResourceProperty> getAllProperties() {
		return allProperties;
	}

	public void setAllProperties(List<ResourceProperty> allProperties) {
		this.allProperties = allProperties;
	}

	public List<Resource> getAvailableResources() {
		return availableResources;
	}

	public void setAvailableResources(List<Resource> availableResources) {
		this.availableResources = availableResources;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}
}
