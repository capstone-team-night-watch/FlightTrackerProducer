package com.capstone.producer;

import com.capstone.producer.kafka.KafkaProducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TfrHandlerTest {

    @InjectMocks
    private TfrHandler tfrHandler;

    private String boundaryPart1 = "FDC 4/3473 ZAB PART 1 OF 2 TX..AIRSPACE VAN HORN, TX..TEMPORARY\r\n" + //
                " FLIGHT\r\n" + //
                "RESTRICTIONS.  \r\n" + //
                "      FEBRUARY 13-20, 2024 LOCAL.  PURSUANT TO 49 USC 40103(B)(3), \r\n" + //
                "THE FEDERAL AVIATION ADMINISTRATION (FAA) CLASSIFIES THE AIRSPACE \r\n" + //
                "DEFINED IN THIS NOTAM AS 'NTL DEFENSE AIRSPACE'. ANY PERSON WHO \r\n" + //
                "KNOWINGLY OR WILLFULLY VIOLATES THE RULES CONCERNING OPS IN THIS \r\n" + //
                "AIRSPACE MAY BE SUBJECT TO CERTAIN CRIMINAL PENALTIES UNDER 49 USC \r\n" + //
                "40103(B)(3).  PILOTS WHO DO NOT ADHERE TO THE FOLLOWING PROC MAY \r\n" + //
                "BE INTERCEPTED, DETAINED AND INTERVIEWED BY LAW \r\n" + //
                "ENFORCEMENT/SECURITY PERSONNEL.   \r\n" + //
                "PURSUANT TO 14 CFR 99.7, SPECIAL SECURITY INSTRUCTIONS, ALL ACFT \r\n" + //
                "FLT OPS ARE PROHIBITED: WI AN AREA DEFINED AS 305920N1044129W \r\n" + //
                "(HUP122049.4) TO 304329N1045617W (HUP144055.3) TO 301152N1043828W \r\n" + //
                "(MRF250036.2) TO 295558N1043739W (MRF227041.3) TO 300651N1042027W \r\n" + //
                "(MRF230022.9) TO 302311N1042820W (MRF270027.4) TO THE POINT OF \r\n" + //
                "ORIGIN 10000FT MSL-13000FT MSL  \r\n" + //
                "EFFECTIVE 2402140200 UTC (2000 LOCAL 02/13/24) UNTIL 2402201400 UTC \r\n" + //
                "(0800 LOCAL 02/20/24) DLY 0200-1400 (2000-0800 LOCAL).  \r\n" + //
                "\r\n" + //
                "\r\n" + //
                "2402140200-2402201400\r\n" + //
                "END PART 1 OF 2";

    private String boundaryPart2 = "FDC 4/3473 ZAB PART 2 OF 2 TX..AIRSPACE VAN HORN, TX..TEMPORARY\r\n" + //
                " FLIGHT\r\n" + //
                "\r\n" + //
                " EXCEPT AS SPECIFIED BELOW, EXCLUDING MEXICAN\r\n" + //
                "AIRSPACE, AND/OR UNLESS AUTHORIZED BY ATC:\r\n" + //
                "   1. ALL AIRCRAFT ENTERING OR EXITING THE TFR MUST\r\n" + //
                "      BE ON AN ACTIVE IFR OR VFR FLIGHT PLAN WITH A\r\n" + //
                "      DISCRETE CODE ASSIGNED BY AN AIR TRAFFIC CONTROL\r\n" + //
                "      (ATC) FACILITY.\r\n" + //
                "   2. AIRCRAFT MUST BE SQUAWKING THE DISCRETE CODE\r\n" + //
                "      PRIOR TO DEPARTURE AND AT ALL TIMES WHILE IN THE\r\n" + //
                "      TFR.\r\n" + //
                "   3. ALL AIRCRAFT ENTERING OR EXITING THE TFR MUST\r\n" + //
                "      REMAIN IN TWO-WAY RADIO COMMUNICATIONS WITH ATC.\r\n" + //
                "   4. ALBUQUERQUE ARTCC, PHONE 505-856-4500 IS THE\r\n" + //
                "      FAA COORDINATION FACILITY.\r\n" + //
                " \r\n" + //
                "2402140200-2402201400\r\n" + //
                "END PART 2 OF 2";
    private String radius = "FDC 4/4447 ZFW TX..AIRSPACE SAN ANGELO, TX..TEMPORARY\r\n" + //
                " FLIGHT\r\n" + //
                "RESTRICTIONS.  \r\n" + //
                "      FEBRUARY 18-23, 2024 LOCAL.  PURSUANT TO 49 USC 40103(B)(3), \r\n" + //
                "THE FEDERAL AVIATION ADMINISTRATION (FAA) CLASSIFIES THE AIRSPACE \r\n" + //
                "DEFINED IN THIS NOTAM AS 'NTL DEFENSE AIRSPACE'. ANY PERSON WHO \r\n" + //
                "KNOWINGLY OR WILLFULLY VIOLATES THE RULES CONCERNING OPS IN THIS \r\n" + //
                "AIRSPACE MAY BE SUBJECT TO CERTAIN CRIMINAL PENALTIES UNDER 49 USC \r\n" + //
                "40103(B)(3).  PILOTS WHO DO NOT ADHERE TO THE FOLLOWING PROC MAY \r\n" + //
                "BE INTERCEPTED, DETAINED AND INTERVIEWED BY LAW \r\n" + //
                "ENFORCEMENT/SECURITY PERSONNEL.   \r\n" + //
                "PURSUANT TO 14 CFR 99.7, SPECIAL SECURITY INSTRUCTIONS, ALL ACFT \r\n" + //
                "FLT OPS ARE PROHIBITED: WI AN AREA DEFINED AS 5NM RADIUS OF \r\n" + //
                "311647N1002234W (SJT135007.0) 3500FT-FL180  \r\n" + //
                "EFFECTIVE 2402182100 UTC (1500 LOCAL 02/18/24) UNTIL 2402240500 UTC \r\n" + //
                "(2300 LOCAL 02/23/24).  \r\n" + //
                "\r\n" + //
                "\r\n" + //
                "\r\n" + //
                " EXCEPT AS SPECIFIED BELOW AND/OR UNLESS AUTHORIZED\r\n" + //
                "BY ATC:\r\n" + //
                "2402182100-2402240500";
    private String invalidTfrBody = "FDC 4/4447 ZFW TX..AIRSPACE SAN ANGELO, TX..TEMPORARY\r\n" + //
                " FLIGHT\r\n" + //
                "RESTRICTIONS.  \r\n" + //
                "      FEBRUARY 18-23, 2024 LOCAL.  PURSUANT TO 49 USC 40103(B)(3), \r\n" + //
                "THE FEDERAL AVIATION ADMINISTRATION (FAA) CLASSIFIES THE AIRSPACE \r\n" + //
                "\r\n" + //
                "\r\n" + //
                "\r\n" + //
                " EXCEPT AS SPECIFIED BELOW AND/OR UNLESS AUTHORIZED\r\n" + //
                "BY ATC:\r\n" + //
                "2402182100-2402240500";

    @Test
    public void testInvalidTfr() throws InterruptedException{
        String  result = tfrHandler.handleTfrAddition("INVALID");
        assertEquals("Unable to parse NOTAM", result);
    }

    @Test
    public void testValidTfrBoundaryPart1() throws InterruptedException{
        String  result = tfrHandler.handleTfrAddition(boundaryPart1);
        assertEquals("Received Notam: 4/3473 waiting on further parts", result);
    }

    @Test
    public void testValidTfrBoundaryPart2() throws InterruptedException{
        MockedStatic<KafkaProducer> mockedStatic = mockStatic(KafkaProducer.class);

        mockedStatic.when(() -> KafkaProducer.runProducer(anyString(), anyString())).thenReturn(null);
        tfrHandler.handleTfrAddition(boundaryPart1);
        String  result = tfrHandler.handleTfrAddition(boundaryPart2);
        assertEquals("Boundary Parsed", result);

        mockedStatic.verify(() -> KafkaProducer.runProducer(anyString(), anyString()), times(1));
        mockedStatic.close();
    }

    @Test
    public void testValidTfrRadius() throws InterruptedException{
        MockedStatic<KafkaProducer> mockedStatic = mockStatic(KafkaProducer.class);

        mockedStatic.when(() -> KafkaProducer.runProducer(anyString(), anyString())).thenReturn(null);
        String  result = tfrHandler.handleTfrAddition(radius);
        assertEquals("Point Radius Parsed", result);
        mockedStatic.verify(() -> KafkaProducer.runProducer(anyString(), anyString()), times(1));
        mockedStatic.close();
    }

    @Test
    public void testBadTfrBody() throws InterruptedException{
        String  result = tfrHandler.handleTfrAddition(invalidTfrBody);
        assertEquals("Unable to parse :(", result);
    }

}
