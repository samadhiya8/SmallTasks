package com.saurabh.nab.services;

import java.util.List;

import com.saurabh.nab.customerInfo.Customer;

public interface ICustomerServices {

	public List<Customer> findAllCustomers() ;
	public void saveCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public Customer findCustomer(int id);
	public int deleteCustomer(int id);
}
