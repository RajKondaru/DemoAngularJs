package com.rj.mobile.web.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;


public class Executer implements Runnable {
	/** The log. */
	private static final Logger log = Logger.getLogger(Executer.class);

	protected String userid;
	protected String projectId;
	protected String pageName;
	protected String browserName;
	protected String platformVersion;
	protected String platformName;
	protected String deviceID;
	protected String url;
	protected String version;
	protected String orientation;
	protected String MobileAutomationJarUrl;
	public Executer(String userid, String projectId,String pageName, String browserName,String version,String deviceID,  String platformName , String platformVersion,  String url, String orientation) throws IOException {
		this.userid= userid;
		this.projectId = projectId;
		this.pageName = pageName;
		this.browserName = browserName;
		this.platformVersion = platformVersion;
		this.platformName = platformName;
		this.deviceID=deviceID;
		this.url=url;
		this.version=version;
		this.orientation=orientation;
		Properties prop = new Properties();
		prop.load(Executer.class.getClassLoader().getResourceAsStream("config.properties"));
		MobileAutomationJarUrl = prop.getProperty("MobileAutomationJarUrl");
		log.info("Mobile Automation Jar Url >> " + MobileAutomationJarUrl);
	}
	@Override
	public void run() {
		try {
			Process p = Runtime.getRuntime().exec("cmd.exe /c start java -jar "+MobileAutomationJarUrl +" "+userid+" "+projectId+" "+pageName+" "+browserName+" " + deviceID +" "+platformName+" "+platformVersion+ " " + url+ " " + version+ " " +orientation);
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line="";
			String allLine="";
			int i=1;
			while((line=r.readLine()) != null){
				//System.out.println(i+". "+line);
				allLine=allLine+""+line+"\n";
				log.info("Appium logs >> " + allLine);
				if(line.contains("Console LogLevel: debug"))
					break;
				i++;
			}
			//System.out.println(allLine);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}

	}

}
