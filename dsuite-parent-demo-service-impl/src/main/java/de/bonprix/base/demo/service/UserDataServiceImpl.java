package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import de.bonprix.annotation.RestService;
import de.bonprix.base.demo.dto.UserData;
import de.bonprix.base.demo.dto.builder.UserDataBuilder;
import de.bonprix.base.demo.jparepository.UserDataRepository;
import de.bonprix.base.demo.model.UserDataEntity;
import de.bonprix.model.Paged;

@RestService
@Transactional
public class UserDataServiceImpl implements UserDataService {

    @Resource
    UserDataRepository userDataRepository;

    @Override
    public List<UserData> findAll(final Paged pageable) {
        final List<UserData> styles = new ArrayList<>();
        for (final UserDataEntity style : this.userDataRepository.findAll(new PageRequest(pageable.getPage(), pageable.getPageSize()))) {
            styles.add(convertToDto(style));
        }
        return styles;

    }

    private UserData convertToDto(final UserDataEntity userData) {

        return new UserDataBuilder().withId(userData.getId())
            .withName(userData.getName())
            .withPassword(userData.getPassword())
            .withCpassword(userData.getCpassword())
            .withEmail(userData.getEmail())
            .withMobileNo(userData.getMobileNo())
            .build();

    }

    @Override
    public List<UserData> findByFilter(final UserData complexFilter) {
        return new ArrayList<>();
    }

    @Override
    public UserData findById(final Long id) {
        return new UserData();
    }

    @Override
    public void deleteById(final Long id) {
        //
    }

    @Override
    public UserData saveUserDatae(final UserData userData) {
        return new UserData();
    }

}
