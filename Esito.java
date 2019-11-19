import java.io.Serializable;

public class Esito implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomeFile;
	private int numeroDiRighe;
	
	public Esito(String nomeFile, int numeroDiRighe) {
		this.nomeFile=nomeFile;
		this.numeroDiRighe=numeroDiRighe;
	}
	
	public String getNomeFile(){return nomeFile;};
	public int getNumeroDiRighe(){return numeroDiRighe;};
	
	//public void setNomeFile(String nome) {nomeFile=nome;};
	//public void setNumeroDiRighe(int numero) {numeroDiRighe=numero;};
}
