package com.saurabh.nab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saurabh.nab.customerInfo.Customer;
import com.saurabh.nab.dao.ICustomerDAO;

@Component("CustomerServices")
public class CustomerServices implements ICustomerServices {

	@Autowired
	private ICustomerDAO customerDAO;

	public List<Customer> findAllCustomers() {
		return customerDAO.list();
	}

	public void saveCustomer(Customer customer) {
		try {
			customerDAO.saveOrUpdate(customer);
		} catch (Exception ex) {
			customer.setId(0);
		}
	}

	public void updateCustomer(Customer customer) {
		if(customerDAO.get(customer.getId())!=null){
			try {
				customerDAO.saveOrUpdate(customer);
			} catch (Exception ex) {
				customer.setId(0);
			}
		} else {
			customer.setId(0);
		}
	}
	
	public Customer findCustomer(int id) {
		return customerDAO.get(id);
	}

	public int deleteCustomer(int id) {
		return customerDAO.delete(id);
	}

}
