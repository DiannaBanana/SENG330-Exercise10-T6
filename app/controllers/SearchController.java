package controllers;

import models.Observation;
import models.WhaleModel;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;

public class SearchController extends Controller {

    private final FormFactory formFactory;
    private final MessagesApi me;
    private final Form<ObservationData> observationDataForm;
    private final Form<WhaleData> whaleDataForm;
    private final WhaleModel activeModel;


    @Inject
    public SearchController(FormFactory f, MessagesApi messagesApi, WhaleModel model) {
        formFactory = f;
        observationDataForm = formFactory.form(ObservationData.class);
        whaleDataForm = formFactory.form(WhaleData.class);
        me = messagesApi;
        activeModel = model;
    }

    public Result searchObservation(Http.Request r, Long obsId) {
        Optional<Observation> observation = activeModel.getObservationStore().getObservationById(obsId);

        if (observation.isPresent()) {
            return ok(views.html.observationDetail.render(observation.get(), whaleDataForm, r, me.preferred(r)));
        }

        return redirect(routes.Driver.index());
    }

}

