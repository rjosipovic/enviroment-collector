package home.enviroment.service.monitor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class MonitorInvoker {
	
	private static final Logger LOG = Logger.getLogger(MonitorInvoker.class.getName()); 
	private EnviromentMonitorClient client;
	
	@PostConstruct
	private void construct() {
		LOG.log(Level.INFO, "MonitorInvoker constructed");
		client = new EnviromentMonitorClient();		
	}
	
	@Schedule(minute = "*/1", hour = "*", persistent = false)
	public void invoke() {
		String status = client.monitor();
		LOG.log(Level.INFO, String.format("Status: %s", status));
	}
	
	@PreDestroy
	private void remove() {
		LOG.log(Level.INFO, "Removing MonitorInvoker");
	}

}
