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
@ContextConfiguration(classes = {DestinationController.class, Destination.class})
@WebMvcTest
public class DestinationCRUDTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
	void DestinationRead() throws Exception {

		String expectedResult = "[{\"destinationID\":901,\"destinationName\":\"TEST DESTINATION\",\"destinationTag\":\"TEST,TEST,TEST\",\"directions\":\"DIR;INSTRUCTIONS*DIR;INSTRUCTIONS*DIR;INSTRUCTIONS*\",\"imgID\":9999,\"userID\":9999},"+
        "{\"destinationID\":902,\"destinationName\":\"TEST DESTINATION 2\",\"destinationTag\":\"TEST,STRING,TEST\",\"directions\":\"directions\",\"imgID\":9999,\"userID\":9999}]";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/destination?searchString=TEST DESTINATION")
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
