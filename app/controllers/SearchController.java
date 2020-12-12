package controllers;

import models.Observation;
import models.Whale;
import models.WhaleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.BiPredicate;

import static play.mvc.Http.HttpVerbs.GET;

public class SearchController extends Controller {

    private final MessagesApi me;
    private final Form<SearchData> searchDataForm;
    private final WhaleModel activeModel;
    private final Logger accessLogger = LoggerFactory.getLogger("requests");
    private final Logger classLogger = LoggerFactory.getLogger(getClass());


    @Inject
    public SearchController(FormFactory f, MessagesApi messagesApi, WhaleModel model) {
        searchDataForm = f.form(SearchData.class);
        me = messagesApi;
        activeModel = model;
    }

    public Result search(Http.Request r){

        List<Observation> observations = activeModel.getObservationStore().getObservations();
        Form<SearchData> filledForm = searchDataForm.bindFromRequest(r);

        if (r.method().equals(GET)){
            accessLogger.info("Rendering blank whale search");
            return ok(views.html.whale_aggregations.render(observations, (ob, w) -> true, searchDataForm, r, me.preferred(r)));
        }

        try {
            SearchData s = filledForm.get();
            BiPredicate<Observation, Whale> speciesFilter = (ob, w) -> true;
            BiPredicate<Observation, Whale> dateFilter = (ob, w) -> true;

            if(!s.getSpecies().equals("")) {
                speciesFilter = (ob, w) -> s.parseSpecies().equals(w.getSpecies());
                classLogger.debug("Filtering by species with value: " + s.getSpecies());
            }

            if(s.isDateValid()){
                dateFilter = (ob, w) -> s.getParsedTime().truncatedTo(ChronoUnit.DAYS).isEqual(ob.getTime().truncatedTo(ChronoUnit.DAYS));
                classLogger.debug("Filtering by date with value: " + s.getTime());
            }

            BiPredicate<Observation, Whale> finalDateFilter = dateFilter;
            BiPredicate<Observation, Whale> finalSpeciesFilter = speciesFilter;

            accessLogger.info("Rendering filtered data");
            return ok(views.html.whale_aggregations.render(observations, (ob, w) -> finalSpeciesFilter.test(ob, w) && finalDateFilter.test(ob, w), filledForm, r, me.preferred(r)));
        } catch (Exception e){
            classLogger.error("Error in search: " + e.getMessage());
            accessLogger.info("Rendering blank whale search");
            return ok(views.html.whale_aggregations.render(observations, (ob, w) -> true, filledForm, r, me.preferred(r)));
        }
    }


}

