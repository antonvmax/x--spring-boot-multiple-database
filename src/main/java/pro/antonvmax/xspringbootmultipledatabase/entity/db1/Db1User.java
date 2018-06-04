package pro.antonvmax.xspringbootmultipledatabase.entity.db1;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Db1User implements Serializable {
    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    private String userid;

    private String userdescrip;
    private String password;

    protected Db1User() {}

    public Db1User(String userid, String userdescrip, String password) {
        this.userid = userid;
        this.userdescrip = userdescrip;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Db1User{" +
                "userid='" + userid + '\'' +
                ", userdescrip='" + userdescrip + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
