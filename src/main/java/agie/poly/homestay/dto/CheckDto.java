package agie.poly.homestay.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CheckDto {
    private Date checkIn;
    private Date checkOut;
    private Long id;
    public CheckDto() {
    }

    public CheckDto(Long id) {
        this.id = id;

    }

    public CheckDto(Date checkIn, Date checkOut, Long id) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.id = id;
    }
}
