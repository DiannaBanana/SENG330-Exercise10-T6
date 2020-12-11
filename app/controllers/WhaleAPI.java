package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Whale;
import models.WhaleModel;
import play.mvc.Controller;
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

   public Result listWhales(){
       List<Whale> listOfWhales = activeModel.getWhaleStore().getAllWhales();
       ObjectMapper mapper = new ObjectMapper();
       JsonNode jsonData = mapper.convertValue(listOfWhales, JsonNode.class);
       return ok(Util.createResponse(jsonData, true));
   }
}