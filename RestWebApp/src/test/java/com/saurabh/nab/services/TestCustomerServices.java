package com.saurabh.nab.services;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.saurabh.nab.customerInfo.Customer;
import com.saurabh.nab.dao.TestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
"file:src/main/webapp/WEB-INF/springrest-servlet.xml"
})
public class TestCustomerServices {

	@Autowired
	private ICustomerServices customerService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private TestHelper helper=new TestHelper();
	
	@Test
	public void testSaveCustomer(){
		Customer customer=helper.getTestCustomer();
		customerService.saveCustomer(customer);
		assertNotEquals(customer.getId(),new Integer(0));
	}
	
	@Test
	public void testSaveCustomerFailed(){
		Customer customer=helper.getTestCustomer();
		customer.getAddress().setStreetNo("1289999998087");
		customerService.saveCustomer(customer);
		assertEquals(new Integer(0),customer.getId());
	}
	
	@Test
	public void testUpdateCustomer(){
		Integer id = helper.getMaxCustomerId(jdbcTemplate);
		Customer existingCustomer=customerService.findCustomer(id);
		existingCustomer.getName().getFullname().setFirstName("testUpdateCustomer");
		customerService.updateCustomer(existingCustomer);
		assertEquals(existingCustomer, customerService.findCustomer(id));
	}
	
	@Test
	public void testUpdateCustomerFail(){
		Integer id = helper.getMaxCustomerId(jdbcTemplate);
		Customer customer=helper.getTestCustomer();
		customer.setId(++id);
		customerService.updateCustomer(customer);
		assertEquals(new Integer(0),customer.getId());
	}
}
