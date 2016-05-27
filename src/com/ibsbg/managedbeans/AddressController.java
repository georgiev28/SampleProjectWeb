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

import com.ibsbg.ejb.beans.AddressBean;
import com.ibsbg.entity.Address;

@SessionScoped
@Named("addressController")
public class AddressController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private AddressBean addressBean;
	
	private Address address;
	private int addressId;
	
	private List<Address> allAddresses;
	private List<Address> filteredAddresses;
	
	@PostConstruct
	public void init(){
		address = new Address();
		setAllAddresses(addressBean.printAllAddresses());
	}
	
	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public void createAddress(){
		addressBean.create(address);
		address = new Address();
		FacesContext.getCurrentInstance()
									.addMessage(null, new FacesMessage("Address is added"));
	}
	
	public void updateAddress(Address addr){
		address.setId(addr.getId());
		addressBean.update(addr);
		FacesContext.getCurrentInstance()
	    							.addMessage(null, new FacesMessage("Address successfully updated"));
	}
	
	public void removeAddress(Address addr){
		addressBean.remove(addr.getId());
		allAddresses.remove(addr);
		FacesContext.getCurrentInstance()
								    .addMessage(null, new FacesMessage("Address successfully removed"));
	}
	
	public void findAddress(){
		address = addressBean.getAddress(getAddressId());
	}
	
	public List<Address> showAllAddresses(){
		return addressBean.printAllAddresses();
	}

	public List<Address> getAllAddresses() {
		return allAddresses;
	}

	public void setAllAddresses(List<Address> allAddresses) {
		this.allAddresses = allAddresses;
	}

	public List<Address> getFilteredAddresses() {
		return filteredAddresses;
	}

	public void setFilteredAddresses(List<Address> filteredAddresses) {
		this.filteredAddresses = filteredAddresses;
	}
	
	public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
