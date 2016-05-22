package com.ibsbg.managedbeans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.ibsbg.ejb.beans.RoleDAO;
import com.ibsbg.entity.Role;

import java.io.Serializable;

@Named("roleController")
@SessionScoped
public class RoleController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private RoleDAO roleDAO;
	
	private Role role;
	private int roleId;
	
	@PostConstruct
	public void init(){
		role = new Role();
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public void addRole(Role role){
		roleDAO.addRole(role);
		role = new Role();
	}
	
	public void deleteRole(){
		roleDAO.removeRole(getRoleId());
	}
	
	public void updateRole(Role role){
		role.setRoleId(role.getRoleId());
		roleDAO.updateRole(role);
	}
	
	public void findRole(){
		role = roleDAO.getRole(getRoleId());
	}
}
