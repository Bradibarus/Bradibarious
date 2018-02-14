package xyz.bradibarus.bradibarious.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class UIcontroller {

@RequestMapping(value = "/app")
public String app(){
    return "app";
    }
@RequestMapping(value = "/login")
public String login(){ return "login"; }
@RequestMapping(value = "/")
public String index(){ return "app"; }
@RequestMapping(value = "/signup")
public String signup(){ return "signup"; }
}
