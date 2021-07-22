package maybank.project.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import maybank.project.entity.TransactionEntity;
import maybank.project.repository.TransactionRepository;
import maybank.project.service.ExtractionService;

@Service
public class ExtractionServiceImpl implements ExtractionService{
	Logger LOGGER = LoggerFactory.getLogger(ExtractionServiceImpl.class);
	
//	@Autowired TransactionEntity transactionEntity;
	@Autowired TransactionRepository transactionRepository;
	
	@Override
	public void extractTextFileMain(MultipartFile inputStreamFile) {
		List<String> list = new ArrayList<String>();
        InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(inputStreamFile.getInputStream(), StandardCharsets.UTF_8);
			
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			bufferedReader.readLine(); //skip first row
	        String str;
	        while((str = bufferedReader.readLine()) != null){
	        	list.add(str);
	        }
			
	        extractTextFile(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void extractTextFile(List<String> rows) {
//		List<TransactionEntity> entityList = new ArrayList<TransactionEntity>();
	    
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
	        
		rows.stream().forEach(row -> {
			
			LOGGER.debug("row line: " + row);
			LOGGER.info("row line: " + row);
			
			String rowStr = row.toString();
			
			String[] arrayOfString = rowStr.split("[|]");
			
			String accountNumberStr = arrayOfString[0].trim().toString();
			LOGGER.info("accountNumberStr: " + accountNumberStr.toString());
			
			accountNumberStr = accountNumberStr.replaceAll("\\D", "");
//			int blockSize = Integer.parseInt(accountNumberStr);
//			Integer accountNumber = Integer.parseInt(accountNumberStr.toString().trim());
			
			String trxAmountStr = arrayOfString[1];
			BigDecimal parsedStringValue = null;
			try {
				parsedStringValue = (BigDecimal) decimalFormat.parse(trxAmountStr.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.info("trxAmountStr: " + trxAmountStr);
			LOGGER.info("trxAmountBig: " + parsedStringValue);
			
			String trxDescStr = arrayOfString[2];
			LOGGER.info("trxDescStr: " + trxDescStr);
			
			String trxDateStr = arrayOfString[3];
			LOGGER.info("trxDateStr: " + trxDateStr);
			
			String trxTimeStr = arrayOfString[4];
			LOGGER.info("trxTimeStr: " + trxTimeStr);
			
			String custIDStr = arrayOfString[5];
			LOGGER.info("custIDStr: " + custIDStr);
			
			
			
			TransactionEntity entity = new TransactionEntity();
			entity.setAccountNumber(accountNumberStr);
			entity.setTrxAmount(trxAmountStr);
			entity.setTrxDescription(trxDescStr);
			entity.setTrxDate(trxDateStr);
			entity.setTrxTime(trxTimeStr);
			entity.setCustomerID(custIDStr);
			
//			entity.setAccountNumber(accountNumber);
//			entity.setTrxAmount(parsedStringValue);
//			entity.setTrxDescription(trxDescStr.toString());
//			entity.setTrxDate(Date.valueOf(trxDateStr.toString()));
//			entity.setTrxTime(Time.valueOf(trxTimeStr.toString()));
//			entity.setCustomerID(Integer.parseInt(custIDStr.toString()));
			
			
			
			System.out.println(entity.toString());
			
			transactionRepository.save(entity);
        });
	        
	}
}
