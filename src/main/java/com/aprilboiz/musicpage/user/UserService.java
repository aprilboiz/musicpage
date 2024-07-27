package com.aprilboiz.musicpage.user;

import com.aprilboiz.musicpage.auth.dto.RegisterRequest;
import com.aprilboiz.musicpage.exception.DuplicateException;
import com.aprilboiz.musicpage.role.Role;
import com.aprilboiz.musicpage.role.RoleService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getUsername())
                .password(user.get().getPassword())
                .roles(user.get().getRoles().stream().map(Role::getName).toArray(String[]::new))
                .build();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void register(RegisterRequest request){
        String userName = request.username();
        Optional<User> existingUser = userRepository.findByUsername(userName);
        if (existingUser.isPresent()){
            throw new DuplicateException(String.format("This username '%s' already exists", userName));
        }
        String hashedPassword = passwordEncoder.encode(request.password());
        Set<Role> userRoles = new HashSet<>();
        for (String roleName : request.roles()){
            userRoles.add(roleService.findByName(roleName));
        }
        User user = new User(userName, hashedPassword, userRoles);
        userRepository.save(user);
    }

    public User getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(currentUsername);
    }
}
