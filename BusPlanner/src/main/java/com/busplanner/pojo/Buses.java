/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "buses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buses.findAll", query = "SELECT b FROM Buses b"),
    @NamedQuery(name = "Buses.findByBusId", query = "SELECT b FROM Buses b WHERE b.busId = :busId"),
    @NamedQuery(name = "Buses.findByBusNumber", query = "SELECT b FROM Buses b WHERE b.busNumber = :busNumber"),
    @NamedQuery(name = "Buses.findByCapacity", query = "SELECT b FROM Buses b WHERE b.capacity = :capacity"),
    @NamedQuery(name = "Buses.findByCurrentLocationLat", query = "SELECT b FROM Buses b WHERE b.currentLocationLat = :currentLocationLat"),
    @NamedQuery(name = "Buses.findByCurrentLocationLong", query = "SELECT b FROM Buses b WHERE b.currentLocationLong = :currentLocationLong"),
    @NamedQuery(name = "Buses.findByLastUpdated", query = "SELECT b FROM Buses b WHERE b.lastUpdated = :lastUpdated")})
public class Buses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bus_id")
    private Integer busId;
    @Basic(optional = false)
    @Column(name = "bus_number")
    private String busNumber;
    @Basic(optional = false)
    @Column(name = "capacity")
    private int capacity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "current_location_lat")
    private BigDecimal currentLocationLat;
    @Column(name = "current_location_long")
    private BigDecimal currentLocationLong;
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    @ManyToOne
    private Routes routeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "busId")
    private Set<Schedules> schedulesSet;

    public Buses() {
    }

    public Buses(Integer busId) {
        this.busId = busId;
    }

    public Buses(Integer busId, String busNumber, int capacity) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.capacity = capacity;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getCurrentLocationLat() {
        return currentLocationLat;
    }

    public void setCurrentLocationLat(BigDecimal currentLocationLat) {
        this.currentLocationLat = currentLocationLat;
    }

    public BigDecimal getCurrentLocationLong() {
        return currentLocationLong;
    }

    public void setCurrentLocationLong(BigDecimal currentLocationLong) {
        this.currentLocationLong = currentLocationLong;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Routes getRouteId() {
        return routeId;
    }

    public void setRouteId(Routes routeId) {
        this.routeId = routeId;
    }

    @XmlTransient
    public Set<Schedules> getSchedulesSet() {
        return schedulesSet;
    }

    public void setSchedulesSet(Set<Schedules> schedulesSet) {
        this.schedulesSet = schedulesSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (busId != null ? busId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buses)) {
            return false;
        }
        Buses other = (Buses) object;
        if ((this.busId == null && other.busId != null) || (this.busId != null && !this.busId.equals(other.busId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.busplanner.pojo.Buses[ busId=" + busId + " ]";
    }
    
}
