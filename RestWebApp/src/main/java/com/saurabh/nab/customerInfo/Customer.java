package com.saurabh.nab.customerInfo;

public class Customer {
	
	private Integer id;
	private Name name;
	private Address address;
	private String sex;
	private String maritalStatus;
	private Integer creditRating;
	private Boolean isNABCustomer;
	
	
	public Customer() {
		super();
	}
	
	
	public Customer(Name name, Address address, String sex, String maritalStatus, Integer creditRating,
			Boolean isNABCustomer) {
		super();
		this.name = name;
		this.address = address;
		this.sex = sex;
		this.maritalStatus = maritalStatus;
		this.creditRating = creditRating;
		this.isNABCustomer = isNABCustomer;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public Integer getCreditRating() {
		return creditRating;
	}
	public void setCreditRating(Integer creditRating) {
		this.creditRating = creditRating;
	}
	public Boolean getIsNABCustomer() {
		return isNABCustomer;
	}
	public void setIsNABCustomer(Boolean isNABCustomer) {
		this.isNABCustomer = isNABCustomer;
	}


	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((creditRating == null) ? 0 : creditRating.hashCode());
		result = prime * result + ((isNABCustomer == null) ? 0 : isNABCustomer.hashCode());
		result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (creditRating == null) {
			if (other.creditRating != null)
				return false;
		} else if (!creditRating.equals(other.creditRating))
			return false;
		if (isNABCustomer == null) {
			if (other.isNABCustomer != null)
				return false;
		} else if (!isNABCustomer.equals(other.isNABCustomer))
			return false;
		if (maritalStatus == null) {
			if (other.maritalStatus != null)
				return false;
		} else if (!maritalStatus.equals(other.maritalStatus))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", sex=" + sex + ", maritalStatus="
				+ maritalStatus + ", creditRating=" + creditRating + ", isNABCustomer=" + isNABCustomer + "]";
	}

	
}
