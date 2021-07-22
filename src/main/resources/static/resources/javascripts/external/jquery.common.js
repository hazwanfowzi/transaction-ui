jQuery.Format = function jQuery_dotnet_string_format(text) {
    //check if there are two arguments in the arguments list
    if (arguments.length <= 1) {
        //if there are not 2 or more arguments there's nothing to replace
        //just return the text
        return text;
    }
    //decrement to move to the second argument in the array
    var tokenCount = arguments.length - 2;
    for (var token = 0; token <= tokenCount; ++token) {
        //iterate through the tokens and replace their placeholders from the text in order
        text = text.replace(new RegExp("\\{" + token + "\\}", "gi"), arguments[token + 1]);
    }
    return text;
};

jQuery.fn.hasAttr = function(name) {  
   return this.attr(name) !== undefined;
};

var is_gritter_initialized = false;

jQuery.extend({
	htmlEncode : function(value) {
		if (value) {
			return jQuery('<div/>').text(value).html();
		} else {
			return '';
		}

	},
	sanitize_json : function(json) {
		if (json) {
			var sanitized_json = {};
			$.each(json,
					function(i, item) {
						if ($.type(item) == 'string') {
							sanitized_json[i] = $.htmlEncode(item);
						} else if ($.type(item) == 'array'
								|| $.type(item) == 'object') {
							sanitized_json[i] = $.sanitize_json(item);
						} else {
							sanitized_json[i] = item;
						}
					});

			return sanitized_json;
		} else {
			return null;
		}

	},
	randomString : function(length, special) {
		var iteration = 0;
		var password = "";
		var randomNumber;
		if (special == undefined) {
			var special = false;
		}
		while (iteration < length) {
			randomNumber = (Math.floor((Math.random() * 100)) % 94) + 33;
			if (!special) {
				if ((randomNumber >= 33) && (randomNumber <= 47)) {
					continue;
				}
				if ((randomNumber >= 58) && (randomNumber <= 64)) {
					continue;
				}
				if ((randomNumber >= 91) && (randomNumber <= 96)) {
					continue;
				}
				if ((randomNumber >= 123) && (randomNumber <= 126)) {
					continue;
				}
			}
			iteration++;
			password += String.fromCharCode(randomNumber);
		}
		return password;
	},
	truncateIfExceed: function (value, max_length){
	      if(value){
	      	  if(value.length>max_length){
	          	var substr_value = value.substring(0, max_length) + "...";
	          	return substr_value;    
	          }else{
	         	return value;
	          }
	      }else{
	          return '';
	      }
	    
	},
	alert: function(message, htmlEncode){
		
		if(is_gritter_initialized==false){
			$.extend($.gritter.options, { 
		        fade_in_speed: 'medium',
		        fade_out_speed: 'slow'
		    });
			is_gritter_initialized = true;
		}
		
		var final_message  = message;
		
		if(htmlEncode){
			final_message = $.htmlEncode(message);
		}
		
		var unique_id = $.gritter.add({
			text: final_message,
			image: APPLICATION_ROOT_CONTEXT+'assets/images/shared/success-icon-32.png',
			sticky: true,
			class_name: 'always-on-top'
		});
		
		setTimeout(function(){

			$.gritter.remove(unique_id, {
				fade: true,
				speed: 'slow'
			});

		}, 3000)
	},
	is_android: function(){
		var ua = navigator.userAgent.toLowerCase();
		var isAndroid = ua.indexOf("android") > -1; //&& ua.indexOf("mobile");
		if(isAndroid) {
		  return true;
		}
		return false;
	}
});



(function($) {

    var keyString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    var uTF8Encode = function(string) {
    	if(string){
	        string = string.replace(/\x0d\x0a/g, "\x0a");
	        var output = "";
	        for(var n = 0; n < string.length; n++) {
	            var c = string.charCodeAt(n);
	            if(c < 128) {
	                output += String.fromCharCode(c);
	            } else if((c > 127) && (c < 2048)) {
	                output += String.fromCharCode((c >> 6) | 192);
	                output += String.fromCharCode((c & 63) | 128);
	            } else {
	                output += String.fromCharCode((c >> 12) | 224);
	                output += String.fromCharCode(((c >> 6) & 63) | 128);
	                output += String.fromCharCode((c & 63) | 128);
	            }
	        }
	        return output;
    	}else{
    		return string;
    	}
    };
    var uTF8Decode = function(input) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while(i < input.length) {
            c = input.charCodeAt(i);
            if(c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if((c > 191) && (c < 224)) {
                c2 = input.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = input.charCodeAt(i + 1);
                c3 = input.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }

    $.extend({
        base64Encode : function(input) {
            var output = "";
            var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
            var i = 0;
            input = uTF8Encode(input);
            while(i < input.length) {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;
                if(isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if(isNaN(chr3)) {
                    enc4 = 64;
                }
                output = output + keyString.charAt(enc1) + keyString.charAt(enc2) + keyString.charAt(enc3) + keyString.charAt(enc4);
            }
            return output;
        },
        base64Decode : function(input) {
            var output = "";
            var chr1, chr2, chr3;
            var enc1, enc2, enc3, enc4;
            var i = 0;
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
            while(i < input.length) {
                enc1 = keyString.indexOf(input.charAt(i++));
                enc2 = keyString.indexOf(input.charAt(i++));
                enc3 = keyString.indexOf(input.charAt(i++));
                enc4 = keyString.indexOf(input.charAt(i++));
                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;
                output = output + String.fromCharCode(chr1);
                if(enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if(enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }
            }
            output = uTF8Decode(output);
            return output;
        }
    });
})(jQuery);

jQuery.fn.keyenter = function (fnc) {
    return this.each(function () {
        $(this).keypress(function (ev) {
            var keycode = (ev.keyCode ? ev.keyCode : ev.which);
            if (keycode == '13') {
                fnc.call(this, ev);
            }
        });
    });
} 

jQuery.fn.overlay = function (hide_show_indicator) {
	var overlay_div = $(this).find('#common_overlay');
	var is_overlay_div_created = overlay_div.length>0;
	
	if(!hide_show_indicator){
		hide_show_indicator = 'show';
	}
	
	if(hide_show_indicator=='show'){
		var the_div_height = $(this).height();
		if(is_overlay_div_created==false){
	    	$(this).append(SHOW_LOAD_OVERLAY_TEMPLATE);
	    	//$(this).css({'position':'relative'});
	    	overlay_div = $(this).find('#common_overlay');
	    	var loading_image = overlay_div.find('img');
	    	loading_image.css({'margin-top': 0.4*the_div_height});
	    	overlay_div.show();
	    }else{
	    	var loading_image = overlay_div.find('img');
	    	loading_image.css({'margin-top': 0.4*the_div_height});
	    	overlay_div.show();
	    }
	}else
	if(hide_show_indicator=='hide'){
		if(is_overlay_div_created){
			overlay_div.hide();
		}
	}
};