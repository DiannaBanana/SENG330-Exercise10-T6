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

    private FormFactory formFactory;
    private MessagesApi me;
    private Form<ObservationData> observationDataForm;
    private Form<WhaleData> whaleDataForm;


    @Inject
    public ObservationController(FormFactory f, MessagesApi messagesApi){
        formFactory = f;
        observationDataForm = formFactory.form(ObservationData.class);
        whaleDataForm = formFactory.form(WhaleData.class);
        me = messagesApi;
    }

    public Result showObservation(Http.Request r, Long obsId){
        Optional<Observation> observation = WhaleModel.getInstance().getObservationStore().getObservationById(obsId);

        if(observation.isPresent()){
            return ok(views.html.observationDetail.render(observation.get(), whaleDataForm, r, me.preferred(r)));
        }

        return ok("That observation does not exist");
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
                WhaleModel.getInstance().getObservationStore().addObservationToStore(o);
                return redirect(routes.Driver.index());
            } catch (Exception e){
                e.printStackTrace();
                return ok(views.html.createObservationForm.render(observationDataForm, r, me.preferred(r)));
            }
        }
    }



}

