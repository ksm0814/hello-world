package net.slipp.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
	private List<User> users = new ArrayList<User>();
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@GetMapping("/")
	public String welcomePage() {
		return "form";
	}
	@PostMapping("/create")
	public String create(User user) {
		users.add(user);
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", users);
		return "list";
	}
	
	@GetMapping("/profile/{userId}") 
	public String profile(@PathVariable String userId, Model model) {
		for (User user : users)
			if(user.getUserId().equals(userId))
				model.addAttribute("profileUser", user);
		return "profile";
	}

//	@GetMapping("/profile/{name}") 
//	public String profileFind(@PathVariable String name, Model model) {
//		log.info("name : {}", name);
//		for (User user : users)
//			if(user.getName().equals(name))
//				model.addAttribute("profileUser", user);
//		return "profile";
//	}
	
	
}
