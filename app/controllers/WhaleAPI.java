package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Whale;
import models.WhaleModel;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Util;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class WhaleAPI extends Controller{
    private HttpExecutionContext ec;
    private WhaleModel activeModel;

    @Inject
    public WhaleAPI(HttpExecutionContext ec, WhaleModel model) {
        this.ec = ec;
        this.activeModel = model;
    }

   public CompletionStage<Result> listWhales(){
       return supplyAsync(() -> {
           List<Whale> listOfWhales = activeModel.getWhaleStore().getAllWhales();
           ObjectMapper mapper = new ObjectMapper();
           JsonNode jsonData = mapper.convertValue(listOfWhales, JsonNode.class);
           return ok(Util.createResponse(jsonData, true));
       }, ec.current());
   }
}