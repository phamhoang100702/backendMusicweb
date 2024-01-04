package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Role;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.RoleRepository;
import project.musicwebsite.service.i.IRoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }


    public List<Role> save(List<Role> role) {
        return roleRepository.saveAll(role);
    }

    @Override
    public Role searchByName(String name) {
        return roleRepository.findByName(name).orElseThrow(
                ()-> new NotFoundException("Role is not existed")
        );
    }

    @Override
    public void deleteRole(Long id) {
        if(!roleRepository.existsById(id)) throw  new NotFoundException("Role is not existed");
        roleRepository.deleteById(id);
    }

    @Override
    public Role updateRole( Role role) {
        return null;
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(
                        ()-> new NotFoundException("This role is not existed")
                );
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
