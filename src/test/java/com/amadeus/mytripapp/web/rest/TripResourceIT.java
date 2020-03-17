package com.amadeus.mytripapp.web.rest;

import com.amadeus.mytripapp.MyTripApp;
import com.amadeus.mytripapp.domain.Trip;
import com.amadeus.mytripapp.repository.TripRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TripResource} REST controller.
 */
@SpringBootTest(classes = MyTripApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TripResourceIT {

    private static final String DEFAULT_DEP_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_DEP_PLACE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEP_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEP_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Duration DEFAULT_DEP_TIME = Duration.ofHours(6);
    private static final Duration UPDATED_DEP_TIME = Duration.ofHours(12);

    private static final String DEFAULT_DEP_UTC_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_DEP_UTC_ZONE = "BBBBBBBBBB";

    private static final String DEFAULT_ARR_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_ARR_PLACE = "BBBBBBBBBB";

    private static final Instant DEFAULT_ARR_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARR_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Duration DEFAULT_ARR_TIME = Duration.ofHours(6);
    private static final Duration UPDATED_ARR_TIME = Duration.ofHours(12);

    private static final String DEFAULT_ARR_UTC_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_ARR_UTC_ZONE = "BBBBBBBBBB";

    private static final String DEFAULT_CABIN_CAT = "AAAAAAAAAA";
    private static final String UPDATED_CABIN_CAT = "BBBBBBBBBB";

    private static final String DEFAULT_MARKETING_FLIGHT_ID = "AAAAAAAAAA";
    private static final String UPDATED_MARKETING_FLIGHT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATOR_FLIGHT_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPERATOR_FLIGHT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MARKETING_AIRLINE = "AAAAAAAAAA";
    private static final String UPDATED_MARKETING_AIRLINE = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATING_AIRLINE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATING_AIRLINE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSPORTATION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPORTATION = "BBBBBBBBBB";

    private static final String DEFAULT_BOOKING_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_BOOKING_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_CABIN_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_CABIN_CLASS = "BBBBBBBBBB";

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private MockMvc restTripMockMvc;

    private Trip trip;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trip createEntity() {
        Trip trip = new Trip()
            .depPlace(DEFAULT_DEP_PLACE)
            .depDate(DEFAULT_DEP_DATE)
            .depTime(DEFAULT_DEP_TIME)
            .depUtcZone(DEFAULT_DEP_UTC_ZONE)
            .arrPlace(DEFAULT_ARR_PLACE)
            .arrDate(DEFAULT_ARR_DATE)
            .arrTime(DEFAULT_ARR_TIME)
            .arrUtcZone(DEFAULT_ARR_UTC_ZONE)
            .cabinCat(DEFAULT_CABIN_CAT)
            .marketingFlightId(DEFAULT_MARKETING_FLIGHT_ID)
            .operatorFlightId(DEFAULT_OPERATOR_FLIGHT_ID)
            .marketingAirline(DEFAULT_MARKETING_AIRLINE)
            .operatingAirline(DEFAULT_OPERATING_AIRLINE)
            .transportation(DEFAULT_TRANSPORTATION)
            .bookingClass(DEFAULT_BOOKING_CLASS)
            .cabinClass(DEFAULT_CABIN_CLASS);
        return trip;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trip createUpdatedEntity() {
        Trip trip = new Trip()
            .depPlace(UPDATED_DEP_PLACE)
            .depDate(UPDATED_DEP_DATE)
            .depTime(UPDATED_DEP_TIME)
            .depUtcZone(UPDATED_DEP_UTC_ZONE)
            .arrPlace(UPDATED_ARR_PLACE)
            .arrDate(UPDATED_ARR_DATE)
            .arrTime(UPDATED_ARR_TIME)
            .arrUtcZone(UPDATED_ARR_UTC_ZONE)
            .cabinCat(UPDATED_CABIN_CAT)
            .marketingFlightId(UPDATED_MARKETING_FLIGHT_ID)
            .operatorFlightId(UPDATED_OPERATOR_FLIGHT_ID)
            .marketingAirline(UPDATED_MARKETING_AIRLINE)
            .operatingAirline(UPDATED_OPERATING_AIRLINE)
            .transportation(UPDATED_TRANSPORTATION)
            .bookingClass(UPDATED_BOOKING_CLASS)
            .cabinClass(UPDATED_CABIN_CLASS);
        return trip;
    }

    @BeforeEach
    public void initTest() {
        tripRepository.deleteAll();
        trip = createEntity();
    }

    @Test
    public void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip
        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getDepPlace()).isEqualTo(DEFAULT_DEP_PLACE);
        assertThat(testTrip.getDepDate()).isEqualTo(DEFAULT_DEP_DATE);
        assertThat(testTrip.getDepTime()).isEqualTo(DEFAULT_DEP_TIME);
        assertThat(testTrip.getDepUtcZone()).isEqualTo(DEFAULT_DEP_UTC_ZONE);
        assertThat(testTrip.getArrPlace()).isEqualTo(DEFAULT_ARR_PLACE);
        assertThat(testTrip.getArrDate()).isEqualTo(DEFAULT_ARR_DATE);
        assertThat(testTrip.getArrTime()).isEqualTo(DEFAULT_ARR_TIME);
        assertThat(testTrip.getArrUtcZone()).isEqualTo(DEFAULT_ARR_UTC_ZONE);
        assertThat(testTrip.getCabinCat()).isEqualTo(DEFAULT_CABIN_CAT);
        assertThat(testTrip.getMarketingFlightId()).isEqualTo(DEFAULT_MARKETING_FLIGHT_ID);
        assertThat(testTrip.getOperatorFlightId()).isEqualTo(DEFAULT_OPERATOR_FLIGHT_ID);
        assertThat(testTrip.getMarketingAirline()).isEqualTo(DEFAULT_MARKETING_AIRLINE);
        assertThat(testTrip.getOperatingAirline()).isEqualTo(DEFAULT_OPERATING_AIRLINE);
        assertThat(testTrip.getTransportation()).isEqualTo(DEFAULT_TRANSPORTATION);
        assertThat(testTrip.getBookingClass()).isEqualTo(DEFAULT_BOOKING_CLASS);
        assertThat(testTrip.getCabinClass()).isEqualTo(DEFAULT_CABIN_CLASS);
    }

    @Test
    public void createTripWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip with an existing ID
        trip.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDepPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setDepPlace(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDepDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setDepDate(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDepTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setDepTime(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDepUtcZoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setDepUtcZone(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkArrPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setArrPlace(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkArrDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setArrDate(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkArrTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setArrTime(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkArrUtcZoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setArrUtcZone(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCabinClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setCabinClass(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.save(trip);

        // Get all the tripList
        restTripMockMvc.perform(get("/api/trips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId())))
            .andExpect(jsonPath("$.[*].depPlace").value(hasItem(DEFAULT_DEP_PLACE)))
            .andExpect(jsonPath("$.[*].depDate").value(hasItem(DEFAULT_DEP_DATE.toString())))
            .andExpect(jsonPath("$.[*].depTime").value(hasItem(DEFAULT_DEP_TIME.toString())))
            .andExpect(jsonPath("$.[*].depUtcZone").value(hasItem(DEFAULT_DEP_UTC_ZONE)))
            .andExpect(jsonPath("$.[*].arrPlace").value(hasItem(DEFAULT_ARR_PLACE)))
            .andExpect(jsonPath("$.[*].arrDate").value(hasItem(DEFAULT_ARR_DATE.toString())))
            .andExpect(jsonPath("$.[*].arrTime").value(hasItem(DEFAULT_ARR_TIME.toString())))
            .andExpect(jsonPath("$.[*].arrUtcZone").value(hasItem(DEFAULT_ARR_UTC_ZONE)))
            .andExpect(jsonPath("$.[*].cabinCat").value(hasItem(DEFAULT_CABIN_CAT)))
            .andExpect(jsonPath("$.[*].marketingFlightId").value(hasItem(DEFAULT_MARKETING_FLIGHT_ID)))
            .andExpect(jsonPath("$.[*].operatorFlightId").value(hasItem(DEFAULT_OPERATOR_FLIGHT_ID)))
            .andExpect(jsonPath("$.[*].marketingAirline").value(hasItem(DEFAULT_MARKETING_AIRLINE)))
            .andExpect(jsonPath("$.[*].operatingAirline").value(hasItem(DEFAULT_OPERATING_AIRLINE)))
            .andExpect(jsonPath("$.[*].transportation").value(hasItem(DEFAULT_TRANSPORTATION)))
            .andExpect(jsonPath("$.[*].bookingClass").value(hasItem(DEFAULT_BOOKING_CLASS)))
            .andExpect(jsonPath("$.[*].cabinClass").value(hasItem(DEFAULT_CABIN_CLASS)));
    }
    
    @Test
    public void getTrip() throws Exception {
        // Initialize the database
        tripRepository.save(trip);

        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trip.getId()))
            .andExpect(jsonPath("$.depPlace").value(DEFAULT_DEP_PLACE))
            .andExpect(jsonPath("$.depDate").value(DEFAULT_DEP_DATE.toString()))
            .andExpect(jsonPath("$.depTime").value(DEFAULT_DEP_TIME.toString()))
            .andExpect(jsonPath("$.depUtcZone").value(DEFAULT_DEP_UTC_ZONE))
            .andExpect(jsonPath("$.arrPlace").value(DEFAULT_ARR_PLACE))
            .andExpect(jsonPath("$.arrDate").value(DEFAULT_ARR_DATE.toString()))
            .andExpect(jsonPath("$.arrTime").value(DEFAULT_ARR_TIME.toString()))
            .andExpect(jsonPath("$.arrUtcZone").value(DEFAULT_ARR_UTC_ZONE))
            .andExpect(jsonPath("$.cabinCat").value(DEFAULT_CABIN_CAT))
            .andExpect(jsonPath("$.marketingFlightId").value(DEFAULT_MARKETING_FLIGHT_ID))
            .andExpect(jsonPath("$.operatorFlightId").value(DEFAULT_OPERATOR_FLIGHT_ID))
            .andExpect(jsonPath("$.marketingAirline").value(DEFAULT_MARKETING_AIRLINE))
            .andExpect(jsonPath("$.operatingAirline").value(DEFAULT_OPERATING_AIRLINE))
            .andExpect(jsonPath("$.transportation").value(DEFAULT_TRANSPORTATION))
            .andExpect(jsonPath("$.bookingClass").value(DEFAULT_BOOKING_CLASS))
            .andExpect(jsonPath("$.cabinClass").value(DEFAULT_CABIN_CLASS));
    }

    @Test
    public void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTrip() throws Exception {
        // Initialize the database
        tripRepository.save(trip);

        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        Trip updatedTrip = tripRepository.findById(trip.getId()).get();
        updatedTrip
            .depPlace(UPDATED_DEP_PLACE)
            .depDate(UPDATED_DEP_DATE)
            .depTime(UPDATED_DEP_TIME)
            .depUtcZone(UPDATED_DEP_UTC_ZONE)
            .arrPlace(UPDATED_ARR_PLACE)
            .arrDate(UPDATED_ARR_DATE)
            .arrTime(UPDATED_ARR_TIME)
            .arrUtcZone(UPDATED_ARR_UTC_ZONE)
            .cabinCat(UPDATED_CABIN_CAT)
            .marketingFlightId(UPDATED_MARKETING_FLIGHT_ID)
            .operatorFlightId(UPDATED_OPERATOR_FLIGHT_ID)
            .marketingAirline(UPDATED_MARKETING_AIRLINE)
            .operatingAirline(UPDATED_OPERATING_AIRLINE)
            .transportation(UPDATED_TRANSPORTATION)
            .bookingClass(UPDATED_BOOKING_CLASS)
            .cabinClass(UPDATED_CABIN_CLASS);

        restTripMockMvc.perform(put("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrip)))
            .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getDepPlace()).isEqualTo(UPDATED_DEP_PLACE);
        assertThat(testTrip.getDepDate()).isEqualTo(UPDATED_DEP_DATE);
        assertThat(testTrip.getDepTime()).isEqualTo(UPDATED_DEP_TIME);
        assertThat(testTrip.getDepUtcZone()).isEqualTo(UPDATED_DEP_UTC_ZONE);
        assertThat(testTrip.getArrPlace()).isEqualTo(UPDATED_ARR_PLACE);
        assertThat(testTrip.getArrDate()).isEqualTo(UPDATED_ARR_DATE);
        assertThat(testTrip.getArrTime()).isEqualTo(UPDATED_ARR_TIME);
        assertThat(testTrip.getArrUtcZone()).isEqualTo(UPDATED_ARR_UTC_ZONE);
        assertThat(testTrip.getCabinCat()).isEqualTo(UPDATED_CABIN_CAT);
        assertThat(testTrip.getMarketingFlightId()).isEqualTo(UPDATED_MARKETING_FLIGHT_ID);
        assertThat(testTrip.getOperatorFlightId()).isEqualTo(UPDATED_OPERATOR_FLIGHT_ID);
        assertThat(testTrip.getMarketingAirline()).isEqualTo(UPDATED_MARKETING_AIRLINE);
        assertThat(testTrip.getOperatingAirline()).isEqualTo(UPDATED_OPERATING_AIRLINE);
        assertThat(testTrip.getTransportation()).isEqualTo(UPDATED_TRANSPORTATION);
        assertThat(testTrip.getBookingClass()).isEqualTo(UPDATED_BOOKING_CLASS);
        assertThat(testTrip.getCabinClass()).isEqualTo(UPDATED_CABIN_CLASS);
    }

    @Test
    public void updateNonExistingTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Create the Trip

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTripMockMvc.perform(put("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTrip() throws Exception {
        // Initialize the database
        tripRepository.save(trip);

        int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Delete the trip
        restTripMockMvc.perform(delete("/api/trips/{id}", trip.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
