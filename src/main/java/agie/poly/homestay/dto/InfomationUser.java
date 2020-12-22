package agie.poly.homestay.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfomationUser {
    private String username;
    private String emaul;

    public InfomationUser(String username, String emaul) {
        this.username = username;
        this.emaul = emaul;
    }

    public InfomationUser() {
    }
}
