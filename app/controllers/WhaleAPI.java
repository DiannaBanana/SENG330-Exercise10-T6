package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Whale;
import models.WhaleModel;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.Util;

import javax.inject.Inject;
import java.util.List;

public class WhaleAPI extends Controller{
    private final WhaleModel activeModel;

    @Inject
    public WhaleAPI(WhaleModel model) {
        this.activeModel = model;
    }

   public Result listWhales(Http.Request request){
        if (request.queryString().size() > 0){
            return status(422, Util.createResponse("Unknown get parameters", false));
        }
       List<Whale> listOfWhales = activeModel.getWhaleStore().getAllWhales();
       ObjectMapper mapper = new ObjectMapper();
       JsonNode jsonData = mapper.convertValue(listOfWhales, JsonNode.class);
       return ok(Util.createResponse(jsonData, true));
   }

   public Result guide(){
        return ok(views.html.api_info.render());
   }
}