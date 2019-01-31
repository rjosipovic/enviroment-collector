/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.enviroment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(name = "hstore", typeClass = AttributesUserType.class)
public class Mesurement implements Serializable {

    private static final long serialVersionUID = 20170819L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mesurement_seq")
    @SequenceGenerator(name = "mesurement_seq", sequenceName = "mesurement_sequence", schema = "enviroment", allocationSize = 50)
    private Long id;
    
    @Column(name = "mesure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mesureTime;

    @Column(name="attributes", columnDefinition = "hstore")
    @Type(type="hstore")
    private Map<String, String> attributes = new HashMap<>();
    
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

    public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mesurement other = (Mesurement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
