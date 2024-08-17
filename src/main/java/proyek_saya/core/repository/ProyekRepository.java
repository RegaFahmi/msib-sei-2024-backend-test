package proyek_saya.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyek_saya.core.model.Proyek;

public interface ProyekRepository extends JpaRepository<Proyek, Long> {
}

