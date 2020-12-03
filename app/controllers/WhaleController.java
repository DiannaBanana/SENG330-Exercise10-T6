package controllers;

import models.Observation;
import models.Whale;
import models.WhaleModel;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;

import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

public class WhaleController {
  private FormFactory formFactory;
  private MessagesApi messages;
  private Form<WhaleData> form;

  @Inject
  public WhaleController(FormFactory f, MessagesApi messages){
    formFactory = f;
    this.messages = messages;
    form = formFactory.form(WhaleData.class);
  }


  public Result handleResult(Http.Request request, Long obsId) {
    Form<WhaleData> filledForm = form.bindFromRequest(request);

    if (filledForm.hasErrors()) {
      return ok(filledForm.errorsAsJson());
    } else {
      try {
        WhaleData temp = filledForm.get();
        Whale whale = new Whale(temp.getSpecies(), temp.getEstimatedWeight(), temp.getGender());


        Optional<Observation> obsWrapper = WhaleModel.getInstance().getObservationStore().getObservationById(obsId);
        obsWrapper.ifPresent(observation -> observation.getWhales().add(whale));
      } catch (Exception e){
        e.printStackTrace();
      }
      return redirect(routes.ObservationController.showObservation(obsId));
    }
  }

  public Result removeWhale(Long obsId, Long whaleId){
    Optional<Observation> observationOptional = WhaleModel.getInstance().getObservationStore().getObservationById(obsId);

    observationOptional.ifPresent(observation -> observation.getWhales().removeIf(w -> w.getId().equals(whaleId)));

    return redirect(routes.ObservationController.showObservation(obsId));
  }

}
