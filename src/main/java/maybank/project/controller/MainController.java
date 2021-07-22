package maybank.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import maybank.project.exception.SystemException;
import maybank.project.service.MessageService;

@Controller
public class MainController {
	
	@Autowired 
	MessageService messageService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String home(Model model, HttpServletRequest request) {
		model.addAttribute("module", "dashboard");
		return "index_page";
	}
	
	@GetMapping("/resources/javascripts/js-messages.js")
	public String jsMessages(Model model) throws SystemException{
		
		 model.addAttribute("messages", messageService.getMessages(LocaleContextHolder.getLocale()));
		return "js_messages";
		
	}
}
