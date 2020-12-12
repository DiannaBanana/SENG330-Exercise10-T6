package controllers;

import databuilders.ObservationBuilder;
import models.Observation;
import models.Whale;
import models.WhaleModel;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import play.Application;
import play.api.test.CSRFTokenHelper;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.Assert.*;
import static play.mvc.Http.MimeTypes.HTML;
import static play.test.Helpers.*;

public class SearchControllerTest extends WithApplication {
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
    public void renderInvalidSearch(){
        cleanup();

        Whale testWhale = new Whale(Whale.Species.BELUGA, 911, Whale.Gender.FEMALE);

        ObservationBuilder observationBuilder = new ObservationBuilder()
                .observedAt(LocalDateTime.of(1999, 10, 12, 10, 12)).atLocation("North Pole")
                .withWhale(testWhale);

        Observation observation = observationBuilder.build();
        model.getObservationStore().addObservation(observation);

        Map<String, String[]> validObservation = Map.of(
                "Species", new String[]{"9Beluga"},
                "time", new String[]{"lp/10/12 10:12"});

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/search")
                .header("accept", HTML)
                .bodyFormArrayValues(validObservation);

        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = route(app, request);

        assertEquals(OK, result.status());
        assertEquals(0, count(Helpers.contentAsString(result), "tr "));
    }

    @Test
    public void renderSearchNotFound(){
        cleanup();

        Whale testWhale = new Whale(Whale.Species.FIN, 911, Whale.Gender.FEMALE);
        Whale testWhale2 = new Whale(Whale.Species.BELUGA, 5, Whale.Gender.MALE);

        ObservationBuilder observationBuilder = new ObservationBuilder()
                .observedAt(LocalDateTime.now()).atLocation("North Pole")
                .withWhale(testWhale).withWhale(testWhale2);

        Observation observation = observationBuilder.build();
        model.getObservationStore().addObservation(observation);

        Map<String, String[]> validObservation = Map.of(
                "Species", new String[]{"Beluga"},
                "time", new String[]{"1999/10/12 10:12"});

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/search")
                .header("accept", HTML)
                .bodyFormArrayValues(validObservation);

        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = route(app, request);

        assertEquals(OK, result.status());
        assertEquals(0, count(Helpers.contentAsString(result), "tr "));
    }

    @Test
    public void renderValidSearchBySpecies(){
        cleanup();

        Whale testWhale = new Whale(Whale.Species.FIN, 911, Whale.Gender.FEMALE);
        Whale testWhale2 = new Whale(Whale.Species.ORCA, 5, Whale.Gender.MALE);

        ObservationBuilder observationBuilder = new ObservationBuilder()
                .observedAt(LocalDateTime.now()).atLocation("North Pole")
                .withWhale(testWhale).withWhale(testWhale2);

        Observation observation = observationBuilder.build();
        model.getObservationStore().addObservation(observation);

        Map<String, String[]> validSearch = Map.of(
                "Species", new String[]{"Fin"},
                "time", new String[]{"____/__/__ __:__"});

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/search")
                .header("accept", HTML)
                .bodyFormArrayValues(validSearch);

        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = route(app, request);

        assertEquals(OK, result.status());
        assertEquals(1, count(Helpers.contentAsString(result), "tr "));
    }

    @Test
    public void renderValidSearchByDate(){
        cleanup();

        Whale testWhale = new Whale(Whale.Species.FIN, 911, Whale.Gender.FEMALE);
        Whale testWhale2 = new Whale(Whale.Species.ORCA, 5, Whale.Gender.MALE);

        ObservationBuilder observationBuilder = new ObservationBuilder()
                .observedAt(LocalDateTime.now()).atLocation("North Pole")
                .withWhale(testWhale);

        ObservationBuilder observationBuilder2 = new ObservationBuilder()
                .observedAt(LocalDateTime.of(1999, 10, 12, 10, 12)).atLocation("Sahara Desert")
                .withWhale(testWhale2);

        Observation observation = observationBuilder.build();
        Observation observation2 = observationBuilder2.build();
        model.getObservationStore().addObservation(observation);
        model.getObservationStore().addObservation(observation2);

        Map<String, String[]> validObservation = Map.of(
                "species", new String[]{""},
                "time", new String[]{LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))});

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/search")
                .header("accept", HTML)
                .bodyFormArrayValues(validObservation);

        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = route(app, request);

        assertEquals(OK, result.status());
        assertEquals(1, count(Helpers.contentAsString(result), "tr "));
    }

    @Test
    public void renderValidSearchBySpeciesAndDate(){
        cleanup();

        Whale testWhale = new Whale(Whale.Species.FIN, 911, Whale.Gender.FEMALE);
        Whale testWhale2 = new Whale(Whale.Species.HUMPBACK, 5, Whale.Gender.MALE);
        Whale testWhale3 = new Whale(Whale.Species.ORCA, 50, Whale.Gender.MALE);
        Whale testWhale4 = new Whale(Whale.Species.GRAY, 150, Whale.Gender.FEMALE);

        ObservationBuilder observationBuilder = new ObservationBuilder()
                .observedAt(LocalDateTime.now()).atLocation("North Pole")
                .withWhale(testWhale).withWhale(testWhale2);

        ObservationBuilder observationBuilder2 = new ObservationBuilder()
                .observedAt(LocalDateTime.of(1999, 10, 12, 10, 12)).atLocation("Victoria")
                .withWhale(testWhale3).withWhale(testWhale4);

        Observation observation = observationBuilder.build();
        Observation observation2 = observationBuilder2.build();
        model.getObservationStore().addObservation(observation);
        model.getObservationStore().addObservation(observation2);

        Map<String, String[]> validObservation = Map.of(
                "Species", new String[]{"Orca"},
                "time", new String[]{"1999/10/12 10:12"});

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/search")
                .header("accept", HTML)
                .bodyFormArrayValues(validObservation);

        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = route(app, request);

        assertEquals(OK, result.status());
        assertEquals(1, count(Helpers.contentAsString(result), "tr "));
    }

    public static int count(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }

}
