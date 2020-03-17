package com.amadeus.mytripapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.Duration;

/**
 * The Trip entity.
 */
@ApiModel(description = "The Trip entity.")
@Document(collection = "trip")
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("dep_place")
    private String depPlace;

    @NotNull
    @Field("dep_date")
    private Instant depDate;

    @NotNull
    @Field("dep_time")
    private Duration depTime;

    @NotNull
    @Field("dep_utc_zone")
    private String depUtcZone;

    @NotNull
    @Field("arr_place")
    private String arrPlace;

    @NotNull
    @Field("arr_date")
    private Instant arrDate;

    @NotNull
    @Field("arr_time")
    private Duration arrTime;

    @NotNull
    @Field("arr_utc_zone")
    private String arrUtcZone;

    @Field("cabin_cat")
    private String cabinCat;

    @Field("marketing_flight_id")
    private String marketingFlightId;

    @Field("operator_flight_id")
    private String operatorFlightId;

    @Field("marketing_airline")
    private String marketingAirline;

    @Field("operating_airline")
    private String operatingAirline;

    @Field("transportation")
    private String transportation;

    @Field("booking_class")
    private String bookingClass;

    @NotNull
    @Field("cabin_class")
    private String cabinClass;

    @DBRef
    @Field("user")
    @JsonIgnoreProperties("trips")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepPlace() {
        return depPlace;
    }

    public Trip depPlace(String depPlace) {
        this.depPlace = depPlace;
        return this;
    }

    public void setDepPlace(String depPlace) {
        this.depPlace = depPlace;
    }

    public Instant getDepDate() {
        return depDate;
    }

    public Trip depDate(Instant depDate) {
        this.depDate = depDate;
        return this;
    }

    public void setDepDate(Instant depDate) {
        this.depDate = depDate;
    }

    public Duration getDepTime() {
        return depTime;
    }

    public Trip depTime(Duration depTime) {
        this.depTime = depTime;
        return this;
    }

    public void setDepTime(Duration depTime) {
        this.depTime = depTime;
    }

    public String getDepUtcZone() {
        return depUtcZone;
    }

    public Trip depUtcZone(String depUtcZone) {
        this.depUtcZone = depUtcZone;
        return this;
    }

    public void setDepUtcZone(String depUtcZone) {
        this.depUtcZone = depUtcZone;
    }

    public String getArrPlace() {
        return arrPlace;
    }

    public Trip arrPlace(String arrPlace) {
        this.arrPlace = arrPlace;
        return this;
    }

    public void setArrPlace(String arrPlace) {
        this.arrPlace = arrPlace;
    }

    public Instant getArrDate() {
        return arrDate;
    }

    public Trip arrDate(Instant arrDate) {
        this.arrDate = arrDate;
        return this;
    }

    public void setArrDate(Instant arrDate) {
        this.arrDate = arrDate;
    }

    public Duration getArrTime() {
        return arrTime;
    }

    public Trip arrTime(Duration arrTime) {
        this.arrTime = arrTime;
        return this;
    }

    public void setArrTime(Duration arrTime) {
        this.arrTime = arrTime;
    }

    public String getArrUtcZone() {
        return arrUtcZone;
    }

    public Trip arrUtcZone(String arrUtcZone) {
        this.arrUtcZone = arrUtcZone;
        return this;
    }

    public void setArrUtcZone(String arrUtcZone) {
        this.arrUtcZone = arrUtcZone;
    }

    public String getCabinCat() {
        return cabinCat;
    }

    public Trip cabinCat(String cabinCat) {
        this.cabinCat = cabinCat;
        return this;
    }

    public void setCabinCat(String cabinCat) {
        this.cabinCat = cabinCat;
    }

    public String getMarketingFlightId() {
        return marketingFlightId;
    }

    public Trip marketingFlightId(String marketingFlightId) {
        this.marketingFlightId = marketingFlightId;
        return this;
    }

    public void setMarketingFlightId(String marketingFlightId) {
        this.marketingFlightId = marketingFlightId;
    }

    public String getOperatorFlightId() {
        return operatorFlightId;
    }

    public Trip operatorFlightId(String operatorFlightId) {
        this.operatorFlightId = operatorFlightId;
        return this;
    }

    public void setOperatorFlightId(String operatorFlightId) {
        this.operatorFlightId = operatorFlightId;
    }

    public String getMarketingAirline() {
        return marketingAirline;
    }

    public Trip marketingAirline(String marketingAirline) {
        this.marketingAirline = marketingAirline;
        return this;
    }

    public void setMarketingAirline(String marketingAirline) {
        this.marketingAirline = marketingAirline;
    }

    public String getOperatingAirline() {
        return operatingAirline;
    }

    public Trip operatingAirline(String operatingAirline) {
        this.operatingAirline = operatingAirline;
        return this;
    }

    public void setOperatingAirline(String operatingAirline) {
        this.operatingAirline = operatingAirline;
    }

    public String getTransportation() {
        return transportation;
    }

    public Trip transportation(String transportation) {
        this.transportation = transportation;
        return this;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getBookingClass() {
        return bookingClass;
    }

    public Trip bookingClass(String bookingClass) {
        this.bookingClass = bookingClass;
        return this;
    }

    public void setBookingClass(String bookingClass) {
        this.bookingClass = bookingClass;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public Trip cabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
        return this;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public User getUser() {
        return user;
    }

    public Trip user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trip)) {
            return false;
        }
        return id != null && id.equals(((Trip) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trip{" +
            "id=" + getId() +
            ", depPlace='" + getDepPlace() + "'" +
            ", depDate='" + getDepDate() + "'" +
            ", depTime='" + getDepTime() + "'" +
            ", depUtcZone='" + getDepUtcZone() + "'" +
            ", arrPlace='" + getArrPlace() + "'" +
            ", arrDate='" + getArrDate() + "'" +
            ", arrTime='" + getArrTime() + "'" +
            ", arrUtcZone='" + getArrUtcZone() + "'" +
            ", cabinCat='" + getCabinCat() + "'" +
            ", marketingFlightId='" + getMarketingFlightId() + "'" +
            ", operatorFlightId='" + getOperatorFlightId() + "'" +
            ", marketingAirline='" + getMarketingAirline() + "'" +
            ", operatingAirline='" + getOperatingAirline() + "'" +
            ", transportation='" + getTransportation() + "'" +
            ", bookingClass='" + getBookingClass() + "'" +
            ", cabinClass='" + getCabinClass() + "'" +
            "}";
    }
}
