var ClienteListaViewModel = function(data) {
	var self = this;

	self.clientes = ko.observableArray([]);
	$.get(String.format("{0}/app/clientes/", contextPath), function(data) {
		self.clientes(data);
	});
}
$(function() {
	var modelView = new ClienteListaViewModel();
	ko.applyBindings(modelView);
});