package com.ken.controller;

import com.ken.constants.PieceConstants;
import com.ken.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Shiro demo
 */
@RestController
@RequestMapping(value = "/shiro")
public class ShiroController {

    @PostMapping("/login")
    public void login(String name, String password) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password );
        subject.login(token);
    }

    @RequiresRoles("1")
    @GetMapping("/check")
    public String checkAuth() {
        return "Yes, You have this right.";
    }

    @RequiresRoles("1")
    @GetMapping("/user")
    public String getUser() {
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute(PieceConstants.SESSION_USER_INFO);
        return user.getName();
    }
}
