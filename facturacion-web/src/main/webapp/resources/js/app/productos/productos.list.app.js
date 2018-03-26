var ProductosViewModel = function(data) {
	var self = this;

	self.productos = ko.observableArray([]);
	$.get(String.format("{0}/app/productos/", contextPath), function(data) {
		self.productos(data);
	});
}


$(function() {
	var modelView = new ProductosViewModel();
	ko.applyBindings(modelView);
});