
import java.util.ArrayList;

public class ScannerTest
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		String ip = ServiceScanner.getIp();
		
		//
		if (!ip.equals(""))
		{
			ArrayList<String> servers = ServiceScanner.serverList(ip);
			
			if (servers.size() > 0)
			{
				
				for (int i = 0; i < servers.size(); i++)
				{
					String testIp = servers.get(i);
					ServiceScanner scanner = new ServiceScanner();
					
					boolean result = scanner.testPort(testIp, 9000);
					//
					if (result)
					{
						
						//try and make a http connection to the host
						boolean reachable = scanner.testUrl("http://" + testIp + ":9000/client/test");
						//System.out.println("reachable test url " + testIp + " " + reachable);
						if (reachable)
						{
							System.out.println("usable host is: " + testIp + " (data: "+ scanner.payload + ")");
							// break out of the loop
							break;
						}
					}
				}
				System.out.println("done scanning");
			}
		}
	}
}
