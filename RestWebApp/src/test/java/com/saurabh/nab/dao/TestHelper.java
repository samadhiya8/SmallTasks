package com.saurabh.nab.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.saurabh.nab.customerInfo.Address;
import com.saurabh.nab.customerInfo.Customer;
import com.saurabh.nab.customerInfo.FullName;
import com.saurabh.nab.customerInfo.Name;

public class TestHelper {

	private final static String SQL_TO_COUNT_CUSTOMER="select count(1) from customer";
	private final static String SQL_TO_GET_MAX_CUSTOMER_ID="select max(id) from customer";

	public TestHelper() {
		
	}

	public  Customer getTestCustomer(){
		Address address=new Address("128","BoldreWood Parade", "Reservoir", "Darebin","VIC","AUS","3073");
		FullName fullName=new FullName("Raj","Kumar","Tiwari");
		Name name= new Name(fullName, "Mr","X");
		 return new Customer(name,address, "Male","Single",87,true);
	}
	
	public  Integer countCustomers(JdbcTemplate jdbcTemplate){
		return jdbcTemplate.queryForObject(SQL_TO_COUNT_CUSTOMER, Integer.class);
	}
	
	public Integer getMaxCustomerId(JdbcTemplate jdbcTemplate){
		return jdbcTemplate.queryForObject(SQL_TO_GET_MAX_CUSTOMER_ID, Integer.class);
	}
}
