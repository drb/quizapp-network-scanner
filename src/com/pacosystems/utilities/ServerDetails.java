package com.pacosystems.utilities;

public class ServerDetails
{
	private int port = 0;
	private String clientIp = "UNRESOLVED_CLIENT_IP";
	private String serverIp = "UNRESOLVED_SERVER_IP";
	private String ssid = "UNRESOLVED_SSID";
	private String quizzapServer = "{}";
	
	public void setClientIp(String ip){
		this.clientIp = ip;
	}
	
	public String getClientIp()
	{
		return this.clientIp;
	}
	
	public void setServerIp(String ip){
		this.serverIp = ip;
	}
	
	public String getServerIp()
	{
		return this.serverIp;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	public int getPort()
	{
		return this.port;
	}
	
	public void setSSID(String ssid)
	{
		this.ssid = ssid;
	}
	
	public String getSSID()
	{
		return this.ssid;
	}
	
	public void setQuizzappServer(String server)
	{
		this.quizzapServer = server;
	}
	
	public String getQuizzappServer()
	{
		return this.quizzapServer;
	}
}
