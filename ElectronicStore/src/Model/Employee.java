package Model;


import java.sql.Date;

public abstract class Employee {
	private String username;
	private String password;
	private String name;
	private String status;
	private double salary;
	private Date birthday;
	private String sector;
	private String email;
	
	Employee(String username,String password,String name,String status,double salary,Date birthday, String sector,String email)
	{
		this.username=username;
		this.password=password;
		this.name=name;
		this.status=status;
		this.salary=salary;
		this.birthday=birthday;
		this.sector=sector;
		this.email=email;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	public String getUsername() {
		return username;
	}
	
	
	public void setPassword(String password) {
		this.password=password;
	}
	public String getPassword() {
		return password;
	}
	
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	
	
	public void setStatus(String status) {
		this.status=status;
	}
	public String getStatus() {
		return status;
	}
	
	
	public void setSalary(double salary) {
		this.salary=salary;
	}
	public double getSalary() {
		return salary;
	}
	
	
	public void setBirthday(Date birthday) {
		this.birthday=birthday;
	}
	public Date getBirthday() {
		return birthday;
	}
	
	
	public void setPhone(String sector) {
		this.sector=sector;
	}
	public String getPhone() {
		return sector;
	}
	
	
	public void setEmail(String email) {
		this.email=email;
	}
	public String getEmail() {
		return email;
	}
	
	
	
	
	
	
	
}
