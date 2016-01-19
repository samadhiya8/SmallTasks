package com.saurabh.nab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.saurabh.nab.customerInfo.Address;
import com.saurabh.nab.customerInfo.Customer;
import com.saurabh.nab.customerInfo.FullName;
import com.saurabh.nab.customerInfo.Name;

@Component("CustomerDAO")
public class CustomerDAO implements ICustomerDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String customerInsertsql = "INSERT INTO customer (gender, meritalStatus, creditRating, isNABCustomer)"
			+ " VALUES (?, ?, ?, ?)";
	private static final String addressInsertsql = "INSERT INTO address (customerID, unit, streetName, suburb, "
			+ "city, country, state, pincode)" + " VALUES (?, ?, ?, ?,?,?,?,?)";
	private static final String nameInsertsql = "INSERT INTO name (customerID, firstName, middleName, surname, "
			+ "title, initials)" + " VALUES (?, ?, ?, ?,?, ?)";

	private static final String customerUpdatesql = "UPDATE customer SET gender=?, meritalStatus=?, creditRating=?, "
			+ "isNABCustomer=? WHERE id=?";

	private static final String addressUpdatesql = "UPDATE address SET unit=?, streetName=?, suburb=?, "
			+ "city=?, country=?, state=?, pincode=? WHERE customerID=?";

	private static final String NameUpdatesql = "UPDATE name SET firstName=?, middleName=?, surname=?, "
			+ "title=?, initials=? WHERE customerID=?";

	private static final String GET_ALL_CUSTOMERS = "select c.id, n.title, n.initials, n.firstName, n.surname, n.middlename, a.unit, a.streetname,"
			+ "a.suburb, a.city, a.state, a.country, a.pincode, c.gender, c.meritalstatus, c.creditrating, c.isnabcustomer from customer c, address a, name n where c.id = a.customerID and n.customerID=a.customerID";

	private static final String GET_CUSTOMER_BY_ID = "select c.id, n.title, n.initials, n.firstName, n.surname, n.middlename, a.unit, a.streetname,"
			+ "a.suburb, a.city, a.state, a.country, a.pincode, c.gender, c.meritalstatus, c.creditrating, c.isnabcustomer from customer c, address a, name n where c.id = a.customerID and n.customerID=a.customerID and a.customerID =?";
	
	private static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id=?";
	private static final String DELETE_NAME = "DELETE FROM name WHERE customerID=?";
	private static final String DELETE_ADDRESS = "DELETE FROM address WHERE customerID=?";
	    
	@Transactional
	public void saveOrUpdate(final Customer customer) {
		if (customer.getId() == null || customer.getId() <= 0) {
			// update

			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(customerInsertsql, new String[] { "id" });
					ps.setString(1, customer.getSex());
					ps.setString(2, customer.getMaritalStatus());
					ps.setInt(3, customer.getCreditRating());
					ps.setString(4, customer.getIsNABCustomer().toString());
					return ps;
				}
			}, keyHolder);
			Long customerId = (Long) keyHolder.getKey();
			customer.setId(customerId.intValue());
			jdbcTemplate.update(addressInsertsql, customerId, customer.getAddress().getStreetNo(),
					customer.getAddress().getStreetName(), customer.getAddress().getSuburb(),
					customer.getAddress().getCity(), customer.getAddress().getCountry(),
					customer.getAddress().getState(), customer.getAddress().getPincode());
			jdbcTemplate.update(nameInsertsql, customerId, customer.getName().getFullname().getFirstName(),
					customer.getName().getFullname().getMiddleName(), customer.getName().getFullname().getSurName(),
					customer.getName().getTitle(), customer.getName().getInitial());

		} else {

			jdbcTemplate.update(customerUpdatesql, customer.getSex(), customer.getMaritalStatus(),
					customer.getCreditRating(), customer.getIsNABCustomer(), customer.getId());

			jdbcTemplate.update(addressUpdatesql, customer.getAddress().getStreetNo(),
					customer.getAddress().getStreetName(), customer.getAddress().getSuburb(),
					customer.getAddress().getCity(), customer.getAddress().getCountry(),
					customer.getAddress().getState(), customer.getAddress().getPincode(), customer.getId());

			jdbcTemplate.update(NameUpdatesql, customer.getName().getFullname().getFirstName(),
					customer.getName().getFullname().getMiddleName(), customer.getName().getFullname().getSurName(),
					customer.getName().getTitle(), customer.getName().getInitial(), customer.getId());
		}

	}
	@Transactional
	public int delete(int contactId) {
		int rowsDeleted=jdbcTemplate.update(DELETE_NAME, contactId);
		rowsDeleted=rowsDeleted+jdbcTemplate.update(DELETE_ADDRESS, contactId);
		rowsDeleted=rowsDeleted+jdbcTemplate.update(DELETE_CUSTOMER, contactId);
		return rowsDeleted;

	}

	public Customer get(int contactId) {
		Customer customer;
		try{
			customer=jdbcTemplate.queryForObject(GET_CUSTOMER_BY_ID, new CustomerRowMapper(), contactId);
		} catch (EmptyResultDataAccessException ex){
			customer=null;
		}
		return customer;
	}

	public List<Customer> list() {	
		try{
			return jdbcTemplate.query(GET_ALL_CUSTOMERS, new CustomerRowMapper());
		} catch (EmptyResultDataAccessException ex){
			return null;
		}
	}

	class CustomerRowMapper implements RowMapper<Customer> {
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			if(rs==null){
				return null;
			}
			Customer aContact = new Customer();
			Name name = new Name();
			FullName fullName = new FullName();
			Address address = new Address();

			fullName.setFirstName(rs.getString("firstName"));
			fullName.setMiddleName(rs.getString("middlename"));
			fullName.setSurName(rs.getString("surname"));
			name.setFullname(fullName);
			name.setInitial(rs.getString("initials"));
			name.setTitle(rs.getString("title"));

			address.setStreetNo(rs.getString("unit"));
			address.setStreetName(rs.getString("streetname"));
			address.setSuburb(rs.getString("suburb"));
			address.setCity(rs.getString("city"));
			address.setState(rs.getString("state"));
			address.setCountry(rs.getString("country"));
			address.setPincode(rs.getString("pincode"));

			aContact.setId(rs.getInt("id"));
			aContact.setName(name);
			aContact.setAddress(address);
			aContact.setSex(rs.getString("gender"));
			aContact.setMaritalStatus(rs.getString("meritalstatus"));
			aContact.setCreditRating(rs.getInt("creditrating"));
			aContact.setIsNABCustomer(rs.getBoolean("isnabcustomer"));

			return aContact;
		}
	}

}
