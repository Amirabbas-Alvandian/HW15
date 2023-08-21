package example.base.service.impl;


import example.base.repository.BaseRepository;
import example.base.repository.impl.BaseRepositoryImpl;
import example.base.service.BaseService;
import example.validation.EntityValidation;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
public class BaseServiceImpl<T> implements BaseService<T> {
    protected BaseRepository<T> baseRepository;
    protected EntityManager entityManager;
    protected Validator validator;

    public BaseServiceImpl(BaseRepositoryImpl<T> baseRepository) {
        this.baseRepository = baseRepository;
        this.entityManager = baseRepository.getEntityManager();
        this.validator = EntityValidation.validator;
    }

    @Override
    public T save(T t) {
        if(!validate(t)){
            return null;
        }
        entityManager.getTransaction().begin();
        try {
            baseRepository.save(t);
            entityManager.getTransaction().commit();

        } catch (PersistenceException p){
            System.out.println(p.getMessage());
            System.out.println("persistence");
            entityManager.getTransaction().rollback();
        }
        return t;
    }

    @Override
    public T update(T t) {
        if(!validate(t)){
            return null;
        }
        entityManager.getTransaction().begin();
        try {
            T result = baseRepository.update(t);
            entityManager.getTransaction().commit();
            return result;
        } catch (PersistenceException p){
            System.out.println(p.getMessage());
            entityManager.getTransaction().rollback();
        }

        return null;
    }

    @Override
    public void delete(long id) {
        entityManager.getTransaction().begin();
        try {
            baseRepository.delete(id);
            entityManager.getTransaction().commit();
        }catch (PersistenceException p){
            System.out.println(p.getMessage());
            entityManager.getTransaction().commit();
        }

    }

    @Override
    public boolean contains(T t) {
        return baseRepository.contains(t);
    }

    @Override
    public Optional<T> find(long id) {
        try {
             return baseRepository.find(id);
        }catch (NoResultException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public void saveWithoutTransaction(T t) {
        baseRepository.save(t);
    }

    @Override
    public boolean validate(T t) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(t) ;
        if(!violationSet.isEmpty()){
            for (ConstraintViolation<T> c : violationSet){
                System.out.println(c.getMessage());
            }
            return false;
        }
        return true;
    }


}
