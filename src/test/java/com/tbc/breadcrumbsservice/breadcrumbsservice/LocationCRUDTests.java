package com.tbc.breadcrumbsservice.breadcrumbsservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {LocationController.class, Location.class})
@WebMvcTest
class LocationCRUDTests {

	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	void LocationRead()  throws Exception {
		String expectedResult = "[{\"locationID\":901,\"locationName\":\"TEST LOCATION\",\"streetNumber\":9999,\"roadName\":\"TEST ROAD\",\"city\":\"TEST CITY\",\"state\":\"XX\",\"zipCode\":9999,\"locationTag\":\"TEST,TEST,TEST\",\"imgID\":9999}]";
		// Location expectedLocation = new Location();
		// expectedLocation.setLocationID(0);
		// expectedLocation.setLocationName("");
		// expectedLocation.setStreetNumber(0);
		// expectedLocation.setRoadName("");
		// expectedLocation.setCity("");
		// expectedLocation.setState("");
		// expectedLocation.setZipCode(0);
		// expectedLocation.setLocationTag("");
		// expectedLocation.setImgID(0);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/location?searchString=TEST LOCATION")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultLocationRead = result.getResponse().getContentAsString();
		System.out.println(resultLocationRead);
        assertNotNull(resultLocationRead);
        assertEquals(resultLocationRead, expectedResult);
		
	}

}
