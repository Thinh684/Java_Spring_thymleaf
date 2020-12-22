package agie.poly.homestay.controller;

import agie.poly.homestay.entity.HoaDon;
import agie.poly.homestay.entity.HomeStay;
import agie.poly.homestay.entity.TaiKhoan;
import agie.poly.homestay.repository.HoaDonRepository;
import agie.poly.homestay.repository.HomeStayRepository;
import agie.poly.homestay.repository.UserRepository;
import agie.poly.homestay.service.HomeStayService;
import agie.poly.homestay.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Nguyen Danh Luong on 8/4/2020.
 * @created 04/08/2020
 * @project poly-homestay-mai-la-ae
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private HomeStayService homeStayService;

    @Autowired
    private HomeStayRepository homeStayRepository;

    @GetMapping("/login")
    public String loginView(Model model){
        model.addAttribute("user", new TaiKhoan());
        return "/admin/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") TaiKhoan user, HttpServletRequest request){
        TaiKhoan taiKhoan = this.userRepository.findByEmailAndMatKhau(user.getEmail(),user.getMatKhau());
        if(taiKhoan != null && (taiKhoan.getRole().matches("ADMIN") || taiKhoan.getRole().matches("STAFF"))){
            request.getSession().setAttribute("userPageAdmin", taiKhoan);
            return "redirect:/admin/receipt";
        }
        else {
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
       request.removeAttribute("userPageAdmin");
       return "redirect:/admin/login";
    }

   

    @GetMapping("/user")
    public String userView(Model model,HttpServletRequest request){
        TaiKhoan taiKhoan = (TaiKhoan)request.getSession().getAttribute("userPageAdmin");
        if(taiKhoan.getRole().matches("ADMIN")){
            model.addAttribute("users", this.userRepository.findAll(Sort.by(Sort.Direction.ASC,HoaDon.Fields.id)));
            return "admin/user";
        }
        else  {
            return "admin/403";
        }

    }



    
    @GetMapping("/receipt")
    public String receiptView(Model model){
        model.addAttribute("receipts",this.hoaDonRepository.findAll(Sort.by(Sort.Direction.DESC, HoaDon.Fields.id)));
        return "/admin/receipt";
    }

    @GetMapping("/receipt/update-status/{id}")
    public String updateStatus(@PathVariable Long id){
        this.receiptService.updateStatus(id);
        return "redirect:/admin/receipt";
    }

    @GetMapping("/user/{id}")
    public String editView(@PathVariable Long id,Model model){
        model.addAttribute("user", this.userRepository.findById(id).get());
        return "/admin/update-user";
    }

    @GetMapping("/user/update-role/{id}")
    public String updateRole(@PathVariable Long id){
        TaiKhoan taiKhoan = this.userRepository.findById(id).get();
        if(taiKhoan != null){
            taiKhoan.setId(id);
            taiKhoan.setRole("STAFF");
            this.userRepository.save(taiKhoan);
            return "redirect:/admin/user";
        }
        else {
            return "redirect:/admin/user";
        }
    }

    @PostMapping("/user/{id}")
    public String update(@ModelAttribute TaiKhoan taiKhoan,@PathVariable Long id){
        TaiKhoan map = this.userRepository.findById(id).get();
        map.setDiaChi(taiKhoan.getDiaChi());
        map.setPhoneNumber(taiKhoan.getPhoneNumber());
        map.setTenTaiKhoan(taiKhoan.getTenTaiKhoan());
        if(taiKhoan.isActive() != false){
            map.setActive(true);
        }
        if(taiKhoan.getRole() != null){
            map.setRole(taiKhoan.getRole());
        }
        this.userRepository.save(map);
        return "redirect:/admin/user";
    }


    @GetMapping("/user/update-password/{id}")
    public String viewUpdatePassword(@PathVariable Long id,Model model){
        TaiKhoan entity = this.userRepository.findById(id).get();
        if(entity != null){
            model.addAttribute("user",entity.getId());
            return "/admin/update-password";
        }
        else {
            return "admin/404";
        }
    }


    @PostMapping("/user/update-password/{id}")
    public String updatePassword(@PathVariable Long id, @RequestParam String oldPass, @RequestParam String confirmPassword){
        TaiKhoan taiKhoan = this.userRepository.findByMatKhau(oldPass);
        if(taiKhoan != null){
            taiKhoan.setId(id);
            taiKhoan.setMatKhau(confirmPassword);
            this.userRepository.save(taiKhoan);
            return "redirect:/admin/user/" + id;
        }
        else {
            return "redirect:/admin/user/update-password/"+ id;
        }
    }


    @GetMapping("/home-stay")
    public String viewHomeStay(Model model){
        model.addAttribute("homestays",this.homeStayRepository.findAll());
        return "admin/homestay";
    }

    @GetMapping("/home-stay/create")
    public String viewCreateHomeStay(Model model){
        model.addAttribute("homestay",new HomeStay());
        return "/admin/create-home-stay";
    }

    @PostMapping("/home-stay/create")
    public String createHomeStay(@ModelAttribute("homestay") HomeStay homeStay){
        this.homeStayRepository.save(homeStay);
        return "redirect:/admin/home-stay";
    }

    @GetMapping("/home-stay/update/{id}")
    public String viewUpdateHomeStay(@PathVariable Long id,Model model){
        model.addAttribute("homestay",this.homeStayRepository.findById(id).get());
        return "admin/update-homestay";
    }

    @PostMapping("/home-stay/update/{id}")
    public String updateHomeStay(@PathVariable Long id,@ModelAttribute("homestay") HomeStay homeStay){
        homeStay.setId(id);
        this.homeStayRepository.save(homeStay);
        return "redirect:/admin/home-stay";
    }
}
