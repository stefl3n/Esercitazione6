import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemOp extends Remote {
	public int conta_righe(String nomeFileRemoto, int limiteInferioreParole) throws RemoteException;
	public Esito elimina_riga(String nomeFileRemoto, int rigaDaEliminare) throws RemoteException;
	
}
