package controllers;

import databuilders.ObservationBuilder;
import models.Observation;
import models.Whale;
import models.WhaleModel;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.*;
import static play.mvc.Http.MimeTypes.HTML;
import static play.test.Helpers.*;

public class WhaleControllerTest extends WithApplication {
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
    public void testRoutingHTML(){
        cleanup();
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/whales")
                .header("accept", HTML);

        Result result = route(app, request);
        assertEquals(303, result.status());
        assertEquals(routes.SearchController.search().url(), result.header("Location").get());
    }

    @Test
    public void testRemoveValidWhale(){
        cleanup();

        Whale testWhale = new Whale(Whale.Species.ORCA, 123, Whale.Gender.MALE);

        ObservationBuilder obBuilder = new ObservationBuilder()
                .observedAt(LocalDateTime.now()).atLocation("somewhere")
                .withWhale(testWhale);

        Observation complete = obBuilder.build();
        model.getObservationStore().addObservationToStore(complete);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(String.format("/observation/%d/remove_whale/%d", complete.getId(), testWhale.getId()))
                .header("accept", HTML);

        assertTrue("check that whale is in model list after adding", model.getWhaleStore().getAllWhales().contains(testWhale));

        route(app, request);

        assertFalse("check that whale is not in model list after request", model.getWhaleStore().getAllWhales().contains(testWhale));

    }

    @Test
    public void testRemoveInvalidWhaleHasNoEffect(){
        cleanup();

        Whale testWhale = new Whale(Whale.Species.ORCA, 123, Whale.Gender.MALE);

        ObservationBuilder obBuilder = new ObservationBuilder()
                .observedAt(LocalDateTime.now()).atLocation("somewhere")
                .withWhale(testWhale);

        Observation complete = obBuilder.build();
        model.getObservationStore().addObservationToStore(complete);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(String.format("/observation/%d/remove_whale/%d", complete.getId(), testWhale.getId()+1))
                .header("accept", HTML);

        assertEquals(1, model.getWhaleStore().getAllWhales().size());

        route(app, request);

        assertEquals(1, model.getWhaleStore().getAllWhales().size());

    }



    //Test for creating whales through form actions
    @Test
    public void testAddValidWhale() {
        cleanup();

        //Create an observation
        ObservationBuilder observationBuilder = new ObservationBuilder()
                .atLocation("Sea")
                .observedAt(LocalDateTime.now());

        Observation observation = observationBuilder.build();
        model.getObservationStore().addObservationToStore(observation);

        Map<String, String[]> validWhaleResponse = Map.of(
                "gender", new String[]{String.valueOf(Whale.Gender.FEMALE)},
                "estimatedWeight", new String[]{"1000"},
                "species", new String[]{String.valueOf(Whale.Species.ORCA)}
                );

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri(String.format("/observation/%d/whale", observation.getId()))
                .header("accept", HTML)
                .bodyFormArrayValues(validWhaleResponse);

        assertEquals(0, model.getWhaleStore().getAllWhales().size());

        Result result = route(app, request);

        assertEquals(303, result.status());

        assertEquals(1, model.getWhaleStore().getAllWhales().size());

        Whale newWhale = model.getWhaleStore().getAllWhales().get(0);

        assertEquals(Whale.Gender.FEMALE, newWhale.getGender());
        assertEquals(Whale.Species.ORCA, newWhale.getSpecies());
        assertEquals(1000, newWhale.getEstimatedWeight());
    }


}
