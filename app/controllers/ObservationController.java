package controllers;

import models.Observation;
import models.WhaleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;

import static play.mvc.Http.HttpVerbs.GET;

public class ObservationController extends Controller {

    private final MessagesApi me;
    private final Form<ObservationData> observationDataForm;
    private final Form<WhaleData> whaleDataForm;
    private final WhaleModel activeModel;
    private final Logger accessLogger = LoggerFactory.getLogger("requests");



    @Inject
    public ObservationController(FormFactory f, MessagesApi messagesApi, WhaleModel model) {
        observationDataForm = f.form(ObservationData.class);
        whaleDataForm = f.form(WhaleData.class);
        me = messagesApi;
        activeModel = model;
    }

    public Result showObservation(Http.Request r, Long obsId) {
        Optional<Observation> observation = activeModel.getObservationStore().getObservationById(obsId);

        if (observation.isPresent()) {
            accessLogger.info("Rendering observation " + obsId);
            return ok(views.html.observationDetail.render(observation.get(), whaleDataForm, r, me.preferred(r)));
        }

        accessLogger.warn("Client attempted to access observation " + obsId + " which did not exist");
        return redirect(routes.Driver.index());
    }


    public Result createObservation(Http.Request r) {
        Form<ObservationData> filledForm = observationDataForm.bindFromRequest(r);


        //The first time the form is shown the error messages should not appear.
        if (r.method().equals(GET)) {
            accessLogger.info("Rendering observation form");
            return ok(views.html.createObservationForm.render(observationDataForm, r, me.preferred(r)));
        }

        if(filledForm.hasErrors()){
            accessLogger.info("Client submitted observation with errors");
            return ok(views.html.createObservationForm.render(filledForm, r, me.preferred(r)));
        }

        try {
            ObservationData filledData = filledForm.get();
            Observation o = new Observation(filledData.parsedTime(), filledData.getLocation());
            activeModel.getObservationStore().addObservation(o);
            return redirect(routes.ObservationController.showObservation(o.getId()));
        } catch (Exception e) {
            accessLogger.error(e.getMessage());
            return ok(views.html.createObservationForm.render(filledForm, r, me.preferred(r)));
        }

    }


}

