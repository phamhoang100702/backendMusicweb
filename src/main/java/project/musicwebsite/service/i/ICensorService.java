package project.musicwebsite.service.i;

import project.musicwebsite.entity.Censor;

import java.util.List;
import java.util.Optional;

public interface ICensorService {
    Censor save(Censor censor);
    Optional<Censor> findById(Long id);
    List<Censor> getAll();

    List<Censor> searchAllByName(String name);
    Censor update(Censor censor);

    void delete(Long id);
    Long getTotalCensor();
}
