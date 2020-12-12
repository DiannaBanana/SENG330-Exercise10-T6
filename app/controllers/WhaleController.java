package controllers;

import models.Observation;
import models.Whale;
import models.WhaleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.api.http.MediaRange;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;

import static play.mvc.Http.MimeTypes.JSON;

public class WhaleController extends Controller {
  private final Form<WhaleData> form;
  private final WhaleModel activeModel;
  private final Logger accessLogger = LoggerFactory.getLogger("requests");
  private final Logger modelLog = LoggerFactory.getLogger(WhaleModel.class);


  @Inject
  public WhaleController(FormFactory f, WhaleModel model){
    form = f.form(WhaleData.class);
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


        if(obsWrapper.isEmpty()){
          modelLog.warn("Tried to add whale to non existent observation id:" + obsId);
        } else {
          modelLog.debug("Added whale " + whale.getId() + " to observation " + obsId);
        }


      } catch (Exception e){
        accessLogger.error("Error while adding whale to observation" + e);
      }
    }
    return redirect(routes.ObservationController.showObservation(obsId));
  }

  public Result removeWhale(Long obsId, Long whaleId){
    Optional<Observation> observationOptional = activeModel.getObservationStore().getObservationById(obsId);

    observationOptional.ifPresent(observation -> observation.getWhales().removeIf(w -> w.getId().equals(whaleId)));

    if(observationOptional.isEmpty()){
      modelLog.warn("Tried to remove whale from non-existent observation id: " + obsId);
    } else {
      modelLog.info("Removed whale " + whaleId + " from observation " + obsId);
    }

    return redirect(routes.ObservationController.showObservation(obsId));
  }

  public Result manageRequestType(Http.Request request){

    //This is required because most browsers accept anything */* so a call to <code>request.accepts()</code>
    //always is true
    if (request.acceptedTypes().stream().map(MediaRange::toString).anyMatch(x -> x.equalsIgnoreCase(JSON))) {
      return new WhaleAPI(activeModel).listWhales(request);
    } else {
      return redirect(routes.SearchController.search());
    }
  }


}
