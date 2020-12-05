package controllers;

import models.Whale;
import models.SimpleHashStore;

import utils.Util;

import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WhaleAPI extends Controller{
    private HttpExecutionContext ec;
    private SimpleHashStore simpleWhaleStore;

    @Inject
    public WhaleAPI(HttpExecutionContext ec, SimpleHashStore simpleWhaleStore) {
        this.simpleWhaleStore = simpleWhaleStore;
        this.ec = ec;
    }

    public CompletionStage<Result> listWhales(){
        return supplyAsync(() -> {
            List<Whale> listWhales = simpleWhaleStore.getAllWhales();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonData = mapper.convertValue(listWhales, JsonNode.class);
            return ok(Util.createResponse(jsonData, true));
        }, ec.current());
    }

}