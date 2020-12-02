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

import java.time.LocalDateTime;

import static play.mvc.Results.*;

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


  public Result create(Http.Request request) {
    return ok(views.html.form.render(form, request, messages.preferred(request)));
  }

  public Result handleResult(Http.Request request) {
    Form<WhaleData> filledForm = form.bindFromRequest(request);

    if (filledForm.hasErrors()) {
      return ok("Error sad");
    } else {
      try {
        WhaleData temp = filledForm.get();
        Whale whale = new Whale(temp.getSpecies(), 1000, "m");
        Observation observation = new Observation(LocalDateTime.now(), temp.getLocation());
        observation.getWhales().add(whale);
        WhaleModel.getInstance().getObservationStore().addObservationToStore(observation);
      } catch (Exception e){
        e.printStackTrace();
      }
      return redirect(routes.Driver.index());
    }
  }

}
