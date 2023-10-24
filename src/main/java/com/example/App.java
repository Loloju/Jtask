package com.example;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Hasher;
import model.Accounts;
import model.Address;
import model.Command;
import model.Customer;
import operations.CommandExecutor;
import operations.CustomerOps;
import operations.AddressOps;

public class App {
	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addAnnotatedClass(Customer.class);
		configuration.addAnnotatedClass(Address.class);
		configuration.addAnnotatedClass(Command.class);
		configuration.addAnnotatedClass(Accounts.class);

		SessionFactory sessionFactory = configuration.buildSessionFactory();

		Session session = sessionFactory.openSession();
//
		CustomerOps.getCustomers(session);
//
//		Address address = new Address("a", "b", "c", "d");
//
//		session.beginTransaction();
//		session.persist(address);
//		session.getTransaction().commit();
//
		Command cmd;
		Class<?>[] paramTypes = {String.class, int.class, String.class};
		cmd = new Command(CustomerOps.class, CustomerOps.class.getDeclaredMethod("insertCustomer", paramTypes).getName());

		session.beginTransaction();
		session.persist(cmd);
		session.getTransaction().commit();
//
//		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
//		parameters.put("name", "Onur");
//		parameters.put("age", 28);
//		parameters.put("tckn", "11111111111");
//		System.out
//				.println("\nCommand true: " + CommandExecutor.executeCommand("insertCustomer", parameters));
////
//		Hasher hasher = BCrypt.withDefaults();
//		byte[] hashedPassword = hasher.hash(12, "asdf123.edfrsae".toCharArray());
//		Accounts acc = new Accounts("11111111", hashedPassword.toString(), "Haydar Salatalık", "test@deneme.com", LocalDate.now().toString());
//
//		session.beginTransaction();
//		session.persist(acc);
//		session.getTransaction().commit();
//
		session.close();
		sessionFactory.close();
	}
}
