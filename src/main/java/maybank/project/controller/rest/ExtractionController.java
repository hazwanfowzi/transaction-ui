package maybank.project.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import maybank.project.config.BatchConfiguration;
import maybank.project.dto.ResponseMessage;
import maybank.project.exception.FileException;
import maybank.project.service.ExtractionService;

@RestController
@RequestMapping("/api/extraction")
public class ExtractionController {
	Logger LOGGER = LoggerFactory.getLogger(ExtractionController.class);
	
	@Autowired ExtractionService extractionService;
	@Autowired BatchConfiguration batchConfiguration;
	
//	@PostMapping("/file")
//    public ResponseMessage postProceesFile(@RequestParam("fileUpload") MultipartFile file) {
//		
//		ResponseMessage message = new ResponseMessage();
//		try {
//			batchConfiguration.reader();
//			
////			FlatFileItemReader<TransactionDTO> reader = batchConfiguration.reader();
//			
//			
////			extractionService.extractTextFileMain(file);
//            message.setStatus(ResponseMessage.OK);
//			return  message;
//        } catch (Exception e) {
//            throw new FileException(e.getMessage());
//        }
//		
//    }
	
	@PostMapping("/file")
    public ResponseMessage postProceesFile(@RequestParam("fileUpload") MultipartFile file) {
		
		ResponseMessage message = new ResponseMessage();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
			    
			    extractionService.extractTextFileMain(file);
	            message.setStatus(ResponseMessage.OK);
			}else {
				message.setStatus(ResponseMessage.WARN);
				
			}
			return  message;
        } catch (Exception e) {
            throw new FileException(e.getMessage());
        }
		
    }
}
