$(function() {
    toastr.options = {
      "closeButton": true,
      "debug": false,
      "newestOnTop": false,
      "progressBar": false,
      "positionClass": "toast-top-right",
      "preventDuplicates": false,
      "onclick": null,
      "showDuration": "300",
      "hideDuration": "1000",
      "timeOut": "5000",
      "extendedTimeOut": "1000",
      "showEasing": "swing",
      "hideEasing": "linear",
      "showMethod": "fadeIn",
      "hideMethod": "fadeOut"
    };
    
    $('#side-menu').metisMenu();
    $('form').validate();

    $.ajaxSetup({ cache: false });
    $(document).ajaxError(function(event, jqXHR, settings, err) {
    	if (jqXHR && jqXHR.responseJSON) {
    		if (jqXHR.responseJSON.errores) {
    			var mensaje = "<p>" + jqXHR.responseJSON.message + "</p>";
    			
				mensaje += "<ul>";
				$.each(jqXHR.responseJSON.errores, function(i, item) {
					mensaje += "<li>" + item + "</li>";
				});
				mensaje += "</ul>";
    			toastr.error(mensaje, "!Error¡");
    		} else {
    			toastr.error(jqXHR.responseJSON.message, "!Error¡");
    		}
    	} else {
    		toastr.error(err, "!Error¡");
    	}
        
    });

    $(document).ajaxSend(function() {
      $.blockUI({ 
        message:"Cargando...",
          css: { 
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff'
          } 
        });
    });

    $(document).ajaxComplete(function(){
      $.unblockUI();
    });

    /*
        function(event, jqxhr, settings) {
           $.blockUI({ css: { 
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' 
          } 
        }); 
    });

    $(document).ajaxComplete(function(){
      $.unblockUI();
    });
*/
  


    $("label.required").prepend("<span>* <span>");

    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    var element = $('ul.nav a').filter(function() {
        return this.href == url || url.href.indexOf(this.href) == 0;
    }).addClass('active').parent().parent().addClass('in').parent();
    if (element.is('li')) {
        element.addClass('active');
    }
    
    var excludes = [String.format("{0}/app/emisores/new", contextPath),
                    String.format("{0}/app/wizard/inicio", contextPath),
                    String.format("{0}", contextPath),
                    String.format("{0}/", contextPath),
                    String.format("{0}/login", contextPath),
                    String.format("{0}/logout", contextPath),
                    String.format("{0}/inicio", contextPath)];
    var pathname = $(location).attr("pathname");

    var index = excludes.indexOf(pathname);

	if (index == -1) {
		if (window.session && !window.session.emisor) {
	//		alert("La sesion no tiene un emisor asociado");
			bootbox.alert("La sesion no tiene un emisor asociado", function() {
				location.href = String.format("{0}/app/emisores/select", contextPath);
			})
		}
//		else {
//			
//
//		}
	}
	
});


var EZFact = function() {
  return {
    block : function(options) {

    }
  }
}