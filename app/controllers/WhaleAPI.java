package controllers;

import models.Whale;
import models.Observation;
import models.WhaleModel;

import utils.Util;
import java.util.ArrayList;

import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.Optional;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WhaleAPI extends Controller{
    private HttpExecutionContext ec;

    @Inject
    public WhaleAPI(HttpExecutionContext ec) {
        this.ec = ec;
    }

   public CompletionStage<Result> listWhales(){
       return supplyAsync(() -> {
            List<Observation> listOfObservations = WhaleModel.getInstance().getObservationStore().getObservations();
            List<Whale> listOfWhales = new ArrayList<>();
            for (Observation o : listOfObservations ) {
                listOfWhales.addAll(o.getWhales());
            }
           ObjectMapper mapper = new ObjectMapper();
           JsonNode jsonData = mapper.convertValue(listOfWhales, JsonNode.class);
           return ok(Util.createResponse(jsonData, true));
       }, ec.current());
   }
}