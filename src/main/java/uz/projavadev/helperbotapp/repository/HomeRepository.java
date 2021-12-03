package uz.projavadev.helperbotapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.projavadev.helperbotapp.entity.Car;
import uz.projavadev.helperbotapp.entity.File;
import uz.projavadev.helperbotapp.entity.Home;

import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {

    Home findByUserId(Long id);
}
