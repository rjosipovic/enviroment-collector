/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.enviroment.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author roman
 */
@Entity
@Table(name = "mesurement", schema = "enviroment")
@NamedQueries({
    @NamedQuery(name = "getAllMesurments", query = "SELECT m FROM Mesurement m ORDER BY m.mesureTime asc"),
    @NamedQuery(name = "getLastHourMesurments", query = "SELECT m FROM Mesurement m WHERE m.mesureTime>=:start AND m.mesureTime<:end ORDER BY m.mesureTime asc")
})
public class Mesurement implements Serializable {

    private static final long serialVersionUID = 20170819L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mesurment_seq")
    @SequenceGenerator(name = "mesurment_seq", sequenceName = "hibernate_sequence", schema = "enviroment", allocationSize = 1)
    private Long id;
    
    @Column(name = "mesure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mesureTime;
    
    @Column(name = "temperature")
    private Float temperature;
    
    @Column(name = "presure")
    private Float presure;
    
    @Column(name = "humidity")
    private Float humidity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMesureTime() {
        return mesureTime;
    }

    public void setMesureTime(Date mesureTime) {
        this.mesureTime = mesureTime;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getPresure() {
        return presure;
    }

    public void setPresure(Float presure) {
        this.presure = presure;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesurement)) {
            return false;
        }
        Mesurement other = (Mesurement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }    
}
