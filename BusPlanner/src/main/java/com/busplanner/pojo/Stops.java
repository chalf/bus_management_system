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
@Table(name = "stops")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stops.findAll", query = "SELECT s FROM Stops s"),
    @NamedQuery(name = "Stops.findByStopId", query = "SELECT s FROM Stops s WHERE s.stopId = :stopId"),
    @NamedQuery(name = "Stops.findByStopName", query = "SELECT s FROM Stops s WHERE s.stopName = :stopName"),
    @NamedQuery(name = "Stops.findByLatitude", query = "SELECT s FROM Stops s WHERE s.latitude = :latitude"),
    @NamedQuery(name = "Stops.findByLongitude", query = "SELECT s FROM Stops s WHERE s.longitude = :longitude"),
    @NamedQuery(name = "Stops.findByAddress", query = "SELECT s FROM Stops s WHERE s.address = :address"),
    @NamedQuery(name = "Stops.findByCreatedAt", query = "SELECT s FROM Stops s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Stops.findByUpdatedAt", query = "SELECT s FROM Stops s WHERE s.updatedAt = :updatedAt")})
public class Stops implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stop_id")
    private Integer stopId;
    @Basic(optional = false)
    @Column(name = "stop_name")
    private String stopName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Basic(optional = false)
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stopId")
    private Set<Routestops> routestopsSet;

    public Stops() {
    }

    public Stops(Integer stopId) {
        this.stopId = stopId;
    }

    public Stops(Integer stopId, String stopName, BigDecimal latitude, BigDecimal longitude, String address) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public Integer getStopId() {
        return stopId;
    }

    public void setStopId(Integer stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @XmlTransient
    public Set<Routestops> getRoutestopsSet() {
        return routestopsSet;
    }

    public void setRoutestopsSet(Set<Routestops> routestopsSet) {
        this.routestopsSet = routestopsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stopId != null ? stopId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stops)) {
            return false;
        }
        Stops other = (Stops) object;
        if ((this.stopId == null && other.stopId != null) || (this.stopId != null && !this.stopId.equals(other.stopId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.busplanner.pojo.Stops[ stopId=" + stopId + " ]";
    }
    
}
