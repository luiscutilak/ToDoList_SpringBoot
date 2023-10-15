package br.com.luiscutilak.todolist2.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;



//representação dos métodos
public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
}
