package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(nativeQuery = true,
            value = """
                        select i.id,
                               i.name,
                               i.description,
                               i.is_available,
                               i.owner_id,
                               i.request_id
                          from items i
                         where i.owner_id = :id
                    """)
    List<Item> findByUserId(@Param(value = "id") long id);

    @Query("""
            select i from Item i
            where i.available = true
              and (
                 upper(i.name) like upper(concat('%', :text, '%'))
                 or upper(i.description) like upper(concat('%', :text, '%'))
                  )
            """)
    List<Item> search(@Param(value = "text") String text);

    @Query(
            nativeQuery = true,
            value = """
                    select i.id,
                           i.name,
                           i.description,
                           i.is_available,
                           i.owner_id,
                           i.request_id
                      from items i
                     where i.request_id = :id
                    """
    )
    List<Item> getByRequest(Long id);
}
