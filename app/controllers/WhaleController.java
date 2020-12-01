package controllers;

import play.filters.csrf.CSRF;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Optional;

import static play.mvc.Results.ok;

public class WhaleController {
  public Result create(Http.Request request) {

    //TODO: create the whale
    //TODO: then call the relevant view code
    System.out.println(request.contentType());
    Optional<CSRF.Token> token = CSRF.getToken(request);
    System.out.println(token);
    return ok();
  }
}
