package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AirportAviationTest {

    AirportAviation airportAviation;

    @Before
    public void setUp() {
        airportAviation = new AirportAviation();
        airportAviation.setAirportId("1")
                .setGmt("gmt")
                .setIataCode("iata_code")
                .setCityIataCode("city_iata_code")
                .setIcaoCode("icao_code")
                .setCountryIso2("country_iso2")
                .setGeonameId("geoname_id")
                .setLatitude(1f)
                .setLongitude(1f)
                .setAirportName("airport_name")
                .setCountryName("country_name")
                .setPhoneNumber("phone_number")
                .setTimezone("timezone");
    }

    @Test
    public void toStringShouldReturnNotNull() {
        assertNotNull(airportAviation.toString());
    }

    @Test
    public void equalsShouldReturnTrue() {
        AirportAviation airportAviationToTest = new AirportAviation();
        airportAviationToTest.setAirportId("1")
                .setGmt("gmt")
                .setIataCode("iata_code")
                .setCityIataCode("city_iata_code")
                .setIcaoCode("icao_code")
                .setCountryIso2("country_iso2")
                .setGeonameId("geoname_id")
                .setLatitude(1f)
                .setLongitude(1f)
                .setAirportName("airport_name")
                .setCountryName("country_name")
                .setPhoneNumber("phone_number")
                .setTimezone("timezone");

        assertEquals(airportAviation, airportAviationToTest);
    }

    @Test
    public void equalsShouldReturnFalse() {
        AirportAviation airportAviationToTest = new AirportAviation();
        airportAviationToTest.setAirportId("2")
                .setGmt("gmt2")
                .setIataCode("iata_code2")
                .setCityIataCode("city_iata_code2")
                .setIcaoCode("icao_code2")
                .setCountryIso2("country_iso22")
                .setGeonameId("geoname_id2")
                .setLatitude(2f)
                .setLongitude(2f)
                .setAirportName("airport_name2")
                .setCountryName("country_name2")
                .setPhoneNumber("phone_number2")
                .setTimezone("timezone2");

        assertNotEquals(airportAviation, airportAviationToTest);
    }
}