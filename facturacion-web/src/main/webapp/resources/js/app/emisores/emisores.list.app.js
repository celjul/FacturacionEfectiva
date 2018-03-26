
var EmisorListaViewModel = function(data) {
	var self = this;

	self.emisores = ko.observableArray([]);
	$.get(String.format("{0}/app/emisores/", contextPath), function(data) {
		self.emisores(data);
	});
}


$(function() {
	var modelView = new EmisorListaViewModel();
	ko.applyBindings(modelView);
});