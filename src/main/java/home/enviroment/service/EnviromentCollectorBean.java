/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.enviroment.service;

import home.enviroment.bean.MesurementHandlerBean;
import home.enviroment.entity.Mesurement;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author roman
 */
@Path(value = "collector")
@Stateless
public class EnviromentCollectorBean {

    private static final Logger LOGGER = Logger.getLogger(EnviromentCollectorBean.class.getName());
    
    @EJB
    private MesurementHandlerBean mesurementHandlerBean;
    
    @PostConstruct
    private void construct() {
        LOGGER.log(Level.FINER, "EnviromentCollectorBean created");
    }
    
    @GET
    public String monitor() {
        return "Home EnviromentCollector is running";
    }
    
    @GET
    @Path("temperature")
    public String mesureTemperature(@QueryParam("value")String temperature) {
    	LOGGER.log(Level.FINE, String.format("Received temperature mesurement [%s]", temperature));
    	mesurementHandlerBean.storeTemperature(parseFloat(temperature));
    	return String.format("Temperature[%s]", temperature);
    }
    
    @GET
    @Path("pressure")
    public String mesurePressure(@QueryParam("value")String pressure) {
    	LOGGER.log(Level.FINE, String.format("Received pressure mesurement [%s]", pressure));
    	mesurementHandlerBean.storePressure(parseFloat(pressure));
    	return String.format("Pressure[%s]", pressure);    	
    }
    @GET
    @Path("humidity")
    public String mesureHumidity(@QueryParam("value")String humidity) {
    	LOGGER.log(Level.FINE, String.format("Received humidity mesurement [%s]", humidity));
    	mesurementHandlerBean.storeHumidity(parseFloat(humidity));
    	return String.format("Humidity[%s]", humidity);    	
    }
    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMesurments() {
    	LOGGER.log(Level.FINE, "Entering getAllMesurements ...");
        List<Mesurement> allMesurments = mesurementHandlerBean.getAllMesurments();
        return Response.ok(allMesurments).build();
    }
    @GET
    @Path("getLastLimit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastMesurments(@QueryParam("limit")int limit) {
    	String logMsg = MessageFormat.format("Enetering getLastMesurements, limit={0}", limit);
    	LOGGER.log(Level.INFO, logMsg);
        List<Mesurement> limitMesurments = mesurementHandlerBean.getlLastMesurements(limit);
        return Response.ok(limitMesurments).build();
    }
    
    @GET
    @Path("getLastHour")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastMesurements() {
    	LOGGER.log(Level.INFO, "Entering getLastMesurements ...");
        List<Mesurement> lastHourMesurements = mesurementHandlerBean.getLastHourMesurments();
        return Response.ok(lastHourMesurements).build();    	
    }

    private Float parseFloat(String value) {
    	Float parsedValue = null;
    	try{
    		parsedValue = Float.parseFloat(value);
    	}catch(NumberFormatException e) {
    		LOGGER.log(Level.WARNING, String.format("Wrong data format, expecting number, received [%s]", value));
    	}
    	return parsedValue;
    }
    
    @PreDestroy
    private void remove() {
        LOGGER.log(Level.FINER, "Removing EnviromentCollectorBean");
    }
}
