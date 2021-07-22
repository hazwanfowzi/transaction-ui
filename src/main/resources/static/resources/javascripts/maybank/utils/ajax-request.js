/* jshint smarttabs: true */

/**
 * An XMLHttpRequest utility class. 
 *
 * @class
 * @name Request
 **/
define([
], function(
		//BlockUI,
		//log,
		//Notification,
		//ServerSettings
		) {
	'use strict';
	
	var Request = _.extend({}, /** @lends Request.prototype */ {
		/**
		 * A list of AJAX content type.
		 * 
		 * @const {Object}
		 * @property {String} JSON application/json 
		 * @property {String} UTF8_URL_ENCODED application/x-www-form-urlencoded; charset=UTF-8
		 */
		ContentType : {
			JSON : 'application/json',
			UTF8_URL_ENCODED : 'application/x-www-form-urlencoded; charset=UTF-8'
		},	
		
		/**
		 * A list of accepted data type.
		 * 
		 * @const {Object}
		 * @property {String} HTML html 
		 * @property {String} JSON json
		 * @property {String} JSONP jsonp 
		 * @property {String} SCRIPT script
		 * @property {String} TEXT text 
		 * @property {String} XML xml
		 */
		DataType : {
			HTML : 'html',
			JSON : 'json',
			JSONP : 'jsonp',
			SCRIPT: 'script',
			TEXT : 'text',
			XML : 'xml'
		},
		
		/**
		 * Request method options.
		 * 
		 * @const {Object}
		 * @property {String} GET GET
		 * @property {String} POST POST 
		 * @property {String} DELETE DELETE
		 */
		Method: {
			GET: 'GET',
			POST: 'POST',
			DELETE: 'DELETE'
		},
		
		/**
		 * Request result options.
		 * 
		 * @const {Object}
		 * @property {String} OK OK
		 * @property {String} ERROR error 
		 */
		Result : {
			TRUE: true,
			OK : 'OK',
			ERROR : 'ERROR'
		},
			
		/**
		 * Perform an asynchronous HTTP (Ajax) request.
		 * 
		 * @param {String} url URL string to call backend method
		 * @param {Object} param Data to be sent to the backend
		 * @param {Function} successFunc Function to be called when request is successful
		 * @param {Boolean} [options.cache] Whether to force requested pages not be cached by the browser
		 * @param {String} [options.contentType] Content type to be used when sending data to backend
		 * @param {String} [options.dataType] The type of data that you are expecting back from the backend
		 * @param {Boolean} [options.isAsync] Whether AJAX call can run asynchronously 
		 * @param {Boolean} [options.showSpinner] Whether to display loading animation
		 * @param {String} [options.showSpinner] Whether to display loading animation
		 * @param {String} [options.block.selector] The DOM element to be blocked 
		 * @param {Function} [options.error.message] Customize error message
		 * @param {Function} [options.error.callback] The Method to be called when request failed 
		 * @returns {void}
		 * @private
		 */
		_ajaxCall : function(url, param, successFunc, opt) {
			// default value
			var options = {
				cache : false,
				contentType : this.ContentType.JSON,
				crossDomain : false,
				dataType : this.DataType.JSON,
				isAsync : true,
				showSpinner : true,
				block : {
					selector : 'body'
				},
				error: {}
			};

			// overwrite the default value
			_.extend(options, opt);

			$.ajax({
				url : url,
				type : options.type,
				cache : options.cache,
				contentType : options.contentType,
				crossDomain : options.crossDomain,
				data : param,
				dataType : options.dataType,
				async : options.isAsync,
				success : successFunc
			});
		},

		/**
		 * Make a request to back-end by using GET method.
		 * 
		 * @param {String} url URL string to call backend method
		 * @param {Object} param Data to be sent to the backend
		 * @param {Function} successFunc Function to be called when request is successful
		 * @param {Boolean} [opt.cache] Whether to force requested pages not be cached by the browser
		 * @param {String} [opt.contentType] Content type to be used when sending data to backend
		 * @param {String} [opt.dataType] The type of data that you are expecting back from the backend
		 * @param {Boolean} [opt.isAsync] Whether AJAX call can run asynchronously 
		 * @param {Boolean} [opt.showSpinner] Whether to display loading animation
		 * @param {String} [opt.block.selector] The DOM element to be blocked 
		 * @param {Function} [opt.error.message] Customize error message
		 * @param {Function} [opt.error.callback] The Method to be called when request failed 
		 * @returns {void}
		 */
		get : function(url, param, successFunc, opt) {
			opt = opt || {};
			opt.type = this.Method.GET;
			this._ajaxCall(url, param, successFunc, opt);
		},

		/**
		 * Make a request to backend by using POST method.
		 * 
		 * @param {String} url URL string to call backend method
		 * @param {Object} param Data to be sent to the backend
		 * @param {Function} successFunc Function to be called when request is successful
		 * @param {Boolean} [opt.cache] Whether to force requested pages not be cached by the browser
		 * @param {String} [opt.contentType] Content type to be used when sending data to backend
		 * @param {String} [opt.dataType] The type of data that you are expecting back from the backend
		 * @param {Boolean} [opt.isAsync] Whether AJAX call can run asynchronously 
		 * @param {Boolean} [opt.showSpinner] Whether to display loading animation
		 * @param {String} [opt.block.selector] The DOM element to be blocked 
		 * @param {Function} [opt.error.message] Customize error message
		 * @param {Function} [opt.error.callback] The Method to be called when request failed 
		 * @returns {void}
		 */
		post : function(url, param, successFunc, opt) {
			opt = opt || {};
			opt.type = this.Method.POST;
			this._ajaxCall(url, param, successFunc, opt);
		},
		
		/**
		 * Make a request to backend by using DELETE method.
		 * 
		 * @param {String} url URL string to call backend method
		 * @param {Object} param Data to be sent to the backend
		 * @param {Function} successFunc Function to be called when request is successful
		 * @param {Boolean} [opt.cache] Whether to force requested pages not be cached by the browser
		 * @param {String} [opt.contentType] Content type to be used when sending data to backend
		 * @param {String} [opt.dataType] The type of data that you are expecting back from the backend
		 * @param {Boolean} [opt.isAsync] Whether AJAX call can run asynchronously 
		 * @param {Boolean} [opt.showSpinner] Whether to display loading animation
		 * @param {String} [opt.block.selector] The DOM element to be blocked 
		 * @param {Function} [opt.error.message] Customize error message
		 * @param {Function} [opt.error.callback] The Method to be called when request failed 
		 * @returns {void}
		 */
		remove : function(url, param, successFunc, opt) {
			opt = opt || {};
			opt.type = this.Method.DELETE;
			this._ajaxCall(url, param, successFunc, opt);
		},		

		/**
		 * Validate JSON object by checking its object structure.
		 * 
		 * @param {String} JSONString JSON string to be checked
		 * @returns {Boolean} True if it is a valid JSON format, otherwise false
		 */
		validateJSON : function(JSONString) {
			if(JSONString.data != null) {
				
				if( !$.isArray(JSONString.data) ||  !JSONString.data.length ) {
					return false;
				}
				
			} else {
				return false;
			}
			
			return true;
		}
	});
	
	return Request;
});

