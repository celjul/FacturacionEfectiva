ko.bindingHandlers.unique = {
    init: function(element, valueAccessor) {
        var value = valueAccessor();
        var a = (value.prefix ? value.prefix : ko.bindingHandlers.unique.prefix) + "-" + 
        ((value.counter != undefined) ? value.counter : ++ko.bindingHandlers.unique.counter);
        
        if (value.name) {
        	element.name = a;
        }
        
        if (value.id){
        	element.id = a;
        }
    },
    counter: 0,
    prefix: "unique"
};