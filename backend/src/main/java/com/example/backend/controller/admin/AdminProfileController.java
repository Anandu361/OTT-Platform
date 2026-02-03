package com.example.backend.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.backend.models.UserModel;
import com.example.backend.service.CustomUserDetail;
import com.example.backend.service.CustomUserDetailsService;

@Controller
public class AdminProfileController {

    private final CustomUserDetailsService customUserDetailsService;

    public AdminProfileController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    // 👤 PROFILE PAGE
    @GetMapping("/admin/profile")
    public String adminProfile(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/admin/login";
        }

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        model.addAttribute("user", user);
        return "admin/profile";
    }
    
    @GetMapping("/admin/profile/update-password")
    public String updatePasswordPage() {
        return "admin/change-password";
    }


    // 🔐 UPDATE PASSWORD (REUSES USER LOGIC)
    @PostMapping("/admin/profile/update-password")
    public String updatePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Model model
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/admin/login";
        }

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        boolean updated = customUserDetailsService.updatePassword(
                user,
                oldPassword,
                newPassword
        );

        if (updated) {
            // ✅ SUCCESS → redirect to profile with flag
            return "redirect:/admin/profile?passwordChanged=true";
        }

        // ❌ FAILURE → stay on same page with error
        model.addAttribute("error", "Old password is incorrect");
        return "admin/change-password";
    }

}
