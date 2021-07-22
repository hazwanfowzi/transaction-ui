/* jshint smarttabs: true */

/**
 * The login main class. 
 *
 * @class
 * @name Login
 **/
define([
	'maybank/shared/constants/link-const',
	'notification',
	'maybank/shared/utils/ajax-request',	
	'serverSettings'
], function(
		LinkConst,
		Notification,
		Request,
		serverSettings
		) {
	'use strict';
	
	var Login =  _.extend({}, /** @lends Login.prototype */ {
		/**
		 * Bind login action.
		 * 
		 * @returns {void}
		 * @private
		 */
		_bindAction: function() {
			$('input[type="text"], input[type="password"]').on('focus',function() {
				$(this).prev().removeClass('blur').addClass('focus');
			});
			
			$('input[type="text"], input[type="password"]').on('blur',function() {
				$(this).prev().removeClass('focus').addClass('blur');
			});
			
			$('form[name=login_form] #login_button').on('click', function(e) {
				e.preventDefault();
				$('form[name=login_form]').submit();
			});
			
			$('form[name=login_form] input[name=password]').on('keypress', function(e) {
				 if(e.which === 13) {
					 $('form[name=login_form] #login_button').trigger('click');
				 }
			});
		},

		/**
		 * Repositioning the login screen & set auto focus.
		 * 
		 * @returns {void}
		 * @private
		 */
		_initLoginScreen : function() {
			var centerHeight = (window.innerHeight - $('.loginscreen').height()) / 2 - $('.logo').outerHeight();
			$('.loginscreen').css('padding-top', centerHeight);
			$('input[name="username"]').focus();
		},		
		
		/**
		 * Initilize login page.
		 * 
		 * @returns {void}
		 */
		initPage : function() {
			if(window.self !== window.top) {
				top.location.href = LinkConst.Mibis.HOME;
			}
			
			this._bindAction();
			this._initLoginScreen();
			this._loginFormValidator();
		},
		
		/**
		 * Login validation.
		 * 
		 * @returns {void}
		 * @private
		 */
		_loginFormValidator : function() {
			var self = this;
			
			var validator = $('form[name=login_form]').validate({
				rules : {
					username : {
        		required : true,
        		maxlength : 50,
        		minlength : 3
        	},
        	password : {
        		required : true,
        		maxlength : 50,
        		minlength : 5
        	}
				},
				errorClass: 'help-block error',
        errorElement: 'div',
        errorPlacement: function(error, element) {
          error.appendTo(element.parents(".form-group"));
        },
        highlight:function(element, errorClass, validClass) {
          $(element).parents('.form-group').addClass('error').removeClass(validClass);
	      },
	      unhighlight: function(element, errorClass, validClass) {
	          $(element).parents('.error').removeClass('error').addClass(validClass);
	      },
	      submitHandler : function(form) {
	      	self._submitLoginForm();
	      },
	      messages: {
	    	  username: {
	    		  required : messages('err.required'),
	    		  maxlength : messages('err.maxlength'),
	    	      minlength : messages('err.minlength')
	    	  },
	    	  password:  {
	    		  required : messages('err.required'),
	    		  maxlength : messages('err.maxlength'),
	    	      minlength : messages('err.minlength')
	    	  }
	        
	        }
			});
		},
		
		/**
		 * Proceed login request.
		 * 
		 * @returns {void}
		 * @private
		 */
		_submitLoginForm : function() {
			Request.post(
				LinkConst.Outh.LOGIN,
				{
					username : $('input[name="username"]').val(),
					password : $('input[name="password"]').val(),
					remember : $('input[name="remember-me"]').prop('checked')
				},
				function(result) {				
				
					if (result.loggedIn) {
						$('#login_button').addClass('disabled').text(messages('label.login.screen.login.redirect'));
						window.location = serverSettings.APPLICATION_ROOT_CONTEXT;
					}
					else {
						Notification.error(messages(result.message),'header');
					}
				}, { 
					crossDomain : true,
					contentType : Request.ContentType.UTF8_URL_ENCODED
				}
			);
		}
	});
	
	return Login;
});