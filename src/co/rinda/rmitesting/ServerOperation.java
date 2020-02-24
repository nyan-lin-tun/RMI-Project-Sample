package co.rinda.rmitesting;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServerOperation extends UnicastRemoteObject implements RMIInterface {
	
	private static final long serialVersionID = 1L;
	
	protected ServerOperation() throws RemoteException{
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public String helloTo(String name) throws RemoteException {
		// TODO Auto-generated method stub
		System.err.println(name+ " is trying to contact!");
		return "Server says hello to " + name;
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

}
