package com.saurabh.nab.dao;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.saurabh.nab.customerInfo.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
"file:src/main/webapp/WEB-INF/springrest-servlet.xml"
})

public class TestCustomerDAO {

	@Autowired
	private ICustomerDAO customerDAO;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private TestHelper helper=new TestHelper();
	
	
	@Test
	public void testSaveOrUpdate(){
		Integer initialCount = helper.countCustomers(jdbcTemplate);	
		Customer customer=helper.getTestCustomer();
		customerDAO.saveOrUpdate(customer);
		assertEquals(++initialCount, helper.countCustomers(jdbcTemplate));
	}
	
	/**
	 * for Address table value for unit is larger then defined, expecting DataIntegrityViolationException
	 */
	@Test(expected=DataIntegrityViolationException.class)
	public void testSaveOrUpdateExceptionWhileCreatingAddress(){
		Customer customer=helper.getTestCustomer();
		customer.getAddress().setStreetNo("1289999998087");
		customerDAO.saveOrUpdate(customer);
	}
	
	/**
	 * for Address table value for unit is larger then defined so insert must fail in address table so entry should rollback from customer table
	 */
	@Test
	public void testSaveOrUpdateTransactionRollback(){
		Integer initialCount = helper.countCustomers(jdbcTemplate);
		Customer customer=helper.getTestCustomer();
		customer.getAddress().setStreetNo("1289999998087");
		try{
			customerDAO.saveOrUpdate(customer);
		} catch (DataIntegrityViolationException ex){
			
		}
		assertEquals(initialCount, helper.countCustomers(jdbcTemplate));
	}
	
	@Test
	public void testFindCustomer(){
		Customer customer=helper.getTestCustomer();
		customer.getName().getFullname().setFirstName("John");
		customerDAO.saveOrUpdate(customer);
		Integer id = helper.getMaxCustomerId(jdbcTemplate);
		Customer customerFromDB=customerDAO.get(id);
		assertNotNull("Fail to read customer from DB", customerFromDB);
		assertEquals("Different customer found",customer, customerFromDB);
	}
	
	@Test
	public void testFindCustomerNonExisting(){
		Integer id = helper.getMaxCustomerId(jdbcTemplate);
		Customer customerFromDB=customerDAO.get(++id);
		assertNull(customerFromDB);
	}
	
	@Test
	public void testUpdate(){
		Integer id = helper.getMaxCustomerId(jdbcTemplate);
		Customer existingCustomer=customerDAO.get(id);
		existingCustomer.getName().getFullname().setFirstName("testUpdate");
		customerDAO.saveOrUpdate(existingCustomer);
		assertEquals(existingCustomer, customerDAO.get(id));
	}
	
	@Test
	public void testUpdateCustomerFailed(){
		Integer id = helper.getMaxCustomerId(jdbcTemplate);
		Customer existingCustomer=customerDAO.get(id);
		existingCustomer.getAddress().setStreetNo("1289999998087");
		try{
			customerDAO.saveOrUpdate(existingCustomer);
		} catch (DataIntegrityViolationException ex){
			
		}
		assertNotEquals(existingCustomer, customerDAO.get(id));
	}
	
	@Test
	public void testDelete(){
		Integer id = helper.getMaxCustomerId(jdbcTemplate);
		customerDAO.delete(id);
		assertNull(customerDAO.get(id));
	}
	
	@Test
	public void testDeleteNonExisting(){
		Integer id = helper.getMaxCustomerId(jdbcTemplate);
		int rowsDeleted=customerDAO.delete(++id);
		assertEquals(0,rowsDeleted);
	}
	
	@Test
	public void testList(){
		Integer count = helper.countCustomers(jdbcTemplate);
		if(count==0){
			assertNull(customerDAO.list());
		}else 
			assertEquals(count, Integer.valueOf(customerDAO.list().size()));
	}
	
	
}
