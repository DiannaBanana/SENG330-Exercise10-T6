package controllers;

import databuilders.ObservationBuilder;
import models.Observation;
import models.WhaleModel;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static play.mvc.Http.MimeTypes.HTML;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

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


}
