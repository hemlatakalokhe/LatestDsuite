package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import de.bonprix.annotation.RestService;
import de.bonprix.base.demo.dto.Login;
import de.bonprix.base.demo.dto.builder.LoginBuilder;
import de.bonprix.base.demo.jparepository.LoginRepository;
import de.bonprix.base.demo.model.LoginEntity;
import de.bonprix.base.demo.model.builder.LoginEntityBuilder;
import de.bonprix.model.ComplexFilter;
import de.bonprix.model.Paged;

@RestService
@Transactional
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginRepository loginRepo;

    @Override
    public boolean validateUser(final String username, final String password) {
        final List<Login> logins = new ArrayList<>();
        for (final Login login : findAll(new Paged(0, 100))) {
            if (username.equals(login.getUsername()) && password.equals(login.getPassword())) {
                logins.add(login);
            }
        }

        return logins.isEmpty();
    }

    @Override
    public List<Login> findAll(final Paged pageable) {
        final List<Login> logins = new ArrayList<>();
        for (final LoginEntity login : this.loginRepo.findAll()) {
            logins.add(convertToDto(login));
        }
        return logins;
    }

    @Override
    public List<Login> findByFilter(final ComplexFilter complexFilter) {
        return new ArrayList<>();
    }

    @Override
    public Login findById(final Long id) {
        return convertToDto(this.loginRepo.findOne(id));
    }

    @Override
    public void deleteById(final Long id) {
        this.loginRepo.delete(id);
    }

    @Override
    public Login saveLogin(final Login login) {
        return convertToDto(this.loginRepo.saveAndFlush(convertToModel(login)));
    }

    public LoginEntity convertToModel(final Login login) {
        return new LoginEntityBuilder().withUsername(login.getUsername())
            .withPassword(login.getPassword())
            .withFirstname(login.getFirstname())
            .withLastname(login.getLastname())
            .build();
    }

    public Login convertToDto(final LoginEntity loginEntity) {
        return new LoginBuilder().withId(loginEntity.getId())
            .withUsername(loginEntity.getUsername())
            .withPassword(loginEntity.getPassword())
            .withFirstname(loginEntity.getFirstname())
            .withLastname(loginEntity.getLastname())
            .build();
    }

}
