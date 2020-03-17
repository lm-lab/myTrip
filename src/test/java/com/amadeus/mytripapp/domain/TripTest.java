package com.amadeus.mytripapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amadeus.mytripapp.web.rest.TestUtil;

public class TripTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trip.class);
        Trip trip1 = new Trip();
        trip1.setId("id1");
        Trip trip2 = new Trip();
        trip2.setId(trip1.getId());
        assertThat(trip1).isEqualTo(trip2);
        trip2.setId("id2");
        assertThat(trip1).isNotEqualTo(trip2);
        trip1.setId(null);
        assertThat(trip1).isNotEqualTo(trip2);
    }
}
