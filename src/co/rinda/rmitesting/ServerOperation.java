package co.rinda.rmitesting;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.DefaultListModel;

public class ServerOperation extends UnicastRemoteObject implements RMIInterface {
	
	private static final long serialVersionID = 1L;
	
	protected ServerOperation() throws RemoteException{
		// TODO Auto-generated constructor stub
		super();
	}


	@Override
	public DefaultListModel<String> getCurrencyList() throws RemoteException {
		// TODO Auto-generated method stub
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		listModel.addElement("Currency rate.");
		listModel.addElement("1 USD = 1452 MMK");
		listModel.addElement("1 SGD = 1036 MMK");
		listModel.addElement("1 EUR = 1570 MMK");
		listModel.addElement("1 JPY = 1298 MMK");
		listModel.addElement("1 CAD = 1094 MMK");
		listModel.addElement("1 THB =   45 MMK");
		return listModel;
	} 

	
	
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(8080);
			Naming.rebind("rmi://127.0.0.1:8080/MyServer", new ServerOperation());
			System.err.println("Server Ready");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Return true if the card number is valid 
	@Override
    public boolean isValidCardNumber(long number) throws RemoteException  { 
       return (getSize(number) >= 13 &&  
               getSize(number) <= 16) &&  
               (prefixMatched(number, 4) ||  
                prefixMatched(number, 5) ||  
                prefixMatched(number, 37) ||  
                prefixMatched(number, 6)) &&  
              ((sumOfDoubleEvenPlace(number) +  
                sumOfOddPlace(number)) % 10 == 0); 
    } 
  
    // Get the result from Step 2 
    public static int sumOfDoubleEvenPlace(long number) 
    { 
        int sum = 0; 
        String num = number + ""; 
        for (int i = getSize(number) - 2; i >= 0; i -= 2)  
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2); 
          
        return sum; 
    } 
  
    // Return this number if it is a single digit, otherwise, 
    // return the sum of the two digits 
    public static int getDigit(int number) 
    { 
        if (number < 9) 
            return number; 
        return number / 10 + number % 10; 
    } 
  
    // Return sum of odd-place digits in number 
    public static int sumOfOddPlace(long number) { 
        int sum = 0; 
        String num = number + ""; 
        for (int i = getSize(number) - 1; i >= 0; i -= 2)  
            sum += Integer.parseInt(num.charAt(i) + "");         
        return sum; 
    } 
  
    // Return true if the digit d is a prefix for number 
    public static boolean prefixMatched(long number, int d) { 
        return getPrefix(number, getSize(d)) == d; 
    } 
  
    // Return the number of digits in d 
    public static int getSize(long d) { 
        String num = d + ""; 
        return num.length(); 
    } 
  
    // Return the first k number of digits from  
    // number. If the number of digits in number 
    // is less than k, return number. 
    public static long getPrefix(long number, int k) { 
        if (getSize(number) > k) { 
            String num = number + ""; 
            return Long.parseLong(num.substring(0, k)); 
        } 
        return number; 
    }

}
