package maybank.project.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import maybank.project.dto.TransactionDTO;
import maybank.project.entity.TransactionEntity;
import maybank.project.exception.InternalServerException;
import maybank.project.repository.TransactionRepository;

@Service
public class TransactionService {
	Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
	@Autowired TransactionRepository transactionRepository;
	
	
	@Transactional(readOnly = true)
	public Page<TransactionDTO> findAll(Optional<String> customerID, Optional<String> accountNumber, Optional<String> trxDescription, Integer pageNo, Integer pageSize) {
//		Sort sort = Sort.by("trx_date").descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		try {
			return transactionRepository.getTrxByCustomerIDPagination(customerID, accountNumber, trxDescription, pageable).map(this::toDto);
		} catch (Exception e) {
			LOGGER.error("Unable to get transactions", e);
			throw new InternalServerException();		
		}
    }
	
	public TransactionDTO toDto(TransactionEntity entity) {
    	TransactionDTO dto = new TransactionDTO();
    	
    	dto.setId(entity.getId());
    	dto.setAccountNumber(entity.getAccountNumber());
    	dto.setTrxAmount(entity.getTrxAmount());
    	dto.setTrxDescription(entity.getTrxDescription());
    	dto.setTrxDate(entity.getTrxDate());
    	dto.setTrxTime(entity.getTrxTime());

		return dto;
	}
}
