package com.polentzi.logreg.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.polentzi.logreg.models.LoginUser;
import com.polentzi.logreg.models.User;
import com.polentzi.logreg.services.UserService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index(Model viewModel) {
		viewModel.addAttribute("user", new User());
		viewModel.addAttribute("login", new LoginUser());
		return "logreg.jsp";
	}
	
	@PostMapping("/registration")
	public String register(@Valid @ModelAttribute("user") User usuario, 
			BindingResult resultado, Model viewModel) {
		if(resultado.hasErrors()) {
			viewModel.addAttribute("login", new LoginUser());
			return "logreg.jsp";
		}
		
		User usuarioRegistrado = userService.registerUser(usuario, resultado);
		viewModel.addAttribute("login", new LoginUser());
		if(usuarioRegistrado != null) {
			viewModel.addAttribute("succesRegister", "Gracias por registrarte, por favor login"); 	
		}
		return "logreg.jsp";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("login") LoginUser loginuser, 
			BindingResult resultado, Model viewModel, HttpSession sesion) {
		if(resultado.hasErrors()) {
			viewModel.addAttribute("user", new User());
			return "logreg.jsp";
		}
		
		if(userService.authenticateUser(loginuser.getEmail(), 
				loginuser.getPassword(), resultado)) {
			User usuarioLog = userService.findByEmail(loginuser.getEmail());
			sesion.setAttribute("userID",  usuarioLog.getId());
//			System.out.println(sesion.getAttribute("userID") + "atributo ");
			return "redirect:/dashboard";
			
		}else {
			viewModel.addAttribute("user", new User());
			return "logreg.jsp";
		}
	}
	
	@GetMapping("/dashboard")
	public String welcome(HttpSession sesion, Model viewModel) {
		Long userId = (Long) sesion.getAttribute("userID");
		if(userId == null) {
			return "redirect:/"; 
		}
		User usuario = userService.findUserById(userId);
		viewModel.addAttribute("usuario", usuario);
		return "dashboard.jsp";
	}
	
	@GetMapping("/logout")
	 public String logout(HttpSession session) {
		 session.setAttribute("userId", null);
		 return "redirect:/";
	 }

}