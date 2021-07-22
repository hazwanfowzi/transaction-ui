/* jshint smarttabs: true */

/**
 * The URL link constant class. 
 *
 * @class
 * @name LinkConst
 **/
define([
], function() {
	'use strict';

	var APP_ROOT_CONTEXT = "";
	
	if (APP_ROOT_CONTEXT == '/') {
		APP_ROOT_CONTEXT = "";
	}
	
	var LinkConst = _.extend({}, /** @lends LinkConst.prototype */ {
		
		
		/**
		 * A list of subscription link.
		 *
		 * @const {Object}
		 * @property {String} POST_EXTRACT /extraction/file
		 */
		Transaction : {
			POST_EXTRACT				: APP_ROOT_CONTEXT + '/transaction-ui/api/extraction/file', //post
			GET_TRX_DETAILS				: APP_ROOT_CONTEXT + '/transaction-ui/api/transaction/getByCustomerIDPagination' //get
		}
	});

	return LinkConst;
});
