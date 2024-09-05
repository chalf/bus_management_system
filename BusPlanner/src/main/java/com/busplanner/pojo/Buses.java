/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "buses")
@NamedQueries({
    @NamedQuery(name = "Buses.findAll", query = "SELECT b FROM Buses b"),
    @NamedQuery(name = "Buses.findByBusId", query = "SELECT b FROM Buses b WHERE b.busId = :busId"),
    @NamedQuery(name = "Buses.findByBusNumber", query = "SELECT b FROM Buses b WHERE b.busNumber = :busNumber")})
@JsonIgnoreProperties({"routeId", "schedulesSet"})
public class Buses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bus_id")
    private Integer busId;
    @Basic(optional = false)
    @NotNull
    @NotBlank(message = "{resources.bus.notnull}")
    @Size(min = 1, max = 20)
    @Column(name = "bus_number")
    private String busNumber;
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

    public Buses(Integer busId, String busNumber) {
        this.busId = busId;
        this.busNumber = busNumber;
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

    public Routes getRouteId() {
        return routeId;
    }

    public void setRouteId(Routes routeId) {
        this.routeId = routeId;
    }

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
