package pro.antonvmax.xspringbootmultipledatabase.repository.db1;

import pro.antonvmax.xspringbootmultipledatabase.entity.db1.Db1User;
import org.springframework.data.repository.CrudRepository;

public interface Db1Repository extends CrudRepository<Db1User, Long> {

    Db1User findFirstByUserid(String userid);
}
