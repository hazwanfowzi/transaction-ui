/* jshint smarttabs: true */

/**
 * The Index main class. 
 *
 * @class
 * @name Index
 **/
define([
	'maybank/utils/ajax-request',
	'maybank/utils/link-const'
], function(
		Request,
		LinkConst
		) {
	'use strict';
	
	var Index =  _.extend({}, /** @lends Index.prototype */ {
		/**
		 * Bind login action.
		 * 
		 * @returns {void}
		 * @private
		 */
		_bindAction: function() {
			var self=this;
			
			$("form#form_searchtrx").submit(function(e) {
			    e.preventDefault();

				const customerID = $('#in_cust_id').val();
				const accountNumber = $('#in_account_no').val();
				const trxDescription = $('#in_description').val();
				const pageNo = $('#in_page_no').val();
				const pageSize = $('#in_page_size').val();
	
				let formData = {
					'customerID': customerID,
					'accountNumber': accountNumber,
					'trxDescription': trxDescription,
					'pageNo': pageNo,
					'pageSize': pageSize
				};
				
				Request.get(
					LinkConst.Transaction.GET_TRX_DETAILS,
					formData,
					_.bind(function(result) {
						if (result.status === Request.Result.OK) {
							var data = result.data;
							self._generateResultTable(data);
						}else{
							alert(messages('error.data.problem'));
						}
					}, this)
				);
			});
		},
		
		/**
		 * Init func.
		 * 
		 * @returns {void}
		 * @public
		 */
		init: function(){
			console.log('js in');
			
			this._bindActionFileUploadModal();
			this._bindAction();
		},
		
		/**
		 * Bind file upload modal action
		 * 
		 * @returns {void}
		 * @private
		 */
		_bindActionFileUploadModal: function(){
			$("form#form_uploadFile").submit(function(e) {
			    e.preventDefault();

				var form = $('form#form_uploadFile')[0];
				var fileInput = $('#fu_fileInput');
				var files = fileInput[0].files;
				
			    var formData = new FormData(form);
				formData.append("fileUpload", files[0]);
			
			    $.ajax({
					xhr: function(){
						var xhr = new window.XMLHttpRequest();
					    //Upload progress
					    xhr.upload.addEventListener("progress", function(evt){
					    	if (evt.lengthComputable) {
					        	var percentComplete = evt.loaded / evt.total;
					        	//Do something with upload progress
					        	console.log((percentComplete*100) + "%");
					      	}
					    }, false);
					    return xhr;
					},
			        type: "POST",
			        enctype: 'multipart/form-data',
			        url: LinkConst.Transaction.POST_EXTRACT,
			        data: formData,
			        processData: false, 
			        contentType: false,
			        cache: false,
			        timeout: 600000,
			        success: function (data) {
						alert("Extraction completed!");
			            console.log("SUCCESS : ", data);
			        },
			        error: function (e) {
			            console.log("ERROR : ", e);
			        }
			    });
			});
		},
		
		/**
		 * Generate content for result table.
		 * 
		 * @returns {void}
		 * @private
		 */
		_generateResultTable: function(data){
			var dataContent = data.content;
			
			
			var tableObj = $('#table_result');
			tableObj.empty();
			tableObj.append('<th>ID</th><th>Account Number</th><th>Amount (RM)</th><th>Transaction Description</th>' + 
							'<th>Transaction Date</th><th>Transaction Time</th><th>Customer ID</th>');
			
			_.each(dataContent, function(item){
				var itemTR = $('<tr>');
				
				_.each( item, function( key, value ){
					
					var itemTD = $('<td>').text(key);
					itemTR.append(itemTD);
//				  	alert( key + ": " + value );
				});
				
				tableObj.append(itemTR);
			});
		}
	});
	
	return Index;
});