package uz.projavadev.helperbotapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.projavadev.helperbotapp.entity.BotUser;

@Repository
public interface UserRepository extends JpaRepository<BotUser, Long> {

    boolean existsByTgId(Integer id);

    BotUser findByTgId(Integer id);
}
