google.load('visualization', '1', { 'packages': ['corechart'], callback: graphLibLoaded })

function graphLibLoaded() {
  var charDiv = $("#chart-div")[0]
  var selected = undefined
  var interval = {}

  function refreshStats(stat, chartRenderer) {
    clearInterval(interval)
    var url = $("#metrics-grid").data("refresh-uri") + "?m=" + stat

    function render(data) {
      var json = $.parseJSON(data)
      if (json[0] != undefined) chartRenderer.appendMetric(json[0])
    }

    interval = setInterval(function() {
      $.ajax({
        url: url,
        dataType: "text",
        success: render
      })
    }, 1000)
  }

  function render(li) {
    var stat = li.html()
    if (selected != undefined) selected.removeClass("selected")
    li.addClass("selected")
    selected = li
    refreshStats(stat, new ChartRenderer(charDiv, stat))
  }

  $('#metrics li').on('click', function(e) { render($(e.target)) })

  var fragmentId = $("#"+window.location.hash.replace('#', '').replace(/\//g, "-"))
  if (fragmentId[0] != undefined) {
    $(fragmentId)[0].scrollIntoView(true)
    render(fragmentId)
  } else {
    render($('#metrics li:first'))
  }
}
