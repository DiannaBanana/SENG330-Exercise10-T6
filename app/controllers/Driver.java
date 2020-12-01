package controllers;

import models.Observation;
import models.Whale;
import models.WhaleModel;
import play.mvc.Controller;
import play.mvc.Result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class Driver extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {

        Whale whale = new Whale("grey", 1000, "m");
        Observation observation = new Observation(LocalDateTime.now(), "my place");
        observation.getWhales().add(whale);
        WhaleModel.getInstance().getObservationStore().addObservationToStore(observation);

        List<Whale> whales = WhaleModel.getInstance().getObservationStore().getObservations().stream()
                .flatMap(observation1 -> observation1.getWhales().stream())
                .collect(Collectors.toList());

        return ok(views.html.index.render("Group 6", whales));
    }
    

}
