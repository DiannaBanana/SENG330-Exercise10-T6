package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import databuilders.ObservationBuilder;
import models.WhaleModel;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static play.mvc.Http.MimeTypes.JSON;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class JsonAPIControllerTest extends WithApplication {

    private WhaleModel model;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
        .build();
    }

    @BeforeEach
    public void cleanup(){
        model = app.injector().instanceOf(WhaleModel.class);
        assertNotNull(model);
        model.getObservationStore().clearAll();
    }

    @Test
    public void testGetEmpty() throws JsonProcessingException {
        cleanup();
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/whales");
        Result result = route(app, request);

        assertEquals(JSON, result.contentType().get());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.readValue(Helpers.contentAsString(result), JsonNode.class);
        assertTrue(jsonData.get("isSuccessful").asBoolean());
        assertEquals(0, jsonData.get("body").size());
    }

    @Test
    public void testWithSingleObservation() throws JsonProcessingException {
        cleanup();
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/whales");

        ObservationBuilder ob = new ObservationBuilder()
                .observedAt(LocalDateTime.of(2000, 12, 5, 4, 1))
                .atLocation("UVic").withNWhalesSpecies(5, "orca");


        model.getObservationStore().addObservationToStore(ob.build());
        Result result = route(app, request);


        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonData = mapper.readValue(Helpers.contentAsString(result), JsonNode.class);
        assertTrue(jsonData.get("isSuccessful").asBoolean());
        assertEquals(5, jsonData.get("body").size());
    }

    @Test
    public void testWithMultipleObservations() throws JsonProcessingException {
        cleanup();
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/whales");

        ObservationBuilder ob = new ObservationBuilder()
                .observedAt(LocalDateTime.of(2000, 12, 5, 4, 1))
                .atLocation("UVic").withNWhalesSpecies(5, "orca");

        model.getObservationStore().addObservationToStore(ob.build());

        ObservationBuilder ob2 = new ObservationBuilder()
                .observedAt(LocalDateTime.of(2002, 12, 5, 4, 1))
                .atLocation("UVic").withNWhalesSpecies(1, "grey");
        model.getObservationStore().addObservationToStore(ob2.build());


        Result result = route(app, request);


        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonData = mapper.readValue(Helpers.contentAsString(result), JsonNode.class);
        assertTrue(jsonData.get("isSuccessful").asBoolean());

        //5+1 for all whales total
        assertEquals(6, jsonData.get("body").size());
    }



}
