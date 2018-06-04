package pro.antonvmax.xspringbootmultipledatabase.repository.db2;

import pro.antonvmax.xspringbootmultipledatabase.entity.db2.Db2User;
import org.springframework.data.repository.CrudRepository;

public interface Db2Repository extends CrudRepository<Db2User, Long> {

    Db2User findFirstByUser(String user);
}
