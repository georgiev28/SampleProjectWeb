package com.ibsbg.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.ibsbg.ejb.beans.DepartmentsBean;
import com.ibsbg.entity.Departments;

@SessionScoped
@Named("departmentsMB")
public class DepartmentsController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private DepartmentsBean departmentsBean;
	
	private Departments department;
	private int depId;
	
	private List<Departments> filteredDepartments;
	private List<Departments> allDepartments;
	
	@PostConstruct
	public void init(){
		department = new Departments();
		setAllDepartments(departmentsBean.showAllDepartments());
	}
	
	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public List<Departments> getAllDepartments() {
		return allDepartments;
	}

	public void setAllDepartments(List<Departments> allDepartments) {
		this.allDepartments = allDepartments;
	}
	
	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	public void createDepartment(){
		departmentsBean.createDepartment(department);
		allDepartments.add(department);
		department = new Departments();
	}
	
	public void updateDepartment(Departments dep){
		department.setDepId(dep.getDepId());
		departmentsBean.updateDepartments(dep);
		FacesContext.getCurrentInstance()
							 .addMessage(null, new FacesMessage(dep.getDepName()+" is updated"));
	}
	
	public void deleteDepartment(Departments dep){
		departmentsBean.removeDepartment(dep.getDepId());
		allDepartments.remove(dep);
		FacesContext.getCurrentInstance()
							.addMessage(null, new FacesMessage("Department with ID("+dep.getDepId()+") removed"));
	}
	
	public void findDepartment(){
		department = departmentsBean.getDepartment(getDepId());
	}
	
	public List<Departments> showAllDepartments(){
		return departmentsBean.showAllDepartments();
	}

	public List<Departments> getFilteredDepartments() {
		return filteredDepartments;
	}

	public void setFilteredDepartments(List<Departments> filteredDepartments) {
		this.filteredDepartments = filteredDepartments;
	}
	
	public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
