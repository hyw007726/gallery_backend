package cn.batenglish.gallery.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import cn.batenglish.gallery.service.UserService;
@Controller // This means that this class is a Controller
@RequestMapping(path="/")
public class MainController {
    private final UserService userService;
    private final HttpSession session;
    @Autowired
    public MainController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }
    @GetMapping("/")
    public String index() {
        return "forward:index.html";
    }
    public static class TestObject {
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
    @GetMapping("/api/test")
    @ResponseBody
    public TestObject test(HttpServletResponse response) {
        TestObject testObject = new TestObject();
        testObject.setData("Data from server");
        String sessionId = session.getId();
//        Cookie cookieSessionId = new Cookie("sessionId", sessionId);
//        cookieSessionId.setMaxAge(-1); // Cookie is valid for the duration of the session
//        response.addCookie(cookieSessionId);
        response.addCookie(new Cookie("domain", "gallery"));
        //add cookie for user, time; sessionID is auto generated;
        return testObject;
    }
//maps any URL path that does not start with "index.html" or "api" to the index.html file.
    @GetMapping("/{path:^(?!index\\.html|api|auth|uploads).*}/**")
    public String redirectToIndex() {
        return "redirect:/";
    }

}