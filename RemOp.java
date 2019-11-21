import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemOp extends Remote {
	
	int conta_righe(String fileName, int numParole) throws RemoteException, FileNotFoundException, IOException;
	Esito elimina_riga(String fileName,int riga) throws RemoteException, IOException;
	
}
