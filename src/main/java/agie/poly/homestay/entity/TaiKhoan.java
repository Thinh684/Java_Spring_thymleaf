package agie.poly.homestay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "taikhoan")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tenTaiKhoan;
    private String matKhau;
    private String email;
    private String phoneNumber;
    private String diaChi;
    private String role;
    private boolean active;
    @OneToMany
    @JoinColumn(name = "maTaiKhoan")
    private List<HoaDon> hoaDon;
}
