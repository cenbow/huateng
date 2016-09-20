defaultTemplate = Handlebars.compile """
  <div class="fake-select {{class}}" tabindex="0">
    <input type="hidden" class="fake-select-input" name="{{name}}" value="{{value}}"/>
    <span class="fake-select-current">{{selectText}}</span><span class="fake-select-caret"></span>
    <ul class="fake-select-dropdown">
      {{#each options}}
        <li {{#equals value ../value}}class="fake-select-checked"{{/equals}}><a href="#" data-value="{{value}}">{{text}}</a></li>
      {{/each}}
    </ul>
  </div>
"""

initializeHelper = require "common/initialize_helper"
initializeHelper.registerInitialize "select", ->
  $target = $(@)

  if $target.is("select")
    $target.val $target.data("originValue")
    content =
      class: $target.attr("class")
      name: $target.attr("name")
      selectText: $target.find("option").filter(":selected").text()
      options: _.map $target.find("option"), (option) ->
        {text: $(option).text(), value: $(option).val()}
      value: $target.val()
    $target = $(defaultTemplate(content)).replaceAll($(@))

  $target.on
    focusout: ->
      $(@).removeClass "expand"
    click: (evt) ->
      # evt.preventDefault()
      $(@).toggleClass "expand"
    keydown: (evt) ->
      console.log evt.which
      if evt.which == 40
        evt.preventDefault()
        $(@).addClass "expand"
        return
      if evt.which == 38 or evt.which == 27
        evt.preventDefault()
        $(@).removeClass "expand"
        return

  $("a", $target).on
    click: (evt) ->
      evt.preventDefault()
      $target.find(".fake-select-input").val($(@).data("value")).change()
      $target.find(".fake-select-current").text $(@).text()
      $(@).parent().addClass("fake-select-checked").siblings().removeClass("fake-select-checked")
