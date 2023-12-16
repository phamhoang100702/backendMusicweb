package project.musicwebsite.service.i;

import project.musicwebsite.entity.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    public Role save(Role role);
    public Role searchByName(String name);
    public void deleteRole(Long id);
    public Role updateRole(Long id,Role role);

    public Role findById(Long id);
    public List<Role> findAll();
}
