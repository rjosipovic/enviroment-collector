package home.enviroment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "humidity", schema = "enviroment")
public class Humidity implements Serializable {
	
	private static final long serialVersionUID = 20190106L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hum_seq_gen")
    @SequenceGenerator(name = "hum_seq_gen", sequenceName = "humidity_sequence", schema = "enviroment", allocationSize = 50)
    private Long id;

    @Column(name = "mesure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mesureTime;
    
    @Column(name = "value")
    private Float value;

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

    public Float getValue() {
        return value;
    }

    public void setValue(Float humidity) {
        this.value = humidity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Mesurement)) {
            return false;
        }
        Humidity other = (Humidity) object;
        if(this.id != null && other.id != null) {
        	return this.id == other.id;
        } else {
            return false;        	
        }
    }
}
