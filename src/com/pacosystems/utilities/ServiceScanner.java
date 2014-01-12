package com.pacosystems.utilities;

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
import java.util.Date;

import org.json.*;

public class ServiceScanner
{

	// default port
	public static final int QUIZZAPP_PORT = 8080;
	
	private String ip;
	private String payload;
	
	/**
	 * We provide the IP
	 * 
	 * @param ip
	 * @return
	 */
	public ServerDetails findListeningServer (String ip)
	{
		ServerDetails sd = new ServerDetails();
		sd.setPort(QUIZZAPP_PORT);
		sd.setClientIp(ip);
		sd.setServerIp(this._findListeningServer(ip));
		sd.setQuizzappServer(this.payload);
		sd.setSSID("DAVE_WOZZ_WERE");
		
		return sd;
	}
	
	/**
	 * We let the util resolve the IP
	 * 
	 * @return
	 */
	public ServerDetails findListeningServer ()
	{
		String ip = this.getIp();
		ServerDetails sd = new ServerDetails();
		sd.setPort(QUIZZAPP_PORT);
		sd.setClientIp(ip);
		sd.setServerIp(this._findListeningServer(ip));
		sd.setQuizzappServer(this.payload);
		sd.setSSID("DAVE_WOZZ_WERE");
		
		return sd;
	}
	
	/**
	 * Returns the listening QuizzApp server by scanning the subnet, and making a request to any listening ports for a friendly response
	 * 
	 * @return
	 */

	private String _findListeningServer(String ip)
	{
		String listeningServerIp = "";
		
		System.out.println ("resolved local ip = " + ip);
			
		if (!ip.isEmpty())
		{
			ArrayList<String> servers = this.serverList(ip);
			
			if (servers.size() > 0)
			{
				
				for (int i = 0; i < servers.size(); i++)
				{
					String testIp = servers.get(i);
					
					boolean result = this.testPort(testIp, ServiceScanner.QUIZZAPP_PORT);
					
					//
					if (result)
					{
						
						//try and make a http connection to the host
						boolean reachable = this.testUrl("http://" + testIp + ":" + ServiceScanner.QUIZZAPP_PORT + "/network/test");
						
						// we found a listening host
						if (reachable)
						{
							listeningServerIp = testIp;
							//
							//System.out.println("usable host is: " + listeningServerIp);// + " (data: "+ scanner.payload + ")");
							// break out of the loop
							break;
						}
					}
				}
			}
		} else
		{
			System.out.println ("no ip was resolved!");
		}
		
		return listeningServerIp;
	}
		
		
//	public String findListeningServer()
//	{
//		String listeningServerIp = null;
//		this.ip = this.getIp();
//			
//		ArrayList<String> servers = this.serverList(ip);
//		
//		if (servers.size() > 0)
//		{
//			
//			for (int i = 0; i < servers.size(); i++)
//			{
//				String testIp = servers.get(i);
//				
//				boolean result = this.testPort(testIp, ServiceScanner.QUIZZAPP_PORT);
//				
//				//
//				if (result)
//				{
//					
//					//try and make a http connection to the host
//					boolean reachable = this.testUrl("http://" + testIp + ":" + ServiceScanner.QUIZZAPP_PORT + "/network/test");
//					
//					// we found a listening host
//					if (reachable)
//					{
//						listeningServerIp = testIp;
//						//
//						//System.out.println("usable host is: " + listeningServerIp);// + " (data: "+ scanner.payload + ")");
//						// break out of the loop
//						break;
//					}
//				}
//			}
//		}
//		
//		return listeningServerIp;
//	}
	
	

	/**
	 * Try and make a connection to the listener service - this will write the payload returned to this.payload
	 * so it can be extracted later if required
	 * 
	 * @param testURL
	 * @return
	 */
	private boolean testUrl(String testURL)
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
        			//System.out.println(jsonPayload.get("quizzapp"));
        		} catch (JSONException je)
        		{
        			//System.out.println(je.getMessage());
        		} catch (Exception e)
        		{
        			//System.out.println(e.getMessage());
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
	private ArrayList<String> serverList(String ip)
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
	
	/**
	 * Resolves the client's IP address (we can then determine the subnet to scan)
	 * 
	 * @return
	 */
	public String getIp()
	{

		String ip = "";
		
		// TODO Auto-generated method stub
		try
		{
			String hostName = InetAddress.getLocalHost().getHostName();
			InetAddress addrs[] = InetAddress.getAllByName(hostName);
			
			//
			for (InetAddress addr: addrs) 
			{
				
				System.out.println ("addr.getHostAddress() = " + addr.getHostAddress());
				System.out.println ("addr.getHostName() = " + addr.getHostName());
				
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
	
	
	/**
	 * Tests the port to see if it's reachable
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	private boolean testPort(String ip, int port)
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

		} catch (IOException e)
		{
			//e.printStackTrace();
		}
		
		return false;
	}
}
