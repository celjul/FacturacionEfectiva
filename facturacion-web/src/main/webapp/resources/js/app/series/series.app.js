var SeriesViewModel = function (data) {
    var self = this;
    self.serie = new Serie(data);

    self.guardar = function () {
        if ($("#form-serie").valid()) {
            var metodo = "POST";
            var serie = self.serie.getJson();
            if (serie.id) {
                metodo = "PUT";
            }
            console.log(JSON.stringify(serie));
            $.ajax({
                url: String.format("{0}/app/series/", contextPath),
                type: metodo,
                dataType: 'json',
                data: JSON.stringify(serie),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    toastr.success("La serie se ha registrado correctamente.", "¡Éxito!");
                    if (serie.id) {
                        location.href=String.format("{0}/app/series/list", contextPath)
                    }
                }
            });
        }
    }
}