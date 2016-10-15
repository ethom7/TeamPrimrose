import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeId implements Serializable {

	private static AtomicInteger employeeId;
	
	public static int getNextEmployeeId() {
		return employeeId.incrementAndGet();
	}
}
