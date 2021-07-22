/*jshint smarttabs: true */

define([
	'jqueryCore',
	'jqueryCommon'

],
	function() {
		'use strict';

		// introduce new contains selector without case sensitive
		jQuery.expr[':'].icontains = function(a, i, m) {
		  return jQuery(a).text().toUpperCase()
		      .indexOf(m[3].toUpperCase()) >= 0;
		};
		
		return window.jQuery;
	}
);