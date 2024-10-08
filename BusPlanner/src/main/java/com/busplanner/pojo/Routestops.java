/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.pojo;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "routestops")
@NamedQueries({
    @NamedQuery(name = "Routestops.findAll", query = "SELECT r FROM Routestops r"),
    @NamedQuery(name = "Routestops.findByRouteStopId", query = "SELECT r FROM Routestops r WHERE r.routeStopId = :routeStopId"),
    @NamedQuery(name = "Routestops.findByStopOrder", query = "SELECT r FROM Routestops r WHERE r.stopOrder = :stopOrder")})
public class Routestops implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "route_stop_id")
    private Integer routeStopId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stop_order")
    private int stopOrder;
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Routes routeId;
    @JoinColumn(name = "stop_id", referencedColumnName = "stop_id")
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Stops stopId;

    public Routestops() {
    }

    public Routestops(Integer routeStopId) {
        this.routeStopId = routeStopId;
    }

    public Routestops(Integer routeStopId, int stopOrder) {
        this.routeStopId = routeStopId;
        this.stopOrder = stopOrder;
    }

    public Integer getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(Integer routeStopId) {
        this.routeStopId = routeStopId;
    }

    public int getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(int stopOrder) {
        this.stopOrder = stopOrder;
    }

    public Routes getRouteId() {
        return routeId;
    }

    public void setRouteId(Routes routeId) {
        this.routeId = routeId;
    }

    public Stops getStopId() {
        return stopId;
    }

    public void setStopId(Stops stopId) {
        this.stopId = stopId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (routeStopId != null ? routeStopId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routestops)) {
            return false;
        }
        Routestops other = (Routestops) object;
        if ((this.routeStopId == null && other.routeStopId != null) || (this.routeStopId != null && !this.routeStopId.equals(other.routeStopId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.busplanner.pojo.Routestops[ routeStopId=" + routeStopId + " ]";
    }
    
}
