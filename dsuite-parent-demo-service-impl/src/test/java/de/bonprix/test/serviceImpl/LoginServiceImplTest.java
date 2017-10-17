/**
 *
 */
package de.bonprix.test.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.base.demo.dto.Login;
import de.bonprix.base.demo.dto.builder.LoginBuilder;
import de.bonprix.base.demo.jparepository.LoginRepository;
import de.bonprix.base.demo.model.LoginEntity;
import de.bonprix.base.demo.model.builder.LoginEntityBuilder;
import de.bonprix.base.demo.service.LoginServiceImpl;
import de.bonprix.model.Paged;

/**
 * @author h.kalokhe
 *
 */
public class LoginServiceImplTest {
    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private LoginServiceImpl loginServiceImpl;

    private LoginEntity loginEntity;

    private Login login;

    private Login login1;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.loginEntity = new LoginEntityBuilder().withId(1L)
            .build();
        this.login = new LoginBuilder().withUsername("Hemlata")
            .build();

        this.login1 = new LoginBuilder().withUsername("Hemlata")
            .build();
    }

    @Test
    public void findAll() {
        final List<LoginEntity> loginEntities = Arrays.asList(this.loginEntity);
        Mockito.when(this.loginRepository.findAll())
            .thenReturn(loginEntities);
        final List<Login> logins = this.loginServiceImpl.findAll(new Paged(0, 100));
        Mockito.verify(this.loginRepository)
            .findAll();
        Assert.assertEquals(loginEntities.size(), logins.size());
    }

    @Test
    public void findOne() {
        Mockito.when(this.loginRepository.findOne(Mockito.anyLong()))
            .thenReturn(this.loginEntity);
        final Login logins = this.loginServiceImpl.findById(1L);
        Mockito.verify(this.loginRepository)
            .findOne(Mockito.anyLong());
        Assert.assertEquals(logins.getId(), this.loginEntity.getId());
    }

    @Test
    public void save() {
        Mockito.when(this.loginRepository.saveAndFlush(Mockito.any(LoginEntity.class)))
            .thenReturn(this.loginEntity);
        final Login logins = this.loginServiceImpl.saveLogin(this.login);
        Mockito.verify(this.loginRepository)
            .saveAndFlush(Mockito.any(LoginEntity.class));
        Assert.assertEquals(logins.getId(), this.loginEntity.getId());
    }

    @Test(
        expectedExceptions = NullPointerException.class)
    public void saveLoginWithIdTest() {
        this.loginServiceImpl.saveLogin(this.login1);
    }

    @Test
    public void delete() {
        Mockito.doNothing()
            .when(this.loginRepository)
            .delete(Mockito.anyLong());
        this.loginRepository.delete(1L);
        Mockito.verify(this.loginRepository)
            .delete(Mockito.anyLong());
    }

    @Test
    public void validate() {
        Mockito.when(this.loginRepository.findAll())
            .thenReturn(Arrays.asList(this.loginEntity));
        final boolean validate = this.loginServiceImpl.validateUser("ABC", "abc");
        Mockito.verify(this.loginRepository)
            .findAll();
        Assert.assertEquals(true, validate);
    }

    @Test
    public void updateLoginData() {
        Mockito.when(this.loginRepository.findOne(Mockito.anyLong()))
            .thenReturn(this.loginEntity);
        this.loginEntity.setFirstname("Ayesha");
        Mockito.when(this.loginRepository.saveAndFlush(Mockito.any(LoginEntity.class)))
            .thenReturn(this.loginEntity);
        final Login login = this.loginServiceImpl.findById(1L);
        final Login login2 = this.loginServiceImpl.saveLogin(login);
        Mockito.verify(this.loginRepository)
            .findOne(1L);
        Mockito.verify(this.loginRepository)
            .saveAndFlush(Mockito.any(LoginEntity.class));
        Assert.assertEquals(this.loginEntity.getId(), login2.getId());
    }

}
