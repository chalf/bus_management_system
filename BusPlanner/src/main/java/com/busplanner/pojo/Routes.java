/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.pojo;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "routes")
@NamedQueries({
    @NamedQuery(name = "Routes.findAll", query = "SELECT r FROM Routes r"),
    @NamedQuery(name = "Routes.findByRouteId", query = "SELECT r FROM Routes r WHERE r.routeId = :routeId"),
    @NamedQuery(name = "Routes.findByRouteName", query = "SELECT r FROM Routes r WHERE r.routeName = :routeName"),
    @NamedQuery(name = "Routes.findByDirection", query = "SELECT r FROM Routes r WHERE r.direction = :direction"),
    @NamedQuery(name = "Routes.findByStartPoint", query = "SELECT r FROM Routes r WHERE r.startPoint = :startPoint"),
    @NamedQuery(name = "Routes.findByEndPoint", query = "SELECT r FROM Routes r WHERE r.endPoint = :endPoint"),
    @NamedQuery(name = "Routes.findByCreatedAt", query = "SELECT r FROM Routes r WHERE r.createdAt = :createdAt"),
    @NamedQuery(name = "Routes.findByUpdatedAt", query = "SELECT r FROM Routes r WHERE r.updatedAt = :updatedAt")})
public class Routes implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "route_name")
    private String routeName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "direction")
    private String direction;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "start_point")
    private String startPoint;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "end_point")
    private String endPoint;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "route_id")
    private Integer routeId;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(mappedBy = "routeId")
    private Set<Buses> busesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeId")
    private Set<Schedules> schedulesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeId")
    private Set<Routestops> routestopsSet;

    public Routes() {
    }

    public Routes(Integer routeId) {
        this.routeId = routeId;
    }

    public Routes(Integer routeId, String routeName, String direction, String startPoint, String endPoint) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.direction = direction;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }




    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
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

    public Set<Buses> getBusesSet() {
        return busesSet;
    }

    public void setBusesSet(Set<Buses> busesSet) {
        this.busesSet = busesSet;
    }

    public Set<Schedules> getSchedulesSet() {
        return schedulesSet;
    }

    public void setSchedulesSet(Set<Schedules> schedulesSet) {
        this.schedulesSet = schedulesSet;
    }

    public Set<Routestops> getRoutestopsSet() {
        return routestopsSet;
    }

    public void setRoutestopsSet(Set<Routestops> routestopsSet) {
        this.routestopsSet = routestopsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (routeId != null ? routeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routes)) {
            return false;
        }
        Routes other = (Routes) object;
        if ((this.routeId == null && other.routeId != null) || (this.routeId != null && !this.routeId.equals(other.routeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.busplanner.pojo.Routes[ routeId=" + routeId + " ]";
    }
    
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
