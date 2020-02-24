package co.rinda.rmitesting;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;

public interface RMIInterface extends Remote{
	
	public boolean isValidCardNumber(long number) throws RemoteException;
	
	public DefaultListModel<String> getCurrencyList() throws RemoteException;
	
	
}
