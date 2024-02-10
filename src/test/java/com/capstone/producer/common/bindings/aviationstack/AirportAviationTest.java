package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AirportAviationTest {

    Airport_Aviation airportAviation;

    @Before
    public void setUp() {
        airportAviation = new Airport_Aviation();
        airportAviation.setAirport_id("1")
                .setGmt("gmt")
                .setIata_code("iata_code")
                .setCity_iata_code("city_iata_code")
                .setIcao_code("icao_code")
                .setCountry_iso2("country_iso2")
                .setGeoname_id("geoname_id")
                .setLatitude(1f)
                .setLongitude(1f)
                .setAirport_name("airport_name")
                .setCountry_name("country_name")
                .setPhone_number("phone_number")
                .setTimezone("timezone");
    }

    @Test
    public void toStringShouldReturnNotNull() {
        assertNotNull(airportAviation.toString());
    }

    @Test
    public void equalsShouldReturnTrue() {
        Airport_Aviation airportAviationToTest = new Airport_Aviation();
        airportAviationToTest.setAirport_id("1")
                .setGmt("gmt")
                .setIata_code("iata_code")
                .setCity_iata_code("city_iata_code")
                .setIcao_code("icao_code")
                .setCountry_iso2("country_iso2")
                .setGeoname_id("geoname_id")
                .setLatitude(1f)
                .setLongitude(1f)
                .setAirport_name("airport_name")
                .setCountry_name("country_name")
                .setPhone_number("phone_number")
                .setTimezone("timezone");

        assertEquals(airportAviation, airportAviationToTest);
    }

    @Test
    public void equalsShouldReturnFalse() {
        Airport_Aviation airportAviationToTest = new Airport_Aviation();
        airportAviationToTest.setAirport_id("2")
                .setGmt("gmt2")
                .setIata_code("iata_code2")
                .setCity_iata_code("city_iata_code2")
                .setIcao_code("icao_code2")
                .setCountry_iso2("country_iso22")
                .setGeoname_id("geoname_id2")
                .setLatitude(2f)
                .setLongitude(2f)
                .setAirport_name("airport_name2")
                .setCountry_name("country_name2")
                .setPhone_number("phone_number2")
                .setTimezone("timezone2");

        assertNotEquals(airportAviation, airportAviationToTest);
    }
}