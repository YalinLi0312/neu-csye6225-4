package com.team4.controller;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.team4.repository.CandidateRepository;
import com.team4.repository.EmployerRepository;
import com.team4.util.URLMapper;

@Controller
public class AjaxController {
	
	@Autowired
	EmployerRepository employerRepository;
	
	@Autowired
	CandidateRepository candidateRepository;

	
	@RequestMapping(value=URLMapper.AJAXEMAILPATTERN, method = RequestMethod.GET)
    	public @ResponseBody String checkEmailPattern(HttpServletRequest request) {
		String email = request.getParameter("email");
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher m = p.matcher(email);
            boolean matchFound = m.matches();
            if (!matchFound) {
        	return "Invalid Email Address";
            }else{
        	return "Valid Email Address";
            }
   	}
	
	@RequestMapping(value = URLMapper.AJAXCHECK, method = RequestMethod.GET)
	public @ResponseBody String processRequest(HttpServletRequest request){
            String role = request.getParameter("role");
            String username = request.getParameter("username");
            System.out.println(role+" "+username);
            String result = "";
            if(role.equalsIgnoreCase("employer")){
            	System.out.println("in employer");
				if(employerRepository.findByUsername(username) == null){
					result = "Valid Username";
				}else{
					System.out.println("invalid employer username");
					
					result = "Invalid Username";
				}
			}
			
			if(role.equalsIgnoreCase("candidate")){
				if(candidateRepository.findByUsername(username) == null){
					System.out.println("valid candidate username");
					result = "Valid Username";
				}else{
					System.out.println("invalid candidate username");
					result = "Invalid Username";
				}
			}
            return result;
        }

}
