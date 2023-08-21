package example.base.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {

    T save(T t);

/*    T update(T t);*/

    void delete(long id);

    boolean contains(T t);

    Optional<T> find(long id);

    List<T> findAll();

    T jpaFind (long id);
}
