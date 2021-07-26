package maybank.project.controller.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import maybank.project.dto.ResponseMessage;
import maybank.project.dto.TransactionDTO;
import maybank.project.entity.TransactionEntity;
import maybank.project.exception.error.GenericErrorCode;
import maybank.project.repository.TransactionRepository;
import maybank.project.service.TransactionService;
import maybank.project.utils.Messages;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired private Messages messages;
	@Autowired private TransactionRepository transactionRepository;
	@Autowired private TransactionService transactionService;
	
	@GetMapping("/getByCustomerIDPagination")
	public ResponseMessage getByCustomerIDPagination(@RequestParam("customerID") Optional<String> customerID, @RequestParam("accountNumber") Optional<String> accountNumber, 
											@RequestParam("trxDescription") Optional<String> trxDescription, Integer pageNo, Integer pageSize) {
		ResponseMessage message = new ResponseMessage();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
			    
				Page<TransactionDTO> result = transactionService.findAll(customerID, accountNumber, trxDescription, pageNo, pageSize);
				
				message.setStatus(ResponseMessage.OK);
				message.setData(result);
			}else {
				message.setStatus(ResponseMessage.WARN);
				
			}
			
			return  message;
		}catch (Exception e) {
			e.printStackTrace();
			message.setCode(GenericErrorCode.GENERIC_ERROR);
			message.setStatus(ResponseMessage.ERROR);
			message.setDescription(messages.get("error.internal"));
			return message;
		}
	}
	
	//no pagination
	@GetMapping("/getByCustomerID")
    public ResponseMessage getByCustomerID (@RequestParam("customerID") String customerID, @RequestParam("accountNumber") String accountNumber, @RequestParam("trxDescription") String trxDescription) {
		
		ResponseMessage message = new ResponseMessage();
		try {
			List<TransactionEntity> result = transactionRepository.getTrxByCustomerID(customerID, accountNumber, trxDescription);
			if (result.size()>0) {
				
			}else {
				
			}
			
			LOGGER.info("size : " + result.size());
			
			message.setStatus(ResponseMessage.OK);
			message.setData(result);
			return  message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setCode(GenericErrorCode.GENERIC_ERROR);
			message.setStatus(ResponseMessage.ERROR);
			message.setDescription(messages.get("error.internal"));
			return message;
		}
    }
}
