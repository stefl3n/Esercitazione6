import java.io.*;
import java.rmi.*;
import java.util.StringTokenizer;

public class Client {
	
	public static void main(String args[]) throws IOException
	{
		String buffer, service, nomeFile=null;
		int intero=-1;
		Esito esito=null;
		int intEsito=-1;
		StringTokenizer strtk;
		BufferedReader stdIn=new BufferedReader(new InputStreamReader(System.in));
		final int REGISTRYPORT = 1099;
		String registryHost = null;
		String serviceName = "OperazioniSuFile";
		
		
		
		System.out.println("Specificare tipo di servizio   <C||E>  (<contaRighe||eliminaRighe>)"+'\n');

		while((service=stdIn.readLine())!=null)
		{
			intero=-1;
			buffer=null;
			try {
				if (args.length != 1)
				{
					System.out.println("Sintassi:...");
					System.exit(1);
				}
				registryHost = args[0];
				
				
				// Connessione al servizio RMI remoto
				String completeName = "//" + registryHost + ":" + REGISTRYPORT + "/" + serviceName;
				ServerImpl serverRMI = (ServerImpl) Naming.lookup (completeName);
				if(service.equalsIgnoreCase("C"))
				{
					System.out.println("Specificare nome del file e numero di parole nel formato <nomeFile,numeroParole>"+'\n');
					buffer=stdIn.readLine();
					strtk=new StringTokenizer(buffer,",");
					nomeFile=strtk.nextToken();
					intero=Integer.parseInt(strtk.nextToken());
					
					
					intEsito=serverRMI.conta_righe(nomeFile, intero);
					System.out.println("Richiesta: "+service+' '+nomeFile+' '+intero+'\n');
					System.out.println("Esito: "+intEsito+" righe eliminate"+'\n');
				}
				else if(service.equalsIgnoreCase("E"))
				{
					System.out.println("Specificare nome del file e numero della riga <nomeFile,numeroRiga>"+'\n');
					buffer=stdIn.readLine();
					strtk=new StringTokenizer(buffer,",");
					nomeFile=strtk.nextToken();
					intero=Integer.parseInt(strtk.nextToken());
					
					esito=serverRMI.elimina_riga(nomeFile, intero);
				}
				else
				{
					System.out.println("Input non valido"+'\n');
				}
			}
			catch(RemoteException r)
			{
				r.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Errore nella lettura dell input"+'\n');
			}
			
			
			
			System.out.println("Specificare tipo di servizio   <C||E>  (<contaRighe||eliminaRighe>)"+'\n');
			
		}
	}

}
