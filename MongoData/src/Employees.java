

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Employees {
	
	private List<Employee> employees;
	private AtomicInteger employeeId;
	
	public Employees() {
		employees = new CopyOnWriteArrayList<Employee>();
		employeeId = new AtomicInteger();
	}
	
	public List<Employee> getEmployees() {
		return this.employees;
	}
	
	
	
	
	
	
	
	@Override
	public String toString() {
		String s = "";
		for (Employee e : employees) {
			s += e.toString();
		}
		
		return s;
	}
	
	
	public int add(String firstName, String lastName) {
		int temp = 0;
		
		return temp;
	}
	
}
