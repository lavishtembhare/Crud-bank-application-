import java.time.LocalDate;
import java.time.Period;

public class BankAccount {
private String acc_no;
private String holder_name;
private LocalDate dob;
private double balance;
private AccountCategory category;
public BankAccount(String acc_no, String holder_name, LocalDate dob, double balance, AccountCategory category) {
	super();
	this.acc_no = acc_no;
	this.holder_name = holder_name;
	this.dob = dob;
	this.balance = balance;
	this.category = category;
}
public String getAcc_no() {
	return acc_no;
}
public String getHolder_name() {
	return holder_name;
}
public LocalDate getDob() {
	return dob;
}
public double getBalance() {
	return balance;
}
public AccountCategory getCategory() {
	return category;
}
}