var Serie = function (data) {
    var self = this;
    self.id = ko.observable(Object.get(data, "id"));
    self.nombre = ko.observable(Object.get(data, "nombre"));
    self.siguienteFolio = ko.observable(Object.get(data, "siguienteFolio"));

    self.cargar = function (data) {
        self.id(Object.get(data, "id"));
        self.nombre(Object.get(data, "nombre"));
        self.siguienteFolio(Object.get(data, "siguienteFolio"));
    }

    self.limpiar = function () {
        self.id(undefined);
        self.nombre(undefined);
        self.siguienteFolio(undefined);
    }

    self.getJson = function () {
        var serie = ko.toJS(self);
        return serie;
    }
}