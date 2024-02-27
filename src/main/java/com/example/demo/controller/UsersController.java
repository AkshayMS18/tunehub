package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

//import com.example.demo.entity.LoginData;
import com.example.demo.entity.Song;
import com.example.demo.entity.Users;
import com.example.demo.service.SongService;
import com.example.demo.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	
	@Autowired
	UsersService service;
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user)
	{
		boolean userStatus = service.emailExists(user.getEmail());
		if(userStatus == false)
		{
		service.addUser(user);
		System.out.println("user added");
		}else
		{
			System.out.println("user already exists");
		}
		return "home";
	}
	@Autowired
	SongService songService;
	@PostMapping("/validate")
	public String validate(@RequestParam("email")String email,@RequestParam("password")String password, HttpSession session,Model model)
	{
		
		if(service.validateUser(email, password)==true)
		{
			String role = service.getRole(email);
			
			session.setAttribute("email", email);
			if(role.equals("admin"))
			{
				return "admin";
			}
			else
			{
				Users user = service.getUser(email);
				boolean userStatus = user.isPremium();
				List<Song> songsList = songService.fetchAllSongs();
			    model.addAttribute("songs", songsList);
				model.addAttribute("isPremium", userStatus);
				return "customer";
			}
			
		}
		else
		{
			return "login";
		}
	}
	
/*	@GetMapping("/pay")
	public String pay(@RequestParam String email)
	{
	boolean paymentStatus = false;//payment api
	
	if(paymentStatus == true)
	{
		Users user = service.getUser(email);
		user.setPremium(true);
		service.updateUser(user);
	}
	 return "login";
	}
	*/
	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		session.invalidate();
		return "login";
	}
			
}
