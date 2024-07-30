import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class BankApp {
public static void main(String[] args) {
	Bank b=new Bank();
	Scanner scanner=new Scanner(System.in);
	DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	while(true) {
		System.out.println("\nBank Application");
        System.out.println("1. Create Account");
        System.out.println("2. Delete Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Update Account Holder Name");
        System.out.println("6. Display Accounts");
        System.out.println("7. Exit");
        
        System.out.println("Enter your choice: ");
        String choice=scanner.nextLine();
        
        switch(choice) {
        case "1":
        	System.out.println("Enter account number: ");
        	String acc_no=scanner.nextLine();
        	System.out.println("Enter holder name: ");
        	String holder_name=scanner.nextLine();
        	System.out.println("Enter date of birth (dd/MM/yyyy): ");
        	String dobs=scanner.nextLine();
        	LocalDate dob=null;
        	try {
        		dob=LocalDate.parse(dobs, formatter);
        	}catch(DateTimeParseException e) {
        			System.out.println("Invalid date format");
        			break;
        	}
        	System.out.println("Enter Initial deposit: ");
        	double initiald=scanner.nextDouble();
        	scanner.nextLine();
        	
        	System.out.println("Select category: ");
        	for(AccountCategory category:AccountCategory.values()) {
        		System.out.println(category.ordinal()+1+". "+category);
        	}
        	System.out.println("Enter the choice: ");
        	int categorychoice=scanner.nextInt();
        	scanner.nextLine();
        	
        	AccountCategory category;
        	try {
        		category=AccountCategory.values()[categorychoice-1];
        	}catch(ArrayIndexOutOfBoundsException e) {
        		System.out.println("Invalid choice");
        		break;
        	}
        	b.createAccount(acc_no, holder_name, dob, initiald, category);
        	break;
        	
        case "2":
        	System.out.println("Enter account number to delete: ");
        	acc_no=scanner.nextLine();
        	b.deleteAccount(acc_no);
        	break;
        	
        case "3":
        	System.out.println("Enter account number: ");
        	acc_no=scanner.nextLine();
        	System.out.println("Enter amount to deposit: ");
        	double depositAmount=scanner.nextDouble();
        	scanner.nextLine();
        	b.depositAtAccount(acc_no, depositAmount);
        	break;
        	
        case "4":
        	System.out.println("Enter account no. : ");
        	acc_no=scanner.nextLine();
        	System.out.println("Enter withdraw amount: ");
        	double witdrawAmount=scanner.nextDouble();
        	scanner.nextLine();
        	b.withdrawAmount(acc_no, witdrawAmount);
        	break;
        	
        case "5":
        	System.out.println("Enter account number to update: ");
        	acc_no=scanner.nextLine();
        	System.out.println("Enter new holder name; ");
        	String newHolderName=scanner.nextLine();
        	b.updateAccount(acc_no, newHolderName);
        	break;
        	
        case "6":
        	b.displayAccount();
        	break;
        	
        case "7":
        	System.out.println("Exiting Application");
        	scanner.close();
        	return;
        	
        default:
        	System.out.println("Invalid choice");
        	}
	}
}
}
