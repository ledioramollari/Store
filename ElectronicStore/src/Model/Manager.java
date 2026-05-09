package Model;

import java.sql.Date;

public class Manager extends Employee {
	public Manager(String username,String password,String name,String status,
			double salary,Date birthday, String sector,String email)
	{
		super( username, password, name, status,salary, birthday,  sector,email);

	}
	
}
