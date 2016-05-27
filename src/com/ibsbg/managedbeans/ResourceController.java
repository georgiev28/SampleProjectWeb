package com.ibsbg.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.ibsbg.ejb.beans.ResourceBean;
import com.ibsbg.entity.Resource;

@Named("resourceController")
@SessionScoped
public class ResourceController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ResourceBean resourceBean;
	
	private Resource resource;
	
	private List<Resource> allResources;
	
	@PostConstruct
	public void init(){
		resource = new Resource();
		setAllResources(resourceBean.findAllResources());
	}
	
	public void addResource(){
		resourceBean.create(resource);
		allResources.add(resource);
		resource = new Resource();
	}
	
	public void updateResource(Resource resource){
		resource.setResId(resource.getResId());
		resourceBean.update(resource);
	}
	
	public void deleteResource(Resource resource){
		allResources.remove(resource);
		resourceBean.remove(resource.getResId());
	}
	
	public List<Resource> printAllResources(){
		return resourceBean.findAllResources();
	}
	
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List<Resource> getAllResources() {
		return allResources;
	}

	public void setAllResources(List<Resource> allResources) {
		this.allResources = allResources;
	}
}
