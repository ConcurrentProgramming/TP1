import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class Logger {
	
	private FileOutputStream file;
	private PrintStream pw;

	public Logger(String nameFile) {
		try {
			file = new FileOutputStream(nameFile+".txt");
	        pw = new PrintStream(file);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	public void escribirInforme(String writefile) {
		pw.print(writefile);
	}

}
