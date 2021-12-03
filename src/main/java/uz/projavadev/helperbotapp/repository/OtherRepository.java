package uz.projavadev.helperbotapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.projavadev.helperbotapp.entity.Car;
import uz.projavadev.helperbotapp.entity.Other;

@Repository
public interface OtherRepository extends JpaRepository<Other, Long> {
}
