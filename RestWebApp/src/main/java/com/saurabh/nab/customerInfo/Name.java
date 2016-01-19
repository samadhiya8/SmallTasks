package com.saurabh.nab.customerInfo;

public class Name {

	private FullName fullname;
	private String title;
	private String initial;
	
	public Name() {
		super();
	}
		
	
	public Name(FullName fullname, String title, String initial) {
		super();
		this.fullname = fullname;
		this.title = title;
		this.initial = initial;
	}


	public FullName getFullname() {
		return fullname;
	}
	public void setFullname(FullName fullname) {
		this.fullname = fullname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((initial == null) ? 0 : initial.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Name other = (Name) obj;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
		if (initial == null) {
			if (other.initial != null)
				return false;
		} else if (!initial.equals(other.initial))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Name [fullname=" + fullname + ", title=" + title + ", initial=" + initial + "]";
	}
	
	
}
