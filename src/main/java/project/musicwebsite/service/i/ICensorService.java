package project.musicwebsite.service.i;

import project.musicwebsite.entity.Censor;

import java.util.List;
import java.util.Optional;

public interface ICensorService {
    Censor save(Censor censor);
    Optional<Censor> findById(Long id);
    List<Censor> getList();
    Censor update(Long id,Censor censor);

    Censor delete(Long id);
}
