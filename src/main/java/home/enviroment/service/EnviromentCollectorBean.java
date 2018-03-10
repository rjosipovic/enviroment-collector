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
    @Path("mesure")
    public String mesure(
            @QueryParam("temperature")String temperature,
            @QueryParam("presure")String presure,
            @QueryParam("humidity")String humidity) {
        String logMsg = MessageFormat.format("Temp: {0}, Presure: {1}, Humidity: {2}", temperature, presure, humidity);
        LOGGER.log(Level.INFO, logMsg);
        mesurementHandlerBean.storeMesurements(temperature, presure, humidity);
        return "mesurement recieved";
    }
    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMesurments() {
    	LOGGER.log(Level.INFO, "Entering getAllMesurements ...");
        List<Mesurement> allMesurments = mesurementHandlerBean.getAllMesurments();
        return Response.ok(allMesurments).build();
    }
    
    @GET
    @Path("getLast")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastMesurements() {
    	LOGGER.log(Level.INFO, "Entering getLastMesurements ...");
        List<Mesurement> lastHourMesurements = mesurementHandlerBean.getLastHourMesurments();
        return Response.ok(lastHourMesurements).build();    	
    }
    
    @PreDestroy
    private void remove() {
        LOGGER.log(Level.FINER, "Removing EnviromentCollectorBean");
    }
}
