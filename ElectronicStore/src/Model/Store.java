package Model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;

public class Store {
	private ArrayList<Employee> employees;
	private ArrayList<Bill> bills;
	private ArrayList<Items> items;
	
	
	public Store() {
        employees = new ArrayList<>();
        items = new ArrayList<>();
        bills = new ArrayList<>();
    }
	
	
	
	public void removeUser(Employee e) {
		 employees.remove(e);
		 
	}

	public ArrayList<Employee> getEmployees() {
		//System.out.println("added");
	    return employees;
	}
	
	public ArrayList<Items> getItems() {
		//System.out.println("added");
		return items;
	}
	
	public void deleteitem(Items i) {
		items.remove(i);
	}
	
	public ArrayList<Bill> getBill() {
		System.out.println("added");
		return bills;
	}
	
	public void loadUsers() throws IOException {
		employees=getUser();
	}
	

	
	//the get function of items arraylist
public ArrayList<Items> fillItems() {
	    return items;
	}

	
	
//ad a bill into the file and arraylist
public void registerBill(Bill bill) {

    bills.add(bill);

    try (FileWriter writer = new FileWriter("BILLS.txt",true)) {

        writer.write(bill.getBillId() + "|");
        writer.write(bill.getdate() + "|");
        writer.write(bill.getcashier() + "|");
        writer.write(bill.billItemsToText());
        writer.write("|" + bill.getTotal());
        writer.write("\n");

        System.out.println("Bill added in file");

    } catch (IOException e) {
        e.printStackTrace();
    }
}
	public void loadBills() {
		bills=fillBills();
	}
//ad bills into arraylist
	public ArrayList<Bill> fillBills() {

	    ArrayList<Bill> bills = new ArrayList<>();
	    File file = new File("BILLS.txt");

	    if (!file.exists()) {
	        return bills;
	    }

	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

	        String line;
	        while ((line = reader.readLine()) != null) {

	            // 🔥 FIX #1: correct split
	            String[] parts = line.split("\\|");
	            if (parts.length < 5) {
	                System.err.println("Invalid bill line: " + line);
	                continue;
	            }

	            String billId = parts[0];
	            Date date = Date.valueOf(parts[1]);
	            String cashier = parts[2];
	            String itemsText = parts[3];
	            double total = Double.parseDouble(parts[4]);

	            ArrayList<BillItems> billItems = new ArrayList<>();

	            String[] itemTexts = itemsText.split(";");
	            for (String itemText : itemTexts) {

	                // 🔥 FIX #2: skip empty items
	                if (itemText.isBlank()) continue;

	                String[] fields = itemText.split(",");
	                if (fields.length < 4) continue;

	                String name = fields[0];
	                int quantity = Integer.parseInt(fields[1]);
	                double price = Double.parseDouble(fields[2]);
	                String serie = fields[3];

	                billItems.add(new BillItems(name, price, quantity, serie));
	            }

	            bills.add(new Bill(billId, date, cashier, billItems, total));
	        }

	        System.out.println("Bills loaded successfully");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    for(int i=0;i<bills.size();i++) {
	    	System.out.println(bills.get(i).getBillId());
	    	System.out.println(bills.get(i).getcashier());
	    	System.out.println(bills.get(i).billItemsToText());
	    	System.out.println(bills.get(i).getTotal());
	    }

	    return bills;
	}
//add item to arrayList
	public void registerItems(Items item) {
		items.add(item);
}
//when closed save the changes
	public void saveItems() {
	    try (ObjectOutputStream writer =
	             new ObjectOutputStream(new FileOutputStream("items.bin"))) {

	        for (Items item : items) {
	            writer.writeObject(item);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
//put items form the arraylist to the store arraylist
public void uploadItems() throws IOException {
		items=fillItem();
	}

//put items form file to arrayList
	public ArrayList<Items> fillItem() throws IOException {
		File file = new File("items.bin");
		items = new ArrayList<>();
		
		if (!file.exists()) {
	        
	        return items;
		}		
		try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(file));) {
			while (true) {
				Items item = (Items) read.readObject();

				items.add(item);
				System.out.print(item.getName());

			}
		} catch (EOFException e) {

			return items;
		} catch (IOException e) {
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return items;
	}

//	public Employee login(String username, String password) {
//
//		for (int i = 0; i < employees.size(); i++) {
//			
//			
//
//			if ((employees.get(i).getUsername()).equals(username)
//					&& (employees.get(i).getPassword()).equals(password)) {
//				return employees.get(i);
//
//			}
//		}
//		return null;
//	}
	
	
//	public void deafultUsers() {
//		employees.add(new Admin("Ledio", "ledio2005", "Ledio", "ADMIN", 1.1, Date.valueOf("2005-10-29"), 355,"Ledio@gmail.com"));
//		employees.add(new Manager("m", "m", "M", "MANAGER", 1.1, Date.valueOf("2005-10-29"), 355,"Ledio@gmail.com"));
//		employees.add(new Cashier("c", "c", "C", "CASHIER", 1.1, Date.valueOf("2005-10-29"), 355,"Ledio@gmail.com"));
//		
//	}
//	public void setBolean(boolean E) {
//		this.E=E;
//	}
	
	
public void saveEmpoloyeeChanges() {
		    
		try (DataOutputStream writer = new DataOutputStream(new FileOutputStream("users.bin"))) {
			for(Employee employee:employees) {
			writer.writeUTF(employee.getUsername());
			writer.writeUTF(employee.getPassword());
			writer.writeUTF(employee.getName());
			writer.writeUTF(employee.getStatus());
			writer.writeUTF(String.valueOf(employee.getSalary()));
			writer.writeUTF(employee.getBirthday().toString());
			writer.writeUTF(String.valueOf(employee.getPhone()));
			writer.writeUTF(employee.getEmail());
			
		}
			System.out.println("file added");
		}
		catch (IOException e) {
			System.out.println("Error");

		}
	}

	
public void registerUser(Employee employee) {
		employees.add(employee);
	//	System.out.println("1 added");
}
	
	public ArrayList<Employee> getUser() throws IOException {

	    ArrayList<Employee> employees = new ArrayList<>();
	    File file = new File("users.bin");

	    
	    if (!file.exists()) {
System.out.println("no");
	        employees.add(new Admin(
	            "Ledio123", "ledio2005", "Ledio", "ADMIN",
	            1.1, Date.valueOf("2005-10-29"),"admin" , "Ledio@gmail.com"
	        ));

	       
	        try (DataOutputStream write =
	             new DataOutputStream(new FileOutputStream(file))) {

	            for (Employee e : employees) {
	                write.writeUTF(e.getUsername());
	                write.writeUTF(e.getPassword());
	                write.writeUTF(e.getName());
	                write.writeUTF(e.getStatus());
	                write.writeUTF(String.valueOf(e.getSalary()));
	                write.writeUTF(e.getBirthday().toString());
	                write.writeUTF(String.valueOf(e.getPhone()));
	                write.writeUTF(e.getEmail());
	            }
	           
	        }
	        return employees;
	      
	    }

	  
	    try (DataInputStream read =
	         new DataInputStream(new FileInputStream(file))) {

	        while (true) {
	            String username = read.readUTF();
	            String password = read.readUTF();
	            String name = read.readUTF();
	            String status = read.readUTF();
	            double salary = Double.parseDouble(read.readUTF());
	            Date birthday = Date.valueOf(read.readUTF());
	            String sector = read.readUTF();
	            String email = read.readUTF();

	            if (status.equals("MANAGER")) {
	                employees.add(new Manager(username, password, name, status,
	                        salary, birthday, sector, email));
	            } else if (status.equals("CASHIER")) {
	            	employees.add(new Cashier(username, password, name, status,
	                        salary, birthday, sector, email));
	                
	            } else if (status.equals("ADMIN")) {
	                employees.add(new Admin(username, password, name, status,
	                        salary, birthday, sector, email));
	            }
	         else if (status.equals("BANNED")) {
	        	employees.add(new Banned(username, password, name, status,
	        			salary, birthday, sector, email));
	        }
	            
	            
	            System.out.println(username+" "+password+" "+status);
	             
	        }

	    } catch (EOFException e) {
	        return employees;
	        
	    }
	}
  
}
