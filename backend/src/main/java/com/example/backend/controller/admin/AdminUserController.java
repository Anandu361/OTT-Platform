package com.example.backend.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.backend.models.UserModel;
import com.example.backend.models.WatchHistoryModel;
import com.example.backend.models.WatchlistModel;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.WatchHistoryRepository;
import com.example.backend.repository.WatchlistRepository;

@Controller
public class AdminUserController {

	@Autowired
    private final UserRepository userRepository;
    
	@Autowired
    private final WatchHistoryRepository watchHistoryRepository;
	
	@Autowired
	private final WatchlistRepository watchlistRepository;


	public AdminUserController(
            UserRepository userRepository,
            WatchHistoryRepository watchHistoryRepository,
            WatchlistRepository watchlistRepository) {

        this.userRepository = userRepository;
        this.watchHistoryRepository = watchHistoryRepository;
        this.watchlistRepository = watchlistRepository;
    }

    // 🔹 List + Search users
    @GetMapping("/admin/users")
    public String listUsers(
            @RequestParam(required = false) String keyword,
            Model model) {

        List<UserModel> users;

        if (keyword != null && !keyword.isBlank()) {
            users = userRepository
                    .findByEmailContainingIgnoreCase(keyword);
        } else {
            users = userRepository.findAll();
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);

        return "admin/users/users";
    }

    // 🔒 Block user
    @GetMapping("/admin/users/block/{id}")
    public String blockUser(@PathVariable Long id) {
        UserModel user = userRepository.findById(id).orElseThrow();
        user.setBlocked(true);
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    // 🔓 Unblock user
    @GetMapping("/admin/users/unblock/{id}")
    public String unblockUser(@PathVariable Long id) {
        UserModel user = userRepository.findById(id).orElseThrow();
        user.setBlocked(false);
        userRepository.save(user);
        return "redirect:/admin/users";
    }
    
    @GetMapping("/admin/users/history/{id}")
    public String viewUserHistory(
            @PathVariable Long id,
            Model model) {

    	UserModel user = userRepository.findById(id).orElseThrow();

    	List<WatchHistoryModel> history =
    	        watchHistoryRepository.findByUserOrderByWatchedAtDesc(user);

    	model.addAttribute("user", user);
    	model.addAttribute("history", history);

        return "admin/users/history";
    }
    
    @GetMapping("/admin/users/watchlist/{id}")
    public String viewUserWatchlist(
            @PathVariable Long id,
            Model model) {

        UserModel user = userRepository.findById(id).orElseThrow();

        List<WatchlistModel> watchlist =
                watchlistRepository.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("watchlist", watchlist);

        return "admin/users/watchlist";
    }

}
