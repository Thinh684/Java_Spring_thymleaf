package agie.poly.homestay.repository;

import agie.poly.homestay.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


/**
 * @author Nguyen Danh Luong on 8/2/2020.
 * @created 02/08/2020
 * @project poly-homestay-mai-la-ae
 */
@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    @Query("select hd from HoaDon hd where hd.homeStay.id = ?3 and (?1 between hd.dateCheckIn and hd.dateCheckOut or ?2 between hd.dateCheckIn and hd.dateCheckOut)")
    List<HoaDon> checkHoaDon(Date in, Date out, Long id);
}
