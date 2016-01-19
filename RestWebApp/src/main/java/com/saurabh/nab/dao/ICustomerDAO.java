package com.saurabh.nab.dao;

import java.util.List;

import com.saurabh.nab.customerInfo.Customer;

public interface ICustomerDAO {

	public void saveOrUpdate(Customer customer);
    
    public int delete(int contactId);
     
    public Customer get(int contactId);
     
    public List<Customer> list();
}
