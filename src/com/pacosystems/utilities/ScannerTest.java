package com.pacosystems.utilities;

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
		
		// port is for debug
		int port = ServiceScanner.QUIZZAPP_PORT;
		
		// get the ip of the listening server on the connected subnet
		ServerDetails server = scanner.findListeningServer();
		
		//
		if (server.getQuizzappServer().isEmpty())
		{
			System.out.println("could not find a listening server using port " + Integer.toString(port));
		}else
		{
			// output the result
			System.out.println("listening quizzapp server: " + server.getServerIp() + " (port: " + Integer.toString(server.getPort()) + ", payload " + server.getQuizzappServer() + ")");	
		}
	}
}
