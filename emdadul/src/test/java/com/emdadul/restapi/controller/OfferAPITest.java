package com.emdadul.restapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;  

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.emdadul.restapi.entities.Offer;
import com.emdadul.restapi.service.OfferService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OfferAPI.class)
public class OfferAPITest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OfferService offerService;
	
	@Test
	public void givenOffer_whenGetOffers_thenReturnJsonArray()
	  throws Exception {
		
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date = format.parse("December 5, 2020");
	    
	    Offer mockOffer = new Offer(1L, "Spring Offer", "Offer Desc", 5.0, date);
	    List<Offer> allOffer = Arrays.asList(mockOffer);
	    when(offerService.findAll()).thenReturn(allOffer);
	 
	 
	    mockMvc.perform(get("/api/v1/offers/")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(1)))
	      .andExpect(jsonPath("$[0].title", is(mockOffer.getTitle())));
	    
	    verify(offerService, times(1)).findAll();
        verifyNoMoreInteractions(offerService);
	}
	
	@Test
    public void findById_OfferEntryFound_ShouldReturnFoundOfferEntry() throws Exception {
		
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date = format.parse("December 5, 2020");
	    
	    Optional<Offer> mockOffer = Optional.ofNullable(new Offer(1L, "Spring Offer", "Offer Desc", 5.0, date));
	    when(offerService.findById(1L)).thenReturn(mockOffer);
 
 
        mockMvc.perform(get("/api/v1/offers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("Offer Desc")))
                .andExpect(jsonPath("$.title", is("Spring Offer")));
 
        verify(offerService, times(1)).findById(1L);
        verifyNoMoreInteractions(offerService);
    }
	
	@Test
    public void findById_OfferEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception {
 
        mockMvc.perform(get("/api/v1/offers/{id}", 1L))
                .andExpect(status().isNotFound());
 
        verify(offerService, times(1)).findById(1L);
        verifyNoMoreInteractions(offerService);
    }
	
	@Test
    public void add_NewOffer_ShouldAddOfferAndReturnAddedEntry() throws Exception {
        
		String offerJson = "{\"title\":\"Spring Offer\",\"description\":\"Offer Desc\",\"price\":\"10.0\",\"expire_date\":\"2020-12-30\"}";
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date = format.parse("December 5, 2020");
	    
	    Offer mockOffer = new Offer(1L, "Spring Offer", "Offer Desc", 5.0, date);
 
		when(offerService.save(Mockito.any(Offer.class))).thenReturn(mockOffer);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/offers")
				.accept(MediaType.APPLICATION_JSON).content(offerJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		
    }
	
}
