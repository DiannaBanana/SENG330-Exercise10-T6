package controllers;

import models.Observation;
import models.WhaleModel;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;

import javax.inject.Inject;
import java.util.Optional;

public class ObservationController extends Controller {

    private FormFactory formFactory;
    private MessagesApi me;


    @Inject
    public ObservationController(FormFactory f, MessagesApi messagesApi){
        formFactory = f;
        me = messagesApi;
    }

    public Result showObservation(Http.Request r, Long obsId){
        Optional<Observation> observation = WhaleModel.getInstance().getObservationStore().getObservationById(obsId);

        if(observation.isPresent()){
            return ok(views.html.observationDetail.render(observation.get()));
        }

        return ok("That observation does not exist");
    }

}

