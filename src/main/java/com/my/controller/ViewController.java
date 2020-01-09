package com.my.controller;

import com.my.entity.User;
import com.my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/action")
public class ViewController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/listUsers",method = RequestMethod.POST)
    public String listUsers(Model model){
        List<User> user = userService.ListUser();
        model.addAttribute("user",user);
        return "hello";
    }

    @RequestMapping(value = "/import")
    public String exImport(@RequestParam(value = "filename")MultipartFile file, HttpSession session) {
        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = userService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }

    @RequestMapping("/index")
    public String showUser(Model model) {
        List<User> users = userService.ListUser();
        model.addAttribute("user",users);
        return "index";
    }


}
