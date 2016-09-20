requireByRegex = (regex) ->
  modules = window.require.modules
  $.each modules, (k) ->
    if regex.test k
      require k

hasModule = (path) ->
  window.require.modules[path] isnt undefined

module.exports =
  requireByRegex: requireByRegex
  hasModule: hasModule
