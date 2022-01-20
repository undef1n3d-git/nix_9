package com.alevel.service.impl;

import com.alevel.exception.EntityExistException;
import com.alevel.persistence.crud.CrudRepositoryHelper;
import com.alevel.persistence.datatable.DataTableRequest;
import com.alevel.persistence.datatable.DataTableResponse;
import com.alevel.persistence.entity.user.Personal;
import com.alevel.persistence.repository.user.PersonalRepository;
import com.alevel.service.PersonalCrudService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonalCrudServiceImpl implements PersonalCrudService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PersonalRepository personalRepository;
    private final CrudRepositoryHelper<Personal, PersonalRepository> crudRepositoryHelper;

    public PersonalCrudServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            PersonalRepository personalRepository, CrudRepositoryHelper<Personal, PersonalRepository> crudRepositoryHelper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.personalRepository = personalRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void create(Personal personal) {
        if (personalRepository.existsByEmail(personal.getEmail())) {
            throw new EntityExistException("this personal is exist");
        }
        personal.setPassword(bCryptPasswordEncoder.encode(personal.getPassword()));
        crudRepositoryHelper.create(personalRepository, personal);
    }

    @Override
    public void update(Personal entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Personal> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DataTableResponse<Personal> findAll(DataTableRequest request) {
        return null;
    }
}
