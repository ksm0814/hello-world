package net.slipp.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.UserRepository;

@Controller
public class QuestionController {
	private List<Question> questions = new ArrayList<Question>();
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/index")
	public String welcomePage(Model model) {
		model.addAttribute("question", questionRepository.findAll());
		return "index";
	}

	@GetMapping("/question_form")
	public String question() {
		return "question_form";
	}
	
	@PostMapping("/question")
	public String question(Question question, Model model) {
		questionRepository.save(question);
		return "redirect:/show";
	}
	
	@GetMapping("/show")
	public String show(Model model) {
		model.addAttribute("question", questionRepository.findAll());
		return "show";
	}
}
