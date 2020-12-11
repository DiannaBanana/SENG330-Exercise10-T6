package controllers;

import models.WhaleModel;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

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


    public Result index(Http.Request r) {
        return ok(views.html.search.render(searchDataForm, r, me.preferred(r)));
    }

    public Result search(Http.Request r){
        Form<SearchData> filledForm = searchDataForm.bindFromRequest(r);
        try {
            SearchData s = filledForm.get();
            return ok(s.toString());
        } catch (Exception e){
            e.printStackTrace();
            return ok();
        }
    }


}

