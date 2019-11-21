import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
	
	public static void main (String args[]) {
		
		if(args.length != 2) {
			
			System.out.println("Errore argomenti");
			System.exit(1);
			
		}
		
		
		int port = Integer.parseInt(args[1]);
		String RegistryHost = args[0];
		String nomeServizio =  "RemOp";
		
		String nomeCompleto = "//"+RegistryHost+":"+port+"/"+nomeServizio;
		RemOp serverRMI = null;
		try {
			
			serverRMI = (RemOp) Naming.lookup(nomeCompleto);
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			
			// TODO Da Gestire
			e.printStackTrace();
		}
		
		String optionString,fileName;
		Scanner scan = new Scanner(System.in);
		System.out.println("Inserisci il nome del file remoto da modificare:");
		try {
			while((fileName = scan.nextLine()) != null) {
				try {
					
						
						
						System.out.println("Opzioni:");
						System.out.println("-Conta Righe (C)");
						System.out.println("-Elimina righe (E)");
						optionString = scan.nextLine();
						if(optionString == null) System.exit(1);
						switch (optionString) {
						case "C":
							int numP,count = 0;
							System.out.println("Inserisci il numero di parole che devono avere le frasi");
							numP = scan.nextInt();
							scan.nextLine();
							
							count = serverRMI.conta_righe(fileName, numP);
							
						
							System.out.println("Il file "+fileName+" contiene "+count+" righe con più di "+numP+" parole");
							break;
						case "E":
							int numR;
							Esito esito = null;
							System.out.println("Inserisci il numero della riga da eliminare");
							numR = scan.nextInt();
							scan.nextLine();
							
							esito = serverRMI.elimina_riga(fileName, numR);
							
						
							System.out.println(esito.toString());
							break;
						default:
							System.out.println("Opzione inserita non esistente");
							break;
						
						}
					
				} catch (RemoteException e) {
					System.out.println(e.getLocalizedMessage());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getLocalizedMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getLocalizedMessage());
				}
				System.out.println("Inserisci il nome del file remoto da modificare:");
				
			}
		} catch (NoSuchElementException e) {
			
		}
	}

}
