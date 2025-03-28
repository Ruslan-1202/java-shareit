package ru.practicum.shareit.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByItem_Id(long id);

    @Query(
            nativeQuery = true,
            value = """
                    select  c.id,
                            c.text,
                            c.author_id,
                            c.item_id,
                            c.created
                      from comments c
                     where c.item_id in :items
                    """
    )
    List<Comment> getComments(List<Long> items);
}
