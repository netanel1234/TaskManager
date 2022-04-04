package com.netanel.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping("/list")
    public String list() {
        return "list";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/add")
    public String add() {
        return "add";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String name,
                          @RequestParam String password,
                          HttpSession session) {
        User user = new User(name, password);
        userRepository.save(user);
        session.setAttribute("id", user.getUserid());
        session.setAttribute("list", new ArrayList<Item>());
        return "list";
    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam String task,
                          HttpSession session,
                          Model model) {
        Item item = new Item((Integer) session.getAttribute("id"), task);
        itemRepository.save(item);
        List<Item> list = itemRepository.findItemsByUserId((Integer) session.getAttribute("id"));
        model.addAttribute("items", list);
        return "list";
    }

    @GetMapping("/update")
    public String update(@RequestParam Integer serialnumber,
                         HttpSession session) {
        itemRepository.deleteById(serialnumber);
        session.setAttribute("serialnumber", serialnumber);
        return "update";
    }

    @GetMapping("/updateItem")
    public String updateItem(@RequestParam String task,
                             HttpSession session,
                             Model model) {
        Item item = new Item((Integer) session.getAttribute("id"), task);
        itemRepository.save(item);
        List<Item> list = itemRepository.findItemsByUserId((Integer) session.getAttribute("id"));
        model.addAttribute("items", list);
        return "list";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam Integer serialnumber,
                             HttpSession session,
                             Model model) {
        print("in deleteItem() serialnumber = " + serialnumber + " session.id = " + session.getAttribute("id"));
        itemRepository.deleteById(serialnumber);
        List<Item> list = itemRepository.findItemsByUserId((Integer) session.getAttribute("id"));
        model.addAttribute("items", list);
        return "list";
    }

    @GetMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User connected = null;
        Iterable<User> iterable = userRepository.findAll();
        for(User user : iterable)
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                connected = user;
                break;
            }
        if (connected==null)
            return "notFound";
        Integer id = connected.getUserid();
        session.setAttribute("id", id);
        List<Item> list = itemRepository.findItemsByUserId(id);
        model.addAttribute("items", list);
        return "list";
    }

    @GetMapping("/logout")
    public String logout() {
        return "hello";
    }

    @GetMapping("/getUsers")
    public String getUsers(Model model) {
        print("in getUsers()");
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    public void printCookie(Cookie cookie) {
        System.out.println("cookie Name = " + cookie.getName() + " cookie Value = " + cookie.getValue());
    }

    public String getIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String id = null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("id"))
            {
                id = cookie.getValue();
                break;
            }
        }
        return id;
    }

    public void print(String str) {
        System.out.println(str);
    }

}