/**
 * Register external API to be loaded by requireJS & convert it into AMD structure.
 */
require.config({
	// waiting period (seconds) before it throw file not found error
	waitSeconds : 200,
	paths : {
		bootstrap : '../plugins/bootstrap/js/bootstrap.min',
		bootstrapSelect: '../plugins/bootstrap/js/bootstrap-select.min',
		moment: 'external/moment',
		serverSettings : 'server-settings',
		underscore : 'external/underscore-min',
		
		// wrappers
		jquery : 'maybank/wrappers/jquery',
		jqueryCore : 'external/jquery-1.10.2.min',
		jqueryUI : '../plugins/jquery-ui/js/jquery-ui-1.10.1.custom.min',
		jqueryCommon : 'external/jquery.common',
	},
	// shims for API that are not AMD-wrapped
	shim : {
		bootstrap : {
			deps : ['jqueryCore', 'jqueryUI']
		},
		bootstrapSelect : {
			deps : ['jqueryCore', 'bootstrap']
		},
		
		// wrappers
		jqueryCore : {
			exports : '$'
		},
		jqueryCommon : {
			deps : ['jqueryCore']
		},
		jqueryUI : {
			deps : ['jqueryCore']
		}
	}
});

define([
	'bootstrap',
	'jquery', 
	'moment', 
	'underscore'
], function(
		bootstrap,
		jquery, 
		moment,
		underscore
		) {
	'use strict';

	// Expose useful libraries to client.
	_.extend(window, {
		_ : underscore,
		moment: moment
	});
	
});
