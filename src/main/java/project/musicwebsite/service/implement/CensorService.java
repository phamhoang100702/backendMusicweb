package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Censor;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.CensorRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.ICensorService;

import java.util.List;
import java.util.Optional;

@Service
public class CensorService implements ICensorService {
    @Autowired
    CensorRepository censorRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Censor save(Censor censor) {
        Optional<User> user = userRepository.findByEmail(censor.getEmail());
        if (user.isPresent()) throw new BadRequestException("This email is existed");
        return censorRepository.save(censor);
    }

    @Override
    public Optional<Censor> findById(Long id) {
        Optional<Censor> censor = censorRepository.findById(id);
        if (censor.isEmpty()) throw new NotFoundException("This censor is not exist");
        return censor;
    }

    @Override
    public List<Censor> getAll() {
        List<Censor> list = censorRepository.findAll();
        return list;
    }

    @Override
    public Censor update(Censor censor) {
        return censorRepository.findById(censor.getId())
                .map(censor1 -> {
                    censor1.setAddress(censor.getAddress());
                    censor1.setBirth(censor.getBirth());
                    censor1.setName(censor.getName());
                    censor1.setPhone(censor.getPhone());
                    censor1.setStatus(censor.getStatus());
                    return censorRepository.save(censor1);
                }).orElseThrow(() -> new NotFoundException("This censor is not existed"));
    }

    @Override
    public void delete(Long id) {
        if (censorRepository.findById(id).isEmpty()) {
            throw new NotFoundException("This censor is not existed");
        }
        censorRepository.deleteById(id);
    }

    @Override
    public Long count() {
        return censorRepository.count();
    }
}
