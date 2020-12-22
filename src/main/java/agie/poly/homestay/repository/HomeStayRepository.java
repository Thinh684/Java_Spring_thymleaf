package agie.poly.homestay.repository;

import agie.poly.homestay.entity.HomeStay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nguyen Danh Luong on 8/2/2020.
 * @created 02/08/2020
 * @project poly-homestay-mai-la-ae
 */
@Repository
public interface HomeStayRepository extends JpaRepository<HomeStay,Long> {
    Page<HomeStay> findAll(Pageable pageable);
    @Query("select hs from HomeStay hs where hs.diaChi like %?1%")
    Page<HomeStay> findSearch(String diachi, Pageable pageable);
}
