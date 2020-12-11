package controllers;

import models.Observation;
import models.Whale;
import models.WhaleModel;
import play.api.http.MediaRange;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;

import static play.mvc.Http.MimeTypes.JSON;

public class WhaleController extends Controller {
  private FormFactory formFactory;
  private MessagesApi messages;
  private Form<WhaleData> form;
  private WhaleModel activeModel;

  @Inject
  public WhaleController(FormFactory f, MessagesApi messages, WhaleModel model){
    formFactory = f;
    this.messages = messages;
    form = formFactory.form(WhaleData.class);
    activeModel = model;
  }


  public Result handleResult(Http.Request request, Long obsId) {
    Form<WhaleData> filledForm = form.bindFromRequest(request);

    if (!filledForm.hasErrors()) {
      try {
        WhaleData temp = filledForm.get();
        Whale whale = new Whale(temp.getSpecies(), temp.getEstimatedWeight(), temp.getGender());


        Optional<Observation> obsWrapper = activeModel.getObservationStore().getObservationById(obsId);
        obsWrapper.ifPresent(observation -> observation.getWhales().add(whale));
      } catch (Exception e){
        e.printStackTrace();
      }
      return redirect(routes.ObservationController.showObservation(obsId));
    } else {
      return ok(filledForm.errorsAsJson());
    }
  }

  public Result removeWhale(Long obsId, Long whaleId){
    Optional<Observation> observationOptional = activeModel.getObservationStore().getObservationById(obsId);

    observationOptional.ifPresent(observation -> observation.getWhales().removeIf(w -> w.getId().equals(whaleId)));

    return redirect(routes.ObservationController.showObservation(obsId));
  }

  public Result manageRequestType(Http.Request request){
    if (request.acceptedTypes().stream().map(MediaRange::toString).anyMatch(x -> x.equalsIgnoreCase(JSON))) {
      return new WhaleAPI(activeModel).listWhales(request);
    } else {
      return redirect(routes.SearchController.search());
    }
  }


}
