package agie.poly.homestay.controller;

import agie.poly.homestay.dto.CheckDto;
import agie.poly.homestay.dto.InfomationUser;
import agie.poly.homestay.entity.HoaDon;
import agie.poly.homestay.entity.HomeStay;
import agie.poly.homestay.entity.TaiKhoan;
import agie.poly.homestay.repository.HoaDonRepository;
import agie.poly.homestay.repository.HomeStayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/room-detail")
public class RoomDetailsController {
    @Autowired
    private HomeStayRepository homeStayRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    //hiển thị phòng theo id
    @GetMapping("/{id}")
    public String roomDetail(@PathVariable Long id, Model model,HttpSession session) {
        model.addAttribute("room", homeStayRepository.findById(id).get());
        model.addAttribute("checkObject", new CheckDto(id));
        session.setAttribute("checkHopLe","khonghople");
        return "room-details";
    }

    @PostMapping("/{id}")
    public String checkBooking(Model model, @ModelAttribute CheckDto checkDto, @PathVariable Long id, HttpSession session) throws ParseException {
        List<HoaDon> list = hoaDonRepository.checkHoaDon(checkDto.getCheckIn(), checkDto.getCheckOut(), checkDto.getId());
        if(list.size()>0){
            HoaDon hoaDon = list.get(0);
            String date1 = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(hoaDon.getDateCheckIn().toLocalDate());
            String date2 = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(hoaDon.getDateCheckOut().toLocalDate());
            model.addAttribute("message","Từ "+date1+" đến "+date2+" đã có người đặt hãy chọn khoảng thời gian khác");
        }else {
            model.addAttribute("message","Thông tin hợp lệ bạn có thể đặt phòng");
            session.setAttribute("checkHopLe","hople");
            session.setAttribute("thongTinHoaDon",checkDto);
        }
        model.addAttribute("room", homeStayRepository.findById(id).get());
        model.addAttribute("checkObject", new CheckDto(checkDto.getCheckIn(),checkDto.getCheckOut(),checkDto.getId()));
        session.setAttribute("timeData",new CheckDto(checkDto.getCheckIn(),checkDto.getCheckOut(),checkDto.getId()));
        return "room-details";
    }

    @GetMapping("/{id}/book")
    public String booking(Model model,HttpSession session,@PathVariable Long id){
        model.addAttribute("room", homeStayRepository.findById(id).get());
        if(session.getAttribute("checkHopLe") == "hople"){

            session.setAttribute("checkHopLe","khonghople");
            TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("loginIdObject");
            if(taiKhoan != null){
                model.addAttribute("infoUser",new InfomationUser(taiKhoan.getTenTaiKhoan(),taiKhoan.getEmail()));
            }else{
                model.addAttribute("infoUser",new InfomationUser());
            }
            model.addAttribute("loginId",taiKhoan);
            model.addAttribute("timeData",(CheckDto)session.getAttribute("timeData"));
            return "contact";
        }else {
            model.addAttribute("room", homeStayRepository.findById(id).get());
            model.addAttribute("checkObject", new CheckDto(id));
            session.setAttribute("checkHopLe","khonghople");
            model.addAttribute("message","hãy nhập ngày cần dặt và kiểm tra thông tin");
            return "room-details";
        }

    }
    @PostMapping("/{id}/book/submit-invoice")
    public String submitInvoice(@PathVariable Long id,HttpSession session,@ModelAttribute InfomationUser infoUser,Model model){
      HomeStay homeStay = homeStayRepository.findById(id).get();
      if(session.getAttribute("loginIdObject")!= null){
          TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("loginIdObject");
          CheckDto checkDto = (CheckDto) session.getAttribute("timeData");
          HoaDon hoaDon = new HoaDon((long)10,taiKhoan,homeStay.getGia(),checkDto.getCheckIn(),checkDto.getCheckOut(),infoUser.getUsername(),infoUser.getEmaul(),homeStay);
          hoaDonRepository.save(hoaDon);
          model.addAttribute("mesage","Đặt phòng thành công mỹ mãn");



          //viết gửi mã code từ đây
          //ở hóa đơn new contruster vào class HoaDon thêm 1 cái là mãcode ở hàm tạo nữa để thêm mã code
          //rồi thực hiện insert hóa đơn line89 hóa đơn được tạo ra. rồi gửi mã code về gmail
          // viết gửi mã code cả 2 bên if và else giống nhau. if là thêm hóa đơn có tài khoản, else là không


          return "blog";
      }else{
          TaiKhoan taiKhoan = null;
          CheckDto checkDto = (CheckDto) session.getAttribute("timeData");
          HoaDon hoaDon = new HoaDon((long)10,taiKhoan,homeStay.getGia(),checkDto.getCheckIn(),checkDto.getCheckOut(),infoUser.getUsername(),infoUser.getEmaul(),homeStay);
          hoaDonRepository.save(hoaDon);
          model.addAttribute("mesage","Đặt phòng thành công mỹ mãn");


          //viết gửi mã code từ đây



          return "blog";
      }

    }
}
