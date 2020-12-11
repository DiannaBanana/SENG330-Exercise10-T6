package controllers;

import models.Observation;
import models.Whale;
import models.WhaleModel;
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

    private final FormFactory formFactory;
    private final MessagesApi me;
    private final Form<SearchData> searchDataForm;
    private final WhaleModel activeModel;


    @Inject
    public SearchController(FormFactory f, MessagesApi messagesApi, WhaleModel model) {
        formFactory = f;
        searchDataForm = formFactory.form(SearchData.class);
        me = messagesApi;
        activeModel = model;
    }

    public Result search(Http.Request r){

        List<Observation> observations = activeModel.getObservationStore().getObservations();
        Form<SearchData> filledForm = searchDataForm.bindFromRequest(r);

        if (r.method().equals(GET)){
            return ok(views.html.whale_aggregations.render(observations, (ob, w) -> true, searchDataForm, r, me.preferred(r)));
        }

        try {
            SearchData s = filledForm.get();
            BiPredicate<Observation, Whale> speciesFilter = (ob, w) -> true;
            BiPredicate<Observation, Whale> dateFilter = (ob, w) -> true;

            if(!s.getSpecies().equals("")) {
                speciesFilter = (ob, w) -> s.parseSpecies().equals(w.getSpecies());
            }

            if(s.isDateValid()){
                dateFilter = (ob, w) -> s.getParsedTime().truncatedTo(ChronoUnit.DAYS).isEqual(ob.getTime().truncatedTo(ChronoUnit.DAYS));
            }

            BiPredicate<Observation, Whale> finalDateFilter = dateFilter;
            BiPredicate<Observation, Whale> finalSpeciesFilter = speciesFilter;
            return ok(views.html.whale_aggregations.render(observations, (ob, w) -> finalSpeciesFilter.test(ob, w) && finalDateFilter.test(ob, w), filledForm, r, me.preferred(r)));
        } catch (Exception e){
            e.printStackTrace();
            return ok(views.html.whale_aggregations.render(observations, (ob, w) -> true, filledForm, r, me.preferred(r)));
        }
    }


}

