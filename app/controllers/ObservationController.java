package controllers;

import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;

import javax.inject.Inject;

public class ObservationController extends Controller {

    private FormFactory formFactory;
    private MessagesApi me;


    @Inject
    public ObservationController(FormFactory f, MessagesApi messagesApi){
        formFactory = f;
        me = messagesApi;
    }

    public Result test(Http.Request r){
        return ok("Fun times");
    }

}

