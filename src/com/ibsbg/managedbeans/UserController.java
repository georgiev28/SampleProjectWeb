package com.ibsbg.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import com.ibsbg.ejb.beans.DepartmentsBean;
import com.ibsbg.ejb.beans.RoleBean;
import com.ibsbg.ejb.beans.UserBean;
import com.ibsbg.entity.Departments;
import com.ibsbg.entity.Role;
import com.ibsbg.entity.User;
import com.ibsbg.utils.DigestUtil;

@Named("userMB")
@SessionScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UserBean userBean;
	
	@EJB
	private DepartmentsBean depBean;
	
	@Inject
	private LoginController loginController;
	
	@EJB
	private RoleBean roleBean;
	
	private User user;
	private User currentUser;
	private Departments department;
	private Role role;
	
	private int userId;
	private String uName;
	private int depid;
	
	private List<Role> allRoles;
	private List<Departments> availableDepartments;
	private List<User> allUsers;
	private List<User> filteredUsers;
	
	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}
	
	public List<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@PostConstruct
	public void init(){
		user = new User();
		department = new Departments();
		role  = new Role();
		setAllRoles(roleBean.getAllRoles());
		setAllUsers(userBean.printAllUsers());
		setAvailableDepartments(depBean.showAllDepartments());
	}

	public void createUser(){
		user.setDepartment(department);
		user.setRole(role);
		userBean.create(user);
		allUsers.add(user);
		user = new User();
		FacesContext.getCurrentInstance()
						.addMessage(null, new FacesMessage("User is created"));
	}
	
	public void updateUser(User user){
		user.setUserid(user.getUserid());
		user.setDepartment(department);
		user.setRole(role);
	    userBean.update(user);
	    FacesContext.getCurrentInstance()
	    				.addMessage(null, new FacesMessage(user.getFirstName()+" is updated"));
	}
	
	public void deleteUser(User user){
		userBean.remove(user.getUserid());
		allUsers.remove(user);
		FacesContext.getCurrentInstance()
						.addMessage(null, new FacesMessage("User with ID "+user.getUserid()+" removed"));
	}
	
	public String registerUser(){
		
		if(isAlreadyRegistered(user.getUserName())){
			FacesContext.getCurrentInstance()
							.addMessage(null, new FacesMessage("User with username '"+ user.getUserName() +"' already existing."));
			user.setUserName("");
			return "/registeringuser.xhtml";
		}
		else{
			role.setRoleId(2); //user role
			user.setRole(role);
			String password = DigestUtil.md5(user.getPassword());
			user.setPassword(password);
			userBean.create(user);
			allUsers.add(user);
			user = new User();
			return "/login.xhtml";
		}
		
	}
	
	public void findUser(){
		 user = userBean.findUser(getUserId());
	}
	
	public List<User> printAllUsers(){
		return userBean.printAllUsers();
	}

	public int getDepid() {
		return depid;
	}

	public void setDepid(int depid) {
		this.depid = depid;
	}

	public List<Departments> getAvailableDepartments() {
		return availableDepartments;
	}

	public void setAvailableDepartments(List<Departments> availableDepartments) {
		this.availableDepartments = availableDepartments;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}
	
	public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public boolean hasAccess(){
		
		HttpSession session = loginController.getSession();
		currentUser = (User) session.getAttribute("user");
		
		if(currentUser.getRole().isRoleAdmin()){
			return true;
		}
		
		return false;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(List<Role> allRoles) {
		this.allRoles = allRoles;
	}
	
	public boolean isAlreadyRegistered(String uName){
		if(userBean.findUserByUserName(uName) == null){
			return false;
		}
		
		return true;
	}
}
