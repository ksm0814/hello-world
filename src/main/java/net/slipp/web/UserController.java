package net.slipp.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;


@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@PostMapping("")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}
	
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if(user == null) {
			log.info("Login Fail");
			return "redirect:/users/loginForm";
		}
		if(!user.matchPassword(password)) {
			log.info("Login Fail");
			return "redirect:/users/loginForm";
		}
		log.info("Login Success");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessionedUser.matchId(id)){
			throw new IllegalStateException("You can't update other user's profile!!!");
		}
		model.addAttribute("user", userRepository.findOne(id));
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessionedUser.matchId(id)){
			throw new IllegalStateException("You can't update other user's profile!!!");
		}
		
		
		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/profile/{id}") 
	public String profile(@PathVariable Long id, Model model) {
		model.addAttribute("profileUser", userRepository.findOne(id));
		return "/user/profile";
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
