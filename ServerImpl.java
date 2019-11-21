import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.StringTokenizer;

public class ServerImpl extends UnicastRemoteObject implements RemOp {

	protected ServerImpl() throws RemoteException {
		super();
		
	}
	
	
	private int righeFile(String fileName) throws IOException {
		
		File file = new File(fileName);
		
		BufferedReader bd = new BufferedReader(new FileReader(file));
		int count = 0;
		while(bd.readLine()!= null) {
			
			count++;
			
		}
		bd.close();
		return count;
		
	}
	@Override
	public synchronized int conta_righe(String fileName, int numParole) throws FileNotFoundException, IOException {
		File file;
		int count = 0;
		if(!(file = new File(fileName)).exists() || numParole < 0) {
			
			System.out.println("File non esistente o numero di parole negativo");
			throw new RemoteException("File Non esistente");
			
		} else {
			
				if(!(Files.probeContentType(file.toPath()).equals("text/plain"))) {
						
						throw new RemoteException(file.getName()+" non è un file di testo");
						
					}else {
				
				BufferedReader rd = null;
				 
				rd = new BufferedReader(new FileReader(file));
					
				
				 String line = null;
				 
					while((line = rd.readLine()) != null) {
						 
						 StringTokenizer tok = new StringTokenizer(line);
						 if(tok.countTokens() >= numParole) {
							 
							 count++;
							 
						 }
						 
					 }
				
				 
				
				}
			
		}
		return count;
	}

	@Override
	public synchronized Esito elimina_riga(String fileName, int riga) throws IOException {
		
		File file;
		int count = 0;
		if(!((file = new File(fileName)).exists())) {
			
			throw new RemoteException("File Non esistente");
			
		}else if(!(Files.probeContentType(file.toPath()).equals("text/plain"))) {
				
				throw new RemoteException(file.getName()+" non è un file di testo");
				
			}else {
		
		
		
			count = 0;
		
		
		
		BufferedReader bd = null; PrintWriter bw = null;
		File fileTemp = new File("temp.txt");
		
		bd = new BufferedReader(new FileReader(file));
		bw = new PrintWriter(new FileWriter(fileTemp));
		
		
		int rigaCor = 0;
		String line;
		
		while((line = bd.readLine()) != null) {
				count++;
			if(count != riga) {
					
				bw.print(line+"\n");
				rigaCor++;
			}
				
		}
		bw.flush();
		bw.close();
		if(count < riga) {
			
			throw new RemoteException("Numero riga troppo elevato");
			
		}
		
		file.delete();
		fileTemp.renameTo(new File(fileName));
		
		
		return new Esito(fileName,rigaCor);
			}
		
	}
	
	public static void main(String args[]) {
		
		int port;
		
		if(args.length != 1) {
			
			System.out.println("Errore argomenti");
			System.exit(1);
			
		}
		
		port = Integer.parseInt(args[0]);
		String RegistryHost = "localhost";
		String nomeServizio = "RemOp";
		String nomeCompleto = "//"+RegistryHost+":"+port+"/"+nomeServizio;
		
		try {
			ServerImpl serverRMI = new ServerImpl();
			Naming.rebind(nomeCompleto, serverRMI);
		} catch (RemoteException e) {
			// TODO Da Gestire
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Da Gestire
			e.printStackTrace();
		}
	}
	
	
}
