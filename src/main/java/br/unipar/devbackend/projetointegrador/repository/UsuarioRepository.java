package br.unipar.devbackend.projetointegrador.repository;
import java.util.Optional;
import br.unipar.devbackend.projetointegrador.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);


    Optional<Usuario> findByEmail(String email);

}
