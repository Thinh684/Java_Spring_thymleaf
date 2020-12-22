package agie.poly.homestay.controller;

import agie.poly.homestay.repository.HomeStayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nguyen Danh Luong on 8/2/2020.
 * @created 02/08/2020
 * @project poly-homestay-mai-la-ae
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private HomeStayRepository homeStayRepository;

    @GetMapping("/")
    public String home(){
        return "index";
    }

}
