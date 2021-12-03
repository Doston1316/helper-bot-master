package uz.projavadev.helperbotapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.projavadev.helperbotapp.entity.Car;
import uz.projavadev.helperbotapp.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
