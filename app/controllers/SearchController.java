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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;



public class SearchController extends Controller {

    private final FormFactory formFactory;
    private final MessagesApi me;
    private final Form<ObservationData> observationDataForm;
    private final Form<WhaleData> whaleDataForm;
    private final WhaleModel activeModel;


    @Inject
    public SearchController(FormFactory f, MessagesApi messagesApi, WhaleModel model) {
        formFactory = f;
        observationDataForm = formFactory.form(ObservationData.class);
        whaleDataForm = formFactory.form(WhaleData.class);
        me = messagesApi;
        activeModel = model;
    }

    public Result searchObservation(Http.Request r, String keyword) {
        int obsId = 0;
        List<Observation> obs= activeModel.getObservationStore().getObservations();
        List<Integer> matchId = new ArrayList<>();
        if(keyword.matches("\\d{4}-\\d{2}-\\d{2}")){
            LocalDate date = LocalDate.parse(keyword);
            System.out.println("date string : " + keyword +", " + "localdate : " + date);
            for(int k = 0; k < obs.size(); k++){
                if(obs.get(k).getTime().toLocalDate().isEqual(date)){
                    System.out.println(obs.get(k).getTime().toLocalDate());
                    obsId=k;
                    matchId.add(k);
                }
            }
        }else{
            for(int i = 0; i < obs.size(); i++){
                Set<Whale> whales = obs.get(i).getWhales();
                List<Whale> whale_list = new ArrayList<>();
                whale_list.addAll(whales);
                for(int j = 0; j < whale_list.size(); j++){
                    if(whale_list.get(j).getSpecies().equalsIgnoreCase(keyword)){
                        obsId = i;
                        matchId.add(i);
                    }
                }
            }
        }

        Optional<Observation> observation = activeModel.getObservationStore().getObservationById(obsId);

        if (observation.isPresent()) {
            return ok(views.html.observationDetail.render(observation.get(), whaleDataForm, r, me.preferred(r)));
        }

        return redirect(routes.Driver.index());
    }
}

