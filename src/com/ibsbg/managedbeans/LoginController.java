package com.ibsbg.managedbeans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.ibsbg.ejb.beans.UserDAO;
import com.ibsbg.entity.User;
import com.ibsbg.utils.DigestUtil;

import java.io.Serializable;

@Named("login")
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserDAO userDAO;
	
	private User currentUser;
	private String uName;
	private String pass;
	private String encodedPass;
	
	@PostConstruct
	public void init(){
		currentUser = new User();
	}
	
	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}

	public String validateUsernamePassword(){
		encodedPass = DigestUtil.md5(getPass());
		boolean valid = userDAO.validate(getuName(), encodedPass);
		
		if(valid){
			HttpSession session = getSession();
			currentUser = userDAO.findUserByUserName(uName);
			
			session.setAttribute("user", currentUser);
			return "/pages/index.xhtml?faces-redirect=true";
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please, enter correct username and Password"));
            return "/login.xhtml";
		}
		
	}
	
	public String logout(){
		HttpSession session = getSession();
		uName = "";
		pass = "";
		session.invalidate();
		return "/login.xhtml";
	}
	
	public HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }
 
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
 
    public String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("uName").toString();
    }
 
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	
}
