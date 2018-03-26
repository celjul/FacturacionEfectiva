(function($) {
	$.fn.findClientes = function(id) {
		var value = this.val();
		id.val("");
		_this = this;

		if (value || value != 0 || value != "") {
			var url = contextPath + "/app/clientes/findClientes?nombre=" + value + "&nocache=" + new Date().getTime();
			
			this.autocomplete({
				source : function (request, response) {
					$.getJSON(url, function(data){
						response($.map(data, function(item) {
							return {
								label : item.nombreCompleto,
								value : item.id
							}
						}));
					});
				},
				minLength : 3,
				select : function (event, ui) {
					id.val(ui.item.value);
					_this.val(ui.item.label)
					return false;
				},
				focus : function (event, ui) {
					id.val(ui.item.value);
					return false;
				},
				change : function (event, ui) {
					if (!ui.item) {
						id.val("");
						_this.val("");
					} else {
						id.val(ui.item.value);
						_this.val(ui.item.label)
					}
					return false;
				}
			});
		}
		return this;
	};
})(jQuery);