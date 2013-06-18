import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.json.*;

public class ServiceScanner
{
	private String ip;
	public String payload;
	
	
	public boolean testUrl(String testURL)
	{
		URL url;
		try
		{
			url = new URL(testURL);
			
			URLConnection urlConn = url.openConnection();
			urlConn.setConnectTimeout(60);
			urlConn.setReadTimeout(60);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            
            while ((line = in.readLine()) != null)
            {
            	buffer.append(line);
            }
            in.close();
            
            // test the response from the server
            if (!buffer.toString().equals(""))
            {
            	this.payload = buffer.toString();
        		
        		// we can parse the info here to display to the client
        		try
        		{
        			JSONObject jsonPayload = new JSONObject(this.payload);
        			System.out.println(jsonPayload.get("server"));
        		} catch (JSONException je)
        		{
        			System.out.println(je.getMessage());
        		} catch (Exception e)
        		{
        			System.out.println(e.getMessage());
        		}
        		
        		return true;
            } else
            {
            	return false;
            }
		}
		catch (ConnectException e)
		{
			//e.printStackTrace();
		}
		catch (MalformedURLException e)
		{
			//e.printStackTrace();
		} catch (Exception e)
		{
			//e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	/**
	 * Defines an IP range based on the passed in IP's subnet
	 * 
	 * @param ip
	 */
	public static ArrayList<String> serverList(String ip)
	{
	    ArrayList<String> servers = new ArrayList<String>();
		
		String start = "", end = "";
	    ip = ip.substring(0, ip.lastIndexOf("."));
	    
	    start = ip + ".1";
	    end = ip + ".254";
	    
	    for (int i = 1; i <= 254; i++)
	    {
	    	servers.add(ip + "." + Integer.toString(i));
	    }
	    
	    return servers;
	}
	
	public static String getIp()
	{
		String ip = "UNKNOWN";
		
		// TODO Auto-generated method stub
		try
		{
			String hostName = InetAddress.getLocalHost().getHostName();
			InetAddress addrs[] = InetAddress.getAllByName(hostName);
			
			//
			for (InetAddress addr: addrs) 
			{
				
				/*System.out.println ("addr.getHostAddress() = " + addr.getHostAddress());
				System.out.println ("addr.getHostName() = " + addr.getHostName());
				System.out.println ("");*/
				if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) 
				{
					ip = addr.getHostAddress();
				}
			}
		}
		catch (UnknownHostException e1)
		{
			e1.printStackTrace();
		}
		
		return ip;
	}
	
	public boolean testPort(String ip, int port)
	{
		//System.out.println("Scanning " + ip + " on port " + port);
		boolean reachable;
		
		try
		{
			reachable = InetAddress.getByName(ip).isReachable(100);
			return reachable;
		}
		catch (UnknownHostException e)
		{
			//System.out.println(ip + " is not a reachable host");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e)
		{
			//e.printStackTrace();
		}
		
		return false;
	}
}
