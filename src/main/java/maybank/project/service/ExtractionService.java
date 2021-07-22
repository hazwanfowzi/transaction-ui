package maybank.project.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ExtractionService {
	void extractTextFileMain(MultipartFile inputStreamFile);
}
