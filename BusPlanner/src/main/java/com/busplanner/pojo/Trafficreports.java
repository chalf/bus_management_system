/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "trafficreports")
@NamedQueries({
    @NamedQuery(name = "Trafficreports.findAll", query = "SELECT t FROM Trafficreports t"),
    @NamedQuery(name = "Trafficreports.findByReportId", query = "SELECT t FROM Trafficreports t WHERE t.reportId = :reportId"),
    @NamedQuery(name = "Trafficreports.findByLatitude", query = "SELECT t FROM Trafficreports t WHERE t.latitude = :latitude"),
    @NamedQuery(name = "Trafficreports.findByLongitude", query = "SELECT t FROM Trafficreports t WHERE t.longitude = :longitude"),
    @NamedQuery(name = "Trafficreports.findByReportType", query = "SELECT t FROM Trafficreports t WHERE t.reportType = :reportType"),
    @NamedQuery(name = "Trafficreports.findByImageUrl", query = "SELECT t FROM Trafficreports t WHERE t.imageUrl = :imageUrl"),
    @NamedQuery(name = "Trafficreports.findByCreatedAt", query = "SELECT t FROM Trafficreports t WHERE t.createdAt = :createdAt")})
public class Trafficreports implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "report_id")
    private Integer reportId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "report_type")
    private String reportType;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users userId;

    public Trafficreports() {
    }

    public Trafficreports(Integer reportId) {
        this.reportId = reportId;
    }

    public Trafficreports(Integer reportId, BigDecimal latitude, BigDecimal longitude, String reportType) {
        this.reportId = reportId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reportType = reportType;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
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

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trafficreports)) {
            return false;
        }
        Trafficreports other = (Trafficreports) object;
        if ((this.reportId == null && other.reportId != null) || (this.reportId != null && !this.reportId.equals(other.reportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.busplanner.pojo.Trafficreports[ reportId=" + reportId + " ]";
    }
    
}
