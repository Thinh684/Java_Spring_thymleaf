package agie.poly.homestay.service;

import agie.poly.homestay.entity.HoaDon;
import agie.poly.homestay.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nguyen Danh Luong on 8/7/2020.
 * @created 07/08/2020
 * @project poly-homestay-mai-la-ae
 */
@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public void updateStatus(Long id) {
        HoaDon hoaDon = this.hoaDonRepository.findById(id).get();
        if(hoaDon != null){
            hoaDon.setStatus(false);
        }
        this.hoaDonRepository.save(hoaDon);
    }
}
