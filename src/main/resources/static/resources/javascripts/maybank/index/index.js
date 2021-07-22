/* jshint smarttabs: true */

/**
 * The Index main class. 
 *
 * @class
 * @name Index
 **/
define([
	'maybank/utils/ajax-request',
	'maybank/utils/link-const',
//	'bootstrapSelect'
], function(
		Request,
		LinkConst,
//		bootstrapSelect
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
			$("form#form_searchtrx").submit(function(e) {
			    e.preventDefault();

				const customerID = $('#in_cust_id').val();
				const accountNumber = $('#in_account_no').val();
				const trxDescription = $('#in_description').val();
	
				let formData = {
					'customerID': customerID,
					'accountNumber': accountNumber,
					'trxDescription': trxDescription,
					'pageNo': 1,
					'pageSize': 2
				};
				
				Request.get(
					LinkConst.Transaction.GET_TRX_DETAILS,
					formData,
					_.bind(function(result) {
						if (result.status === Request.Result.OK) {
							var data = result.data;
							var listOfDate = data.subscriptionDate;
							
//							$('#in_date_out').val(data.subscriptionDate[0] + "  " + data.invoiceDay);
//							$('#in_invoice_amount_out').val('$ ' + data.invoiceAmount);
//							$('#in_subs_type_out').val(data.subscriptionType);
//							
//							$('#txtarea_follow_date').val(data.subscriptionDate.join("\n"));
						}else{
							alert(messages('error.data.problem'));
						}
					}, this)
				);
			});
			
//			$('#dd_subs_type').selectpicker('render');
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
			            console.log("SUCCESS : ", data);
						if (data && data.status) {
							Notification.info(data.message + ' extraction initializing...', undefined);
						}
						
			        },
			        error: function (e) {
			            console.log("ERROR : ", e);
						Notification.error('Ops, something went wrong.', undefined);
						// Notification.notyMessage(e.message + ' Ops, something went wrong.', 'error', undefined);
			
			        }
			    });
			});
		}
	});
	
	return Index;
});