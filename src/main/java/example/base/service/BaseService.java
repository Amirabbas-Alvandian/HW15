package example.base.service;

import example.entity.Student;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    T save(T t);

    T update(T t);

    void delete(long id);

    boolean contains(T t);

    Optional<T> find(long id);

    List<T> findAll();

    void saveWithoutTransaction(T t);

    boolean validate(T t);

}
