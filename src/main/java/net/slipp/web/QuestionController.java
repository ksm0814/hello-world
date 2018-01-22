package net.slipp.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
	private List<Question> questions = new ArrayList<Question>();
	
	@GetMapping("/index")
	public String welcomePage(Model model) {
		model.addAttribute("question", questions);
		return "index";
	}

	@GetMapping("/question_form")
	public String question() {
		return "question_form";
	}
	
	@PostMapping("/show")
	public String question(Question question, Model model) {
		questions.add(question);
		model.addAttribute("question", questions);
		return "show";
	}
	
	@GetMapping("/show")
	public String show(Model model) {
		model.addAttribute("question", questions);
		return "show";
	}
}
