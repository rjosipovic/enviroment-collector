package home.enviroment.service.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnviromentMonitorClient {
	
	private static final String SERVER_NAME = "rpi";
	private static final int SERVER_PORT = 1234;
	
	private static final Logger LOG = Logger.getLogger(EnviromentMonitorClient.class.getName());
	
	private Socket socket;
	private String status;
	
	private void connect() {
		LOG.fine("About to connect");
		try{
			socket = new Socket(SERVER_NAME, SERVER_PORT);
		} catch (UnknownHostException e) {
			LOG.log(Level.SEVERE, e.getMessage());
			status = String.format("Unable  to determine application status. Reason: %s", e.getMessage());
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage());
			status = String.format("Unable  to determine application status. Reason: %s", e.getMessage());
		}
		if(socket != null && socket.isConnected()) {
			LOG.log(Level.INFO, "Connection established: {0}", socket);
		}
	}
	
	private void sendData() {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
				String line = reader.readLine();
				LOG.info(line);
				status = line;
		}catch(IOException e) {
			LOG.log(Level.SEVERE, e.getMessage());
			status = String.format("Unable  to determine application status. Reason: %s", e.getMessage());
		}
	} 
	
	private void disconnect() {
		LOG.fine("About to disconnect");
		if(socket != null && !socket.isClosed()) {
			LOG.log(Level.FINE, "Disconnecting: {0}", socket);
			try {
				socket.close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, e.getMessage());
			}
		}
		if(socket.isClosed()) {
			LOG.fine("Connection closed");
		}
	}
	
	public String monitor() {
		connect();
		if(socket != null && socket.isConnected()) {
			sendData();
			disconnect();
		}
		LOG.log(Level.INFO, String.format("Application status: %s", status));
		return status;
	}
}
