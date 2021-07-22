package maybank.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import maybank.project.entity.TransactionEntity;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long>{
	
	@Query("SELECT e FROM TransactionEntity e WHERE e.customerID LIKE :customerID OR e.accountNumber LIKE :accountNumber OR e.trxDescription LIKE :trxDescription")
	List<TransactionEntity> getTrxByCustomerID(@Param("customerID") String customerID, @Param("accountNumber") String accountNumber, @Param("trxDescription") String trxDescription);
	
	@Transactional(readOnly = true)
	@Query("SELECT e FROM TransactionEntity e WHERE (:customerID IS NULL OR e.customerID LIKE :customerID) OR (:accountNumber IS NULL OR e.accountNumber LIKE :accountNumber) OR (:trxDescription IS NULL OR e.trxDescription LIKE :trxDescription)")
	Page<TransactionEntity> getTrxByCustomerIDPagination(@Param("customerID") Optional<String> customerID, 
			@Param("accountNumber") Optional<String> accountNumber, 
			@Param("trxDescription") Optional<String> trxDescription,
			Pageable pageable);
}
