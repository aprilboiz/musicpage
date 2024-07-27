package com.aprilboiz.musicpage.role;

import com.aprilboiz.musicpage.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Role findByName(String roleName){
        return roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new NotFoundException(String.format("Role '%s' is not found.", roleName)));
    }

    @Transactional(readOnly = true)
    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    @Transactional
    public void save(Role role){
        roleRepository.save(role);
    }

}
