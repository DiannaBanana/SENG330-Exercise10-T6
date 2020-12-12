package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Whale;
import models.WhaleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.Util;

import javax.inject.Inject;
import java.util.List;

public class WhaleAPIController extends Controller{
    private final WhaleModel activeModel;
    private final Logger accessLogger = LoggerFactory.getLogger("requests");

    @Inject
    public WhaleAPIController(WhaleModel model) {
        this.activeModel = model;
    }

   public Result listWhales(Http.Request request){
        if (request.queryString().size() > 0){
            accessLogger.warn("API access with unknown get parameters: " + request.queryString());
            return status(422, Util.createResponse("Unknown get parameters", false));
        }
       List<Whale> listOfWhales = activeModel.getWhaleStore().getAllWhales();
       ObjectMapper mapper = new ObjectMapper();
       JsonNode jsonData = mapper.convertValue(listOfWhales, JsonNode.class);
       accessLogger.info("Served JSON api endpoint successfully");
       return ok(Util.createResponse(jsonData, true));
   }

   public Result guide(){
       accessLogger.info("Rendering API guide");
       return ok(views.html.api_info.render());
   }
}