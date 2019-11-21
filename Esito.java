import java.io.Serializable;

/**
 * 
 */

/**
 * @author root
 *
 */
public class Esito implements Serializable {
	String fileName;
	int righe;
	public Esito(String fileName, int righe) {
		
		this.fileName = fileName;
		this.righe = righe;
		
		
	}
	public String getFileName() {
		return fileName;
	}
	private void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getRighe() {
		return righe;
	}
	private void setRighe(int righe) {
		this.righe = righe;
	}
	
	public String toString() {
		
		return fileName + " " + righe;
		
	}
	
	
	
}
