/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.enviroment.bean;

import home.enviroment.entity.Mesurement;
import home.enviroment.entity.MesurementType;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author roman
 */
@Stateless
public class MesurementHandlerBean {

    private static final Logger LOGGER = Logger.getLogger(MesurementHandlerBean.class.getName());
    
    @PersistenceContext(unitName = "home.enviromentPU")
    private EntityManager em;
    
    @PostConstruct
    private void construct() {
        LOGGER.log(Level.FINER, "MesurementHandlerBean created");
    }
    
    private void storeMesurement(MesurementType type, Float value) {
    	Mesurement mesurement = new Mesurement();
    	mesurement.setMesureTime(new Date());
    	mesurement.getAttributes().put(type.getTag(), value.toString());
    	em.persist(mesurement);    	
    }
    
    public void storeTemperature(Float temperatureValue) {
    	storeMesurement(MesurementType.TEMPERATURE, temperatureValue);
    }

    public void storePressure(Float pressureValue) {
    	storeMesurement(MesurementType.PRESSURE, pressureValue);
    }
    
    public void storeHumidity(Float humidityValue) {
    	storeMesurement(MesurementType.HUMIDITY, humidityValue);
    }
    
    public List<Mesurement> getAllMesurments() {
        return em.createNamedQuery("getAllMesurments").getResultList();
    }
    
    public List<Mesurement> getlLastMesurements(int limit) {
    	return em.createNamedQuery("getAllMesurments").setMaxResults(limit).getResultList();
    }
    
    public List<Mesurement> getLastHourMesurments() {
        Query q = em.createNamedQuery("getLastHourMesurments");
        Calendar c = Calendar.getInstance();
        Date end = c.getTime();
        c.add(Calendar.HOUR, -1);
        Date start = c.getTime();

        q.setParameter("start", start);
        q.setParameter("end", end);
        return q.getResultList();
    }
    
    @PreDestroy
    private void remove()  {
        LOGGER.log(Level.FINER, "Removing MesurementHandlerBean");
    }
}
