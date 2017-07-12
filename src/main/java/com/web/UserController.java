package com.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Task;
import com.model.User;
import com.service.SecurityService;
import com.service.TaskService;
import com.service.UserService;
import com.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private TaskService taskService;


    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

	@Autowired
	private HttpServletRequest context;
	
	
    
    private static User user;
    
    //private JdbcTemplate jdbcTemplate = ;
    private final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        user = userService.findByUsername(userForm.getUsername());
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) throws UnsupportedEncodingException {
    	user = userService.findByUsername(context.getUserPrincipal().getName());
    	if(user != null) {
    		List<Task> taskSet = user.getTasks();
    		Task[] taskList = new Task[taskSet.size()];
    		int i = 0;
    		for(Task t: taskSet){
    			taskList[i++] = t;
    		}
        	model.addAttribute("tasks", taskList);
    	}
    	
        return "welcome";
    }
    
    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    public String update(@RequestParam("id") String id,@RequestParam("name") String name,@RequestParam("start") String start,@RequestParam("end") String end,Model model) throws UnsupportedEncodingException {
    	Task task;
    	if(id != null && id.length()>0){
    		task = taskService.findById(Long.parseLong(id));
    	}else{
        	task = new Task();
        	task.setUser(user);
    	}
    	try{
    		if(start != null && end != null && start.length() >0 && end.length()>0){
    			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        	    Date parsedStart = dateFormat.parse(start);
        	    Timestamp startstamp = new java.sql.Timestamp(parsedStart.getTime());
        	    
        	    Date parsedEnd = dateFormat.parse(end);
        	    Timestamp endstamp = new java.sql.Timestamp(parsedEnd.getTime());
        	    task.setStart(startstamp);
        	    task.setEnd(endstamp);
    		}
    		task.setName(name);
    		taskService.save(task);
    	    
    	}catch(Exception e){
    		model.addAttribute("msg", "failure");
    		return "redirect:/welcome";
    	}
    	
    	return "redirect:/welcome";
    }
    
    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public String delete(@PathVariable String id,Model model) throws UnsupportedEncodingException {
    	if(id != null && id.length()>0){
    		try{
        		taskService.deleteTasksById(Long.parseLong(id));
        	    
        	}catch(Exception e){
        		model.addAttribute("msg", id);
        		System.out.println(e);
        		return "redirect:/welcome";
        	}
    	}else{
    		return "redirect:/welcome";
    	}
    	return "redirect:/welcome";
    }
}
