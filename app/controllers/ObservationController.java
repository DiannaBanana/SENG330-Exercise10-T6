package controllers;

import models.Observation;
import models.WhaleModel;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;

import javax.inject.Inject;
import java.util.Optional;

public class ObservationController extends Controller {

    private final FormFactory formFactory;
    private final MessagesApi me;
    private final Form<ObservationData> observationDataForm;
    private final Form<WhaleData> whaleDataForm;
    private final WhaleModel activeModel;


    @Inject
    public ObservationController(FormFactory f, MessagesApi messagesApi, WhaleModel model){
        formFactory = f;
        observationDataForm = formFactory.form(ObservationData.class);
        whaleDataForm = formFactory.form(WhaleData.class);
        me = messagesApi;
        activeModel = model;
    }

    public Result showObservation(Http.Request r, Long obsId){
        Optional<Observation> observation = activeModel.getObservationStore().getObservationById(obsId);

        if(observation.isPresent()){
            return ok(views.html.observationDetail.render(observation.get(), whaleDataForm, r, me.preferred(r)));
        }

        return redirect(routes.Driver.index());
    }

    public Result createObservation(Http.Request r){
        Form<ObservationData> filledForm = observationDataForm.bindFromRequest(r);

        if (filledForm.hasErrors()) {
            return ok(filledForm.errorsAsJson());
        } else {
            if(filledForm.value().isEmpty()){
                return ok(views.html.createObservationForm.render(observationDataForm, r, me.preferred(r)));
            }
            try {
                ObservationData filledData = filledForm.get();
                Observation o = new Observation(filledData.parsedTime(), filledData.getLocation());
                activeModel.getObservationStore().addObservationToStore(o);
                return redirect(routes.ObservationController.showObservation(o.getId()));
            } catch (Exception e){
                e.printStackTrace();
                return ok(views.html.createObservationForm.render(observationDataForm, r, me.preferred(r)));
            }
        }
    }



}

