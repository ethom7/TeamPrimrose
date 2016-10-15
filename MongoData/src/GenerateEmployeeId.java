import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GenerateEmployeeId {
	
	private Integer employeeId;
	
	public GenerateEmployeeId() {
		
		EmployeeId emp = new EmployeeId();
		
		try {
			this.employeeId = (Integer) deserialize("empId.ser");
			
			
			emp.getNextEmployeeId();
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		try {
			serialize(employeeId, "empId.ser");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object obj = ois.readObject();
		ois.close();
		return obj;
		
	}
	
	public static void serialize(Object obj, String fileName) throws IOException {
        
		FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }
	
	
	
	
}
