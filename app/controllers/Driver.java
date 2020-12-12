package controllers;

import models.WhaleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class Driver extends Controller {

    private final WhaleModel activeModel;
    private final Logger accessLogger = LoggerFactory.getLogger("requests");

    @Inject
    public Driver(WhaleModel w){
        activeModel = w;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        accessLogger.info("Homepage loaded");
        return ok(views.html.index.render( activeModel.getObservationStore().getObservations()));
    }


    public Result credits() {
        accessLogger.info("Credits loaded");
        return ok(views.html.credits.render());
    }
    

}
