package maybank.project.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
			e.printStackTrace();
		}
	}

	private void extractTextFile(List<String> rows) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
	        
		rows.stream().forEach(row -> {
			String rowStr = row.toString();
			String[] arrayOfString = rowStr.split("[|]");
			String accountNumberStr = arrayOfString[0].trim().toString();
			
			accountNumberStr = accountNumberStr.replaceAll("\\D", "");
			
			String trxAmountStr = arrayOfString[1];
			String trxDescStr = arrayOfString[2];
			String trxDateStr = arrayOfString[3];
			String trxTimeStr = arrayOfString[4];
			String custIDStr = arrayOfString[5];
			
			TransactionEntity entity = new TransactionEntity();
			entity.setAccountNumber(accountNumberStr);
			entity.setTrxAmount(trxAmountStr);
			entity.setTrxDescription(trxDescStr);
			entity.setTrxDate(trxDateStr);
			entity.setTrxTime(trxTimeStr);
			entity.setCustomerID(custIDStr);
			
			transactionRepository.save(entity);
        });
	        
	}
}
