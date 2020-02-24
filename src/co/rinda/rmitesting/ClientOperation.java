package co.rinda.rmitesting;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClientOperation {
	private static RMIInterface lookUp;
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		lookUp = (RMIInterface) Naming.lookup("rmi://127.0.0.1:8080/MyServer");
	
		
		ClientOperation.userInterface(lookUp);
		
	}
	
	private static void userInterface(RMIInterface lookUp) throws RemoteException {
		ClientOperation.createInterface(lookUp);
		
	}
	
	private static void createInterface(RMIInterface lookUp) throws RemoteException {
		// TODO Auto-generated constructor stub
		JFrame frame = new JFrame("Payment card detection system");
		JButton checkPaymentCard = new JButton("Identify payment card.");
		
		JPanel main = new JPanel(new BorderLayout());
		JPanel top = new JPanel(new BorderLayout());
		JPanel bottom = new JPanel(new BorderLayout());
		JList<String> currencyList;
		
		
		currencyList = new JList<String>(lookUp.getCurrencyList());
		
		checkPaymentCard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String response;
				Long toCheckInt;
				try {
					response = ClientOperation.askQuestionForCardIdentification(lookUp);
					toCheckInt = Long.parseLong(response);
	            	ClientOperation.detectCardNumber(Long.parseLong(response), lookUp);
	            	int userChoice = JOptionPane.showConfirmDialog(null, "Do you want to identify another card?", "Detect your credit card.", JOptionPane.YES_NO_OPTION);
	            	if (userChoice == 1) {
	            		JOptionPane.showMessageDialog(null, "Thanks for using our system");
	            	}else {
	            		do {
	            			String fromResponse = ClientOperation.askQuestionForCardIdentification(lookUp);
	            			ClientOperation.detectCardNumber(Long.parseLong(fromResponse), lookUp);
	            			userChoice = JOptionPane.showConfirmDialog(null, "Do you want to identify another card?", "Detect your credit card.", JOptionPane.YES_NO_OPTION);
	            		} while (userChoice == 0);
	            		JOptionPane.showMessageDialog(null, "Thanks for using our system");
	            	}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//            	
				
			}
		});
		top.add(currencyList, BorderLayout.NORTH);
		bottom.add(checkPaymentCard, BorderLayout.CENTER);
		main.setLayout(new BorderLayout(5,5));
		main.add(top, BorderLayout.NORTH);
		main.add(bottom, BorderLayout.SOUTH);
		frame.setContentPane(main);
		frame.setSize(300, 200);
		frame.setVisible(true);	
		
	}
	
	
	private static String askQuestionForCardIdentification(RMIInterface lookUp) throws RemoteException {
		String txt = JOptionPane.showInputDialog("Please enter your card number to identify your payment card.");
		
		do {
			if (txt.isEmpty()) {
				txt = JOptionPane.showInputDialog(null, "Card number cannot be blank. Please enter card number for identification.", JOptionPane.YES_OPTION);
			}else {
				System.out.println("in else don't know what to do");
				System.out.println("not empty and value is -> " + txt);
			}
		} while (txt.isEmpty());
		
		return txt;
	}
	
	private static void detectCardNumber(Long cardNumber, RMIInterface lookUp) throws HeadlessException, RemoteException {
		if (lookUp.isValidCardNumber(cardNumber)) {
			System.out.println("Card number is " + cardNumber);
			System.out.println("Card number to Strnig is " + cardNumber.toString());
			System.out.println("First char of string is " + cardNumber.toString().charAt(0));
			char firstCharString = cardNumber.toString().charAt(0);
			int startNumber = Character.getNumericValue(firstCharString);
			System.out.println("start number is " + startNumber);
			switch (startNumber) {
			case 4: {
				JOptionPane.showMessageDialog(null, "Your card is VISA card");
				break;
			}
			case 5: {
				JOptionPane.showMessageDialog(null, "Your card is Master card");
				break;
			}
			
			case 3: {
				char secondCharString = cardNumber.toString().charAt(1);
				int secondNumber = Character.getNumericValue(secondCharString);
				if (secondNumber == 7) {
					JOptionPane.showMessageDialog(null, "Your card is American Express");
					break;
				}else if (secondNumber == 5){ 
					JOptionPane.showMessageDialog(null, "Your card is JCB");
					break;
				}else {
					JOptionPane.showMessageDialog(null, "Your card is not valid");
					break;
				}	
			}
			case 6: {
				//Discover card
				JOptionPane.showMessageDialog(null, "Your card is Discover card");
				break;
			}
			default:
				JOptionPane.showMessageDialog(null, "Your card is invalid");
				
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "Your card is not valid.");
		}
	}
	
}
