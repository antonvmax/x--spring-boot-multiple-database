package pro.antonvmax.xspringbootmultipledatabase.entity.db2;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Db2User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String user;
    private Long lvl;

    protected Db2User() {}

    public Db2User(String user, Long lvl) {
        this.user = user;
        this.lvl = lvl;
    }

    @Override
    public String toString() {
        return "Db2User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", lvl=" + lvl +
                '}';
    }
}
