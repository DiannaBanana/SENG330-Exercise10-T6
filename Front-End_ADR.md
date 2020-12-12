#Front End implementation

# Title

## Status

Accepted

## Context

The front-end implementation of a web application is a key aspect and contains much of the code for a web app. Our front-end must adhere to the general object oriented principles 
and design patterns that we have been practicing during this term, thus our decisions must reflect this requirement. As we are creating an application from scratch, and as it is 
the presentation of the development teams hard work, it is important to choose the correct framework and technologies in order to achieve a design that is not only suitable 
for the application, but also highlights its features. 

## Decision

In order to implement an MVC-based design pattern for structuring our user interface as part of our play web application, we chose to use jQuery as a front-end 
framework along with HTML and CSS to achieve the design that the team envisioned.

What is the change that we're proposing and/or doing?

jQuery was used to enhance the functionality of the web app by adding plugins to the front end to aid use experience and error
handling. The first feature we implemented was table sorting. Using the [tablesorter plugin](https://mottie.github.io/tablesorter/docs/)
we added the ability for users sort both tables containing info on Observations and Whales by whichever field was desired.
This is critical for users to aggregate data and to find things quickly. While searching is useful, sorting by date is
far more likely to yield critical insights into the data. 

jQuery was also used to parse the local date fields. There is an input of type datetime-local but it is not supported in
Firefox. In order to ensure cross browser compatibility, we went with the W3C recommended solution of using the 
[pickadate](https://www.jqueryscript.net/time-clock/Lightweight-jQuery-Date-Input-Picker.html) api to ensure cross
browser support for date entry. One of the primary reasons to use a plugin instead of just accepting text for the date
was to ensure that the dates would be returned to the server in a reliable format. This means that we can always expect
dates in YYYY/MM/DD HH:MM format and parse them accordingly.

Javascript key event handling was a key component of validating user input. Particularly for the Whale form. For the 
whale entry form, drop down menus were used to ensure that the input to the system would match one of the enums in the
model. However, for the estimated weight, we wanted to ensure that the value was greater than or equal to 0. Unlike the
observation for which has room to expand vertically and print error messages, there was no space within a Whale card.
As such, on keypress the field is validated. If the field only contains a positive integer then the submit button
is activated. If it does not, then the field is greyed out until it is valid. One interesting bug occurred when a user
would type a letter and press enter simultaneously. In this case, we added extra javascript before the form could submit.
This ensures that even if an extraneous submit event fires with bad text it will be rejected, and the button will be greyed out.



## Consequences

Using jQuery makes it much easier to have external plugins add functionality to the website. In the future, we would like
to add google maps API functionality to the locations, so that users locations can be more easily collated into useful
results by making queries such as filtering by a radius. Using form validation on the client side reduces the need for
time-consuming server calls, especially in the cases where clients may be on mobile with low data rates.