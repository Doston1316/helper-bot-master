package uz.projavadev.helperbotapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.projavadev.helperbotapp.entity.Car;
import uz.projavadev.helperbotapp.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
