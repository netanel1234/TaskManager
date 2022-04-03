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
        print("in addUser()");
        User user = new User(name, password);
        print(user.toString());
        userRepository.save(user);
        session.setAttribute("id", user.getUserid());
        session.setAttribute("list", new ArrayList<Item>());
        return "list";
    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam String task,
                          HttpSession session,
                          Model model) {
        print("in addItem()");
        Item item = new Item((Integer) session.getAttribute("id"), task);
        print("item = " + item.toString());
        itemRepository.save(item);
        List<Item> list = (ArrayList<Item>) session.getAttribute("list");
        System.out.println(list);
        list.add(item);
        System.out.println(list);
        model.addAttribute("items", list);
        return "list";
    }

    @GetMapping("/update")
    public String update(@RequestParam Integer serialnumber,
                         HttpSession session) {
        print("in update(), serialnumber = " + serialnumber + " session.id = " + session.getAttribute("id"));
        itemRepository.deleteById(serialnumber);
        session.setAttribute("serialnumber", serialnumber);
        return "update";
    }

    @GetMapping("/updateItem")
    public String updateItem(@RequestParam String task,
                             HttpSession session,
                             Model model) {
        print("in updateItem(), task = " + task + " session.id = " + session.getAttribute("id"));
        Item item = new Item((Integer) session.getAttribute("id"), task);
        System.out.println(item.toString());
        itemRepository.save(item);
        ArrayList<Item> list = new ArrayList<>();
        Iterable<Item> iterable = itemRepository.findAll();
        for(Item i : iterable)
            if(i.getUserid() == ((Integer) session.getAttribute("id")))
                list.add(i);
        System.out.println(list);
        model.addAttribute("items", list);
        return "list";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam Integer serialnumber,
                             HttpSession session,
                             Model model) {
        print("in deleteItem() serialnumber = " + serialnumber + " session.id = " + session.getAttribute("id"));
        itemRepository.deleteById(serialnumber);
        Iterable<Item> iterable = itemRepository.findAll();
        ArrayList<Item> list = new ArrayList<>();
        for(Item i : iterable)
            if(i.getUserid() == ((Integer) session.getAttribute("id")))
                list.add(i);
        System.out.println(list);
        model.addAttribute("items", list);
        return "list";
    }

    @GetMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        print("in login() username = " + username + "password = " + password + "session.id = " + session.getAttribute("id"));
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
        System.out.println("id = " + id);
        session.setAttribute("id", id);
        List<Item> list = new ArrayList<>();
        System.out.println(list);
        Iterable<Item> itemsList = itemRepository.findAll();
        for(Item i : itemsList)
            if(i.getUserid() == Integer.valueOf(id))
                list.add(i);
        model.addAttribute("items", list);
        System.out.println(list);
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