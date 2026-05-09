package Controll;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import Model.Bill;
import Model.Cashier;
import Model.Employee;
import Model.Items;
import Model.Store;

public class StoreControll {
	private Store store;
	private Employee emplo;

	public StoreControll(Store store) {
		this.store = store;

	}

	public Employee checkInfo(String username, String password) {
		ArrayList<Employee> e = store.getEmployees();
		for (int i = 0; i < e.size(); i++) {
			System.out.println("checking" + " " + e.get(i).getUsername() + " " + e.get(i).getPassword());
			if (e.get(i).getUsername().equals(username) && e.get(i).getPassword().equals(password)) {

				return e.get(i);
			}
		}

		return null;
	}

	public void setEmployee(Employee emplo) {
		this.emplo = emplo;

	}

	public Employee getEmplo() {
		return emplo;
	}

	public ArrayList<Employee> getListEmployee() {
		return store.getEmployees();
	}

	public ArrayList<Employee> getCashiers(ArrayList<Employee> l) {
		ArrayList<Employee> e = new ArrayList<>();
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i) instanceof Cashier) {
				e.add(l.get(i));
			}

		}
		return e;
	}

	public ArrayList<Items> getListItems() {
		return store.getItems();
	}

	public void removeEmployee(Employee e) {

		store.removeUser(e);

	}

	public void addEmployee(Employee e) {
		store.registerUser(e);

	}

	public void editEmployee(Employee e, String name, String salary, String status, String email, LocalDate birthday,
			String sector) {
		e.setName(name);
		e.setSalary(Double.valueOf(salary));
		e.setPhone(sector);
		e.setEmail(email);
		e.setBirthday(Date.valueOf(birthday));
		e.setStatus(status);
		
	}

	public void editItem(Items i, String name, String brand, String price, String stock, String serie,String bprice,String sector) {
		i.setName(name);
		i.setBrand(brand);
		i.setPrice(Double.valueOf(price));
		i.setBprice(Double.valueOf(bprice));
		i.setStock(Integer.valueOf(stock));
		i.setSerie(serie);
		i.setSpesifics(sector);

	}

	public void addItems(Items i) {
		store.registerItems(i);
	}

	public void removeItem(Items item) {
		store.deleteitem(item);
	}

	public void addtheBill(Bill bill) {
		
		store.registerBill(bill);
	}
	
	public ArrayList<Bill> getBills(){
		return store.getBill();
	}
	
	public ArrayList<Bill> BillsToday() {

	    ArrayList<Bill> allBills = store.getBill();
	    ArrayList<Bill> result = new ArrayList<>();

	    java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());

	    for (Bill bill : allBills) {
	        if (bill.getdate().compareTo(today) == 0) {
	            result.add(bill);
	        }
	    }

	    return result;
	}
	
	public ArrayList<Bill> billsBetweenDates(String name, Date endDate, Date startDate) {

	    ArrayList<Bill> result = new ArrayList<>();

	    for (Bill bill : store.getBill()) {

	        Date billDate = bill.getdate();

	        if (
	            bill.getcashier().equals(name)
	            && !billDate.before(startDate)
	            && !billDate.after(endDate)
	        ) {
	            result.add(bill);
	        }
	    }

	    return result;
	}
	
	 public ArrayList<Items> sortSectors(ArrayList<Items> items, String s){
	    	ArrayList<Items> n=new ArrayList<>();
	    	
	    	
	    	for(Items i:store.getItems() ) {
	    		if(i.getSpefics().equals(s) || s.equals("Both")) {
	    			n.add(i);
	    			System.out.println("item added");
	    		}
	    		
	    	}
	    	return n;
	    	
	    }
	 
	 public ArrayList<Employee> sortEmployees(String s,ArrayList<Employee> emp){
		 ArrayList<Employee> n=new ArrayList<>();
		 
		 
		 for(Employee e:emp ) {
			 if(s.equals("Both")) {
				 n=emp;
				 return n;
			 }
			 if(e.getPhone().equals(s) ) {
				 n.add(e);
				 System.out.println("item added");
			 }
			 
		 }
		 return n;
		 
	 }
	 
	 
	

}
