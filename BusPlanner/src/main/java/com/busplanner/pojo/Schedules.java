/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "schedules")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schedules.findAll", query = "SELECT s FROM Schedules s"),
    @NamedQuery(name = "Schedules.findByScheduleId", query = "SELECT s FROM Schedules s WHERE s.scheduleId = :scheduleId"),
    @NamedQuery(name = "Schedules.findByDepartureTime", query = "SELECT s FROM Schedules s WHERE s.departureTime = :departureTime"),
    @NamedQuery(name = "Schedules.findByArrivalTime", query = "SELECT s FROM Schedules s WHERE s.arrivalTime = :arrivalTime"),
    @NamedQuery(name = "Schedules.findByDayOfWeek", query = "SELECT s FROM Schedules s WHERE s.dayOfWeek = :dayOfWeek")})
public class Schedules implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "schedule_id")
    private Integer scheduleId;
    @Basic(optional = false)
    @Column(name = "departure_time")
    @Temporal(TemporalType.TIME)
    private Date departureTime;
    @Basic(optional = false)
    @Column(name = "arrival_time")
    @Temporal(TemporalType.TIME)
    private Date arrivalTime;
    @Basic(optional = false)
    @Column(name = "day_of_week")
    private String dayOfWeek;
    @JoinColumn(name = "bus_id", referencedColumnName = "bus_id")
    @ManyToOne(optional = false)
    private Buses busId;
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    @ManyToOne(optional = false)
    private Routes routeId;

    public Schedules() {
    }

    public Schedules(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Schedules(Integer scheduleId, Date departureTime, Date arrivalTime, String dayOfWeek) {
        this.scheduleId = scheduleId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Buses getBusId() {
        return busId;
    }

    public void setBusId(Buses busId) {
        this.busId = busId;
    }

    public Routes getRouteId() {
        return routeId;
    }

    public void setRouteId(Routes routeId) {
        this.routeId = routeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scheduleId != null ? scheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Schedules)) {
            return false;
        }
        Schedules other = (Schedules) object;
        if ((this.scheduleId == null && other.scheduleId != null) || (this.scheduleId != null && !this.scheduleId.equals(other.scheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.busplanner.pojo.Schedules[ scheduleId=" + scheduleId + " ]";
    }
    
}
