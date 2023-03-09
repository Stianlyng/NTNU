package no.ntnu.idata2001.realestate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PropertyTest {

    public PropertyTest() {
    }
    
    
    
    @Test
    public void testCreationOfInstance() {
        Property p = new Property(1400, "Skodje", 54, 123, "Solvik", 980.2);
        // Now verify that all the parameters were set
        assertEquals(1400, p.getMunicipalityNumber());
        assertEquals("Skodje", p.getMunicipalityName());
        assertEquals(54, p.getLotNumber());
        assertEquals(123, p.getSectionNumber());
        assertEquals("Solvik", p.getName());
        assertEquals(980.2, p.getArea());
    }
}