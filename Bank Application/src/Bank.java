import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Bank {
private static final String JDBC_URL="jdbc:mysql://localhost:3306/bankdb";
private static final String JDBC_USER="root";
private static final String JDBC_PASSWORD="";
private Connection con;
public Bank() {
	try {
		con=DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
		System.out.println("Connected");
	}catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Failed");
	}
}
public void createAccount(String acc_no,String holder_name,LocalDate dob,double initial_deposit,AccountCategory category) {
	 int age = calculateAge(dob);
	if(age<18) {
		System.out.println("You're Underage");
		return;
	}
	if(initial_deposit<1000) {
		System.out.println("Minimum balance must be 1000");
		return;
	}
	String sql="INSERT INTO BankAccount (acc_no, holder_name, dob, balance, category) VALUES (?, ?, ?, ?, ?)";
	try(PreparedStatement psmt=con.prepareStatement(sql)){
		psmt.setString(1, acc_no);
		psmt.setString(2, holder_name);
		psmt.setDate(3, Date.valueOf(dob));
		psmt.setDouble(4, initial_deposit);
		psmt.setString(5, category.name());
		
		int rowInserted=psmt.executeUpdate();
		if(rowInserted>0) {
			System.out.println("Account created successfully");
		}
	}catch(SQLException e) {
			System.out.println("Failed: "+e.getMessage());
		}
	}
public void deleteAccount(String acc_no) {
	String sql="DELETE FROM bankaccount WHERE acc_no = ?";
	try(PreparedStatement psmt=con.prepareStatement(sql)){
		psmt.setString(1, acc_no);
		int rowdelete=psmt.executeUpdate();
		if(rowdelete>0) {
				System.out.println("Account deleted");
		}else {
			System.out.println("Account does not exist");
		}
	}catch(SQLException e) {
		System.out.println("Failed: "+e.getMessage());
	}
}
public void depositAtAccount(String acc_no,double amount) {
	String sqlSelect="SELECT balance FROM bankaccount WHERE acc_no=?";
	String sqlUpdate="UPDATE bankaccount SET balance=? WHERE acc_no=?";
	
	try(PreparedStatement psmtSelect=con.prepareStatement(sqlSelect)){
		psmtSelect.setString(1, acc_no);
		ResultSet rs=psmtSelect.executeQuery();
		if(rs.next()) {
			double current_balance=rs.getDouble("balance");
			double new_balance=current_balance+amount;
			
			try(PreparedStatement psmtUpdate=con.prepareStatement(sqlUpdate)){
				psmtUpdate.setDouble(1, new_balance);
				psmtUpdate.setString(2, acc_no);
				
				int rowsUpdate=psmtUpdate.executeUpdate();
				if(rowsUpdate>0) {
					System.out.println("New Balance: "+new_balance);
					System.out.println("Deposited: "+amount);
				}
			}
		}else {
			System.out.println("Account does not exist");
		}
	}catch(SQLException e) {
			System.out.println("Failed: "+e.getMessage());
	}
}
public void withdrawAmount(String acc_no,double amount) {
	String sqlSelect="SELECT balance FROM bankaccount WHERE acc_no = ?";
	String sqlUpdate="UPDATE bankaccount SET balance = ? WHERE acc_no = ?";
	
	try(PreparedStatement psmtSelect=con.prepareStatement(sqlSelect)){
		psmtSelect.setString(1, acc_no);
		ResultSet rs=psmtSelect.executeQuery();
		
		if(rs.next()) {
			double current_balance=rs.getDouble("balance");
			if(current_balance-amount<1000) {
				System.out.println("Insufficient balance. Minimum balance of 1000 must be maintained.");
                return;
			}
			double new_balance=current_balance-amount;
			
			try(PreparedStatement psmtUpdate=con.prepareStatement(sqlUpdate)){
				psmtUpdate.setDouble(1, new_balance);
				psmtUpdate.setString(2, acc_no);
				
				int rowsUpdated=psmtUpdate.executeUpdate();
				if(rowsUpdated>0) {
					System.out.println("Withdrew: "+amount);
					System.out.println("New balance: "+new_balance);
				}
			}
		}else {
			System.out.println("Account does not exist");
		}
	}catch(SQLException e) {
		System.out.println("Failed: "+e.getMessage());
	}
}
public void updateAccount(String acc_no,String newHolderName) {
	String sql="UPDATE bankaccount SET holdername = ? WHERE acc_no = ?";
	
	try(PreparedStatement psmt=con.prepareStatement(sql)){
		psmt.setString(1, newHolderName);
		psmt.setString(2, acc_no);
		
		int rowUpdate=psmt.executeUpdate();
		if(rowUpdate>0) {
			System.out.println("Update Successful");
		}else {
			System.out.println("Account not found");
		}
	}catch(SQLException e) {
		System.out.println("Failed: "+e.getMessage());
	}
}
public void displayAccount() {
	String sql="SELECT * FROM bankaccount";
	
	try(Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(sql)){
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if(!rs.isBeforeFirst()) {
			System.out.println("No accounts available");
			return;
		}
		while (rs.next()) {
			String acc_no=rs.getString("acc_no");
			String holder_name=rs.getString("holder_name");
			LocalDate dob=rs.getDate("dob").toLocalDate();
			double balance=rs.getDouble("balance");
			String category=rs.getString("category");
			System.out.println("Account Number: "+acc_no);
			System.out.println("Holder: "+holder_name);
			System.out.println("Date of birth: "+dob);
			System.out.println("Balance: "+balance);
			System.out.println("Category: "+category);
		}
	}catch(SQLException e) {
		System.out.println("Failed: "+e.getMessage());
	}
}
private int calculateAge(LocalDate dob) {
	LocalDate today=LocalDate.now();
	return Period.between(dob, today).getYears();
}
} 	