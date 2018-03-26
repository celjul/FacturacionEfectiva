var SeriesViewModel = function (data) {
    var self = this;
    self.series = ko.observableArray([]);
    $.get(String.format("{0}/app/series/", contextPath), function (data) {
        self.series(data);
    });
}

$(function () {
    var modelView = new SeriesViewModel();
    ko.applyBindings(modelView);
});