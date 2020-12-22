package agie.poly.homestay.controller;

import agie.poly.homestay.entity.HomeStay;
import agie.poly.homestay.entity.TaiKhoan;
import agie.poly.homestay.repository.HomeStayRepository;
import agie.poly.homestay.repository.UserRepository;
import agie.poly.homestay.service.HomeStayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping("/homestay")
public class HomeStayController {
    @Autowired
    private HomeStayService homeStayService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HomeStayRepository homeStayRepository;
    @GetMapping(value = {"/",""})
    public String homestay(Model model, @PageableDefault(size = 3,sort = "id",direction = Sort.Direction.ASC) Pageable pageable){
        model.addAttribute("homeStayList",homeStayRepository.findAll(pageable));
        return "index";
    }

    @GetMapping("/seach")
    public String search(@RequestParam("address")String address, Model model,@PageableDefault(size = 6,sort = "id",direction = Sort.Direction.ASC) Pageable pageable){
        model.addAttribute("page",homeStayRepository.findSearch(address,pageable));
        return "rooms";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email")String email, @RequestParam("pass")String pass, Model model, HttpSession session,
                        @PageableDefault(size = 3,sort = "id",direction = Sort.Direction.ASC) Pageable pageable){
        model.addAttribute("homeStayList",homeStayRepository.findAll(pageable));
        TaiKhoan taiKhoan = userRepository.findByEmailAndMatKhau(email,pass);
        session.setAttribute("loginIdObject",taiKhoan);
        model.addAttribute("loginId",taiKhoan);
        return "index";
    }
    @GetMapping("/logout")
    public String logout(Model model,HttpSession session,@PageableDefault(size = 3,sort = "id",direction = Sort.Direction.ASC) Pageable pageable){
        model.addAttribute("homeStayList",homeStayRepository.findAll(pageable));
        TaiKhoan taiKhoan = null;
        session.setAttribute("loginIdObject",taiKhoan);
        model.addAttribute("loginId",taiKhoan);
        return "index";
    }
}
