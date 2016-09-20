$(document).on 'show', '#style-accordion', (evt) ->
  $('.accordion-heading .flag', $(evt.target).parent()).removeClass('icon-flag-close');
  $('.accordion-heading .flag', $(evt.target).parent()).addClass('icon-flag-open');

$(document).on 'hide', '#style-accordion', (evt) ->
  $('.accordion-heading .flag', $(evt.target).parent()).removeClass('icon-flag-open');
  $('.accordion-heading .flag', $(evt.target).parent()).addClass('icon-flag-close');
