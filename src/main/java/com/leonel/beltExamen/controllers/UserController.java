package com.leonel.beltExamen.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.leonel.beltExamen.models.Idea;
import com.leonel.beltExamen.models.User;
import com.leonel.beltExamen.services.IdeaService;
import com.leonel.beltExamen.services.UserService;
import com.leonel.beltExamen.validator.UserValidator;

@Controller
public class UserController {
	private final UserService userService;
	private final UserValidator userValidator;

	public UserController(UserService userService, UserValidator userValidator) {
		this.userService = userService;
		this.userValidator = userValidator;

	}
	@Autowired
	private IdeaService ideaService;

	
	@RequestMapping("/")
	public String registerLogin(@ModelAttribute("user") User user, HttpSession session, Model model) {
		if (session.getAttribute("errorMessage") != null) {
			model.addAttribute("errorMessage ", session.getAttribute("errorMessage"));
			return "login-registrer.jsp";
		} else
			return "login-registrer.jsp";

	}
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, Model model, BindingResult result, HttpSession session) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "login-registrer.jsp";
		}
		User user1 = userService.registerUser(user);
		session.setAttribute("userId", user1.getId());
		return "redirect:/ideas";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session) {
		boolean isAuntentico =userService.authenticateUser(email, password);
		
		if (isAuntentico) {
			User user2 = userService.findByEmail(email);
			session.setAttribute("userId", user2.getId());
			return "redirect:/ideas";
		} else {
			session.setAttribute("errorMessage", "Credenciales invalidas, intente de nuevo");
			return "redirect:/";
		}

	}

	@RequestMapping("/ideas")
	public String home(HttpSession session, Model model) {
		Long uID = (Long) session.getAttribute("userId");
		User user = userService.findUserById(uID);
		Iterable<Idea> ideas = ideaService.allIdeas();
		model.addAttribute("ideas", ideas);
		model.addAttribute("user", user);
		return "index.jsp";

	}
	@RequestMapping("/ideas/lowest")
	public String homeL(HttpSession session, Model model) {
		Long uID = (Long) session.getAttribute("userId");
		User user = userService.findUserById(uID);
		Iterable<Idea> ideas = ideaService.allIdeasL();
		model.addAttribute("ideas", ideas);
		model.addAttribute("user", user);
		return "index.jsp";

	}
	@RequestMapping("/ideas/highest")
	public String homeH(HttpSession session, Model model) {
		Long uID = (Long) session.getAttribute("userId");
		User user = userService.findUserById(uID);
		Iterable<Idea> ideas = ideaService.allIdeasH();
		model.addAttribute("ideas", ideas);
		model.addAttribute("user", user);
		return "index.jsp";

	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
}
