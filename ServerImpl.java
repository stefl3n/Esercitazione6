import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements RemOp{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ServerImpl() throws RemoteException {
		super();
	}
	
	public static void main(String args[]) {
		final int REGISTRYPORT = 1099;//uso la porta di default
		String registryHost = "localhost";
		String serviceName = "OperazioniSuFile";
		try
		{ // Registrazione del servizio RMI
		String completeName = "//" + registryHost +
		":" + REGISTRYPORT + "/" + serviceName;
		ServerImpl serverRMI = new ServerImpl();
		
		Naming.rebind (completeName, serverRMI);
		} // try
		catch (Exception e){ 
			e.printStackTrace();
			System.exit(1);
			//bind fallito inutile tenere server attivo.
		}
	}
	
	public synchronized int conta_righe(String nomeFileRemoto, int limiteInferioreParole) throws RemoteException
	{
		int ctr=0;
		/*restituisce il numero delle righe che contengono un numero di
		parole maggiore dell’intero inviato; in caso di errore, solleva
		un’eccezione remota: ad esempio, se il file non è presente nel
		sistema o non è un file testo*/
		
		//come verificare che sia un file di testo?
		//forse readLine non va a buon fine?
		
		try {
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomeFileRemoto)));
			
			while((line=br.readLine())!=null) {
				if(line.split(" ").length > limiteInferioreParole) ctr++;
			}
			
			br.close();
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			throw new RemoteException();
		}
		catch(IOException ioe) {
			//non e'file di testo?
			throw new RemoteException();
		}
		return ctr;
	}
	
	//da finire
	public synchronized Esito elimina_riga(String nomeFileRemoto, int rigaDaEliminare) throws RemoteException
	{
		long numeroRighe;
		/*
		 • se il file esiste e se ha un numero di righe almeno pari all’intero
inviato dal cliente, restituisce l’esito dell’operazione, ovvero il nome
del file modificato e un intero corrispondente al numero di righe
presenti nel file modificato
In caso di errore, solleva un’eccezione remota: ad esempio, se il
file non è presente nel sistema, se non è un file testo o se ha meno
righe di quella della quale se ne richiede l’eliminazione.
		*/
		
		try {
			FileInputStream fis = new FileInputStream(nomeFileRemoto);
			InputStreamReader isr =new InputStreamReader(fis);
			BufferedReader br= new BufferedReader(isr);
			
			//come verificare che sia un file di testo?
			
			/*
			 * a fine stream devo chiudere e riaprire il file
			 * perche' IO pointer e' alla fine?
			 * */
			numeroRighe=br.lines().count();
			if(rigaDaEliminare>numeroRighe) {
				br.close();
				throw new RemoteException();
			}
			
		}
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			throw new RemoteException();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
			throw new RemoteException();
		}
		return null;
	}

	
}
