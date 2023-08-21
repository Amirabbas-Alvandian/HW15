package example.base.repository.impl;


import example.base.repository.BaseRepository;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Getter
public class BaseRepositoryImpl<T> implements BaseRepository<T> {
    private final EntityManager entityManager;
    Class<T> classname;

    public BaseRepositoryImpl(EntityManager entityManager, Class<T> classname) {
        this.entityManager = entityManager;
        this.classname = classname;
    }

    @Override
    public T save(T t) {
        entityManager.persist(t);
        return t;
    }

/*    @Override
    public T update(T t) {
        return entityManager.merge(t);
    }*/

    @Override
    public void delete(long id) {
        entityManager.remove(find(id).orElseThrow(NoResultException::new));
    }


    @Override
    public boolean contains(T t) {
        return entityManager.contains(t);
    }

    @Override
    public Optional<T> find(long id) {
        return Optional.ofNullable(entityManager.find(classname, id));
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + classname.getName(), classname).getResultList();
    }

    @Override
    public T jpaFind(long id) {
        return entityManager.find(classname,id);
    }
}
