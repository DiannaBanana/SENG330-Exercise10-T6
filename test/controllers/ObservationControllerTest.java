package controllers;

import databuilders.ObservationBuilder;
import models.Observation;
import models.WhaleModel;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import play.Application;
import play.filters.csrf.CSRF;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.mvc.Http.MimeTypes.HTML;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.*;

public class ObservationControllerTest extends WithApplication {

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
    public void renderValidObservation(){
        cleanup();

        ObservationBuilder observationBuilder = new ObservationBuilder()
                .observedAt(LocalDateTime.now())
                .atLocation("Home");

        Observation observation = observationBuilder.build();
        model.getObservationStore().addObservation(observation);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(String.format("/observation/%d", observation.getId()))
                .header("accept", HTML);

        Result result = route(app, request);

        assertEquals(OK, result.status());
        assertEquals(HTML, result.contentType().get());

    }

    @Test
    public void redirectInvalidObservation(){
        cleanup();

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(String.format("/observation/%d", 10))
                .header("accept", HTML);

        Result result = route(app, request);

        assertEquals(SEE_OTHER, result.status());
    }

    @Test
    public void testCreateValidObservation(){
        cleanup();

        Map<String, String[]> validWhaleResponse = Map.of(
                "location", new String[]{"the ocean"},
                "time", new String[]{"1999/10/12 10:12"});

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/observation")
                .header("accept", HTML)
                .bodyFormArrayValues(validWhaleResponse);

        Result result = route(app, request);

        assertEquals(SEE_OTHER, result.status());

        assertEquals(1, model.getObservationStore().getObservations().size());
        Observation created = model.getObservationStore().getObservations().get(0);

        assertEquals(0, created.getWhales().size());
        assertEquals("the ocean", created.getLocation());
        assertEquals(LocalDateTime.of(1999, 10, 12, 10, 12), created.getTime());
    }

    @Test
    public void testCreateInvalidDateObservation(){
        cleanup();

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/observation")
                .header("accept", HTML);

        Map<String, String[]> validWhaleResponse = Map.of(
                "location", new String[]{"the ocean"},
                "time", new String[]{"90/10/12 10:12"},
                "csrf", new String[]{String.valueOf(CSRF.getToken(request.build()).get())});

        request.bodyFormArrayValues(validWhaleResponse);

        Result result = route(app, request);

        assertEquals(OK, result.status());

        assertEquals(0, model.getObservationStore().getObservations().size());
    }




}
