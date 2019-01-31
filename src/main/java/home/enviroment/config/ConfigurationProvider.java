package home.enviroment.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.PreRemove;

@Startup
@Singleton
public class ConfigurationProvider {
	
	private static final Logger LOG = Logger.getLogger(ConfigurationProvider.class.getName());
	private static final String PROPERTIES_FILE = "enviroment-collector.properties";
	
	private Properties properties = new Properties();
	
	@PostConstruct
	private void construct() {
		LOG.log(Level.FINE, String.format("%s created", ConfigurationProvider.class.getName()));
		loadProperties();
	}
	
	@Schedule(second = "*/10",minute = "*", hour = "*", persistent = false)
	private void sheduleReload() {
		loadProperties();
	}
	
	@Lock(LockType.READ)
	public String getProperty(String propName) {
		return properties.getProperty(propName);
	}
	
	@Lock(LockType.WRITE)
	private void loadProperties() {
		LOG.log(Level.FINE, String.format("About to load properties from file [%s]", PROPERTIES_FILE));
		try {
			InputStream input = ConfigurationProvider.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
			properties.load(input);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error in loading properties from file", e);
		}
		LOG.log(Level.FINE, properties.toString());
	}
	
	@PreRemove
	private void remove() {
		LOG.log(Level.FINE, String.format("Removing %s", ConfigurationProvider.class.getName()));
	}
}
