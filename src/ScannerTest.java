
import java.util.ArrayList;
import java.util.Date;

public class ScannerTest
{

	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// new instance of service scanner
		ServiceScanner scanner = new ServiceScanner();
		
		int port 	= ServiceScanner.QUIZZAPP_PORT;
		String ip 	= scanner.findListeningServer();
		
		System.out.println("listening server: " + ip + " (port: " + Integer.toString(port) + ", payload " + scanner.payload + ")");
	}
}
