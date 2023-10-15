package br.com.luiscutilak.todolist2.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.luiscutilak.todolist2.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {
            // Pegar a autenticação (usuario e senha)
            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecode);
            // Separando usuario e senha para ser validado.
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            var user = this.userRepository.findByUsername(username);
            // Abaixo validação de usuario e senha
            // Se o usuario nao tem autorização ou nao valido erro 401
            if (user == null) {
                response.sendError(401);
                // Senao entra no else e valida a senha e Segue viagem.
            } else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    //Recuperar atributo Id do UserModel,controller força isso
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                    // Se senha der errada ou invalida entra no senao e error 401
                } else {
                    response.sendError(401);
                }

            }

        } else {
            filterChain.doFilter(request, response);
        }

    }

}
