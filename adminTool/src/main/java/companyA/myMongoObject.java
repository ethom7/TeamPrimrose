package companyA;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import companyA.Employee;

public class myMongoObject {

	//loadEmployees(hm2, dataOrder, employeeList, employeeCollection, userCollection)
	//addToMongo(employeeList, employeeCollection, userCollection)
	
	private ArrayList<Employee> employeeList;
	private MongoCollection<Document> employeeCollection;
	private MongoCollection<Document> userCollection;
	
	
	
	public ArrayList<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(ArrayList<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	public MongoCollection<Document> getEmployeeCollection() {
		return employeeCollection;
	}
	public void setEmployeeCollection(MongoCollection<Document> employeeCollection) {
		this.employeeCollection = employeeCollection;
	}
	public MongoCollection<Document> getUserCollection() {
		return userCollection;
	}
	public void setUserCollection(MongoCollection<Document> userCollection) {
		this.userCollection = userCollection;
	}
	
	
	
}
