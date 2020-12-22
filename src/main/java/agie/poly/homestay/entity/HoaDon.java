package agie.poly.homestay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "hoadon")
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldNameConstants
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String maCode;

    @ManyToOne
    @JoinColumn(name = "maTaiKhoan")
    private TaiKhoan taiKhoan;

    private double gia;

    @Column(name = "date_check_in")
    private Date dateCheckIn;

    @Column(name = "date_check_out")
    private Date dateCheckOut;

    private String tenKhachHang;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean status;

    @OneToOne
    @JoinColumn(name = "homeStayId")
    private HomeStay homeStay;

    private Date timeBookIn;

    private Date timeBookOut;

    public HoaDon(Long id,TaiKhoan taiKhoan,double gia,Date dateCheckIn,Date dateCheckOut,String tenKhachHang,
                  String email,HomeStay homeStay) {
        this.id=id;
        this.taiKhoan=taiKhoan;
        this.gia=gia;
        this.dateCheckIn=dateCheckIn;
        this.dateCheckOut=dateCheckOut;
        this.tenKhachHang=tenKhachHang;
        this.email=email;
        this.homeStay=homeStay;
    }
}
