package br.com.lp2.trabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
    Time findByNomeTimeIgnoreCase(String nomeTime);
}
