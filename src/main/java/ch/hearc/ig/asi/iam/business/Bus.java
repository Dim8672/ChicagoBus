package ch.hearc.ig.asi.iam.business;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author dimitri.mella
 * Classe métier utilisée pour représenter les bus
 */
public class Bus implements Serializable{
    
    private Long id;
    private String direction;
    private Double latitude;
    private Double longitude;
    private BigDecimal distance;
    private boolean goodBus;

    public Bus(Long id, String direction, Double latitude, Double longitude, BigDecimal distance) {
        this.id = id;
        this.direction = direction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.goodBus = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public boolean isGoodBus() {
        return goodBus;
    }

    public void setGoodBus(boolean goodBus) {
        this.goodBus = goodBus;
    }
 
}
