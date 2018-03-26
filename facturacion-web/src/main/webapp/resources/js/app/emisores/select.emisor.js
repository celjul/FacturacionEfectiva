var EmisorSelectViewModel = function(data) {
	var self = this;
	self.emisores = ko.observableArray([]);

	$.get(String.format("{0}/app/emisores/", contextPath), function(data) {
		self.emisores(data);
    });
	
	self.actualizarLista = function(){
		$.get(String.format("{0}/app/emisores/", contextPath), function(data) {
			self.emisores(data);
	    });
	}
}

