Handlebars.registerHelper 'component', (className, options) ->
  "<div class=\"#{className} js-comp\">\n" + options.fn(this) + "\n</div>"

Handlebars.registerHelper 'equals', (a, b, options) ->
  if a?.toString() == b?.toString()
    options.fn(this)
  else
    options.inverse(this)

Handlebars.registerHelper 'and', (a, b, options) ->
  if a? and b?
    options.fn(this)
  else
    options.inverse(this)

Handlebars.registerHelper 'neither', (a, b, options) ->
  if !a and !b
    options.fn(this)
  else
    options.inverse(this)

Handlebars.registerHelper 'mod', (a, b, options) ->
  if (a + 1) % b != 0
    options.inverse(this)
  else
    options.fn(this)

Handlebars.registerHelper 'pp', (options) ->
  JSON.stringify(this)

Handlebars.registerHelper 'for', (n, items, options) ->
  out = ""
  _(n).times (index) ->
    out += options.fn(items[n])
  out

Handlebars.registerHelper 'add', (a, b, options) ->
  a + b

Handlebars.registerHelper "join", (array, separator, options) ->
  return if not array
  _.map array, (item) ->
    options.fn(item)
  .join " #{separator} "

Handlebars.registerHelper "foreach", (arr, options) ->
  return unless arr
  return options.inverse(this)  if options.inverse and not arr.length
  _.map arr (item, index) ->
    options.fn
      $this: item
      $index: index
      $first: index is 0
      $last: index is arr.length - 1
  .join ""

Handlebars.registerHelper "formatPrice", (price, options) ->
  return if not price? or price < 0
  formatedPrice = (price / 100).toFixed(2)
  roundedPrice = parseInt(price / 100)
  if `formatedPrice == roundedPrice` then roundedPrice else formatedPrice

Handlebars.registerHelper "formatDate", (date, options) ->
  return unless date
  moment(parseInt date).format("YYYY-MM-DD")

