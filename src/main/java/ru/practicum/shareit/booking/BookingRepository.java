package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.enumeration.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(
            nativeQuery = true,
            value = """
                   update bookings 
                      set status = :status 
                   where id = :bookingId
                   returning id
                   """
    )
    long setStatus(@Param("bookingId") long bookingId,
                   @Param("status") int status);


    List<Booking> findAllByBooker_IdOrderByStartDesc(long bookerId);

    List<Booking> findAllByBooker_IdAndStatusOrderByStartDesc(long bookerId, BookingStatus status);

    @Query(
            nativeQuery = true,
            value = """
                    select *
                      from bookings 
                     where booker_id = :bookerId
                       and start_date <= :dateTime
                       and end_date  >= :dateTime
                    order by start_date desc
                    """
    )
    List<Booking> findByBookerNow(long bookerId, LocalDateTime dateTime);

    @Query(
            nativeQuery = true,
            value = """
                    select *
                      from bookings 
                     where booker_id = :bookerId
                       and start_date > :dateTime
                    order by start_date desc
                    """
    )
    List<Booking> findByBookerFuture(long bookerId, LocalDateTime dateTime);

    @Query(
            nativeQuery = true,
            value = """
                    select *
                      from bookings 
                     where booker_id = :bookerId
                       and end_date <= :dateTime
                    order by start_date desc
                    """
    )
    List<Booking> findByBookerPast(long bookerId, LocalDateTime dateTime);

    List<Booking> findAllByItem_Owner_IdOrderByStartDesc(long bookerId);

    List<Booking> findAllByItem_Owner_IdAndStatusOrderByStartDesc(long bookerId, BookingStatus status);

    @Query(
            nativeQuery = true,
            value = """
                    select b.*
                      from bookings b
                      join items i on i.id = b.item_id
                     where i.owner_id = :ownerId
                       and b.start_date <= :dateTime
                       and b.end_date  >= :dateTime
                    order by start_date desc
                    """
    )
    List<Booking> findByOwnerNow(long ownerId, LocalDateTime dateTime);

    @Query(
            nativeQuery = true,
            value = """
                    select b.*
                      from bookings b
                      join items i on i.id = b.item_id
                     where i.owner_id = :ownerId
                       and b.start_date  >= :dateTime
                    order by start_date desc
                    """
    )
    List<Booking> findByOwnerFuture(long ownerId, LocalDateTime dateTime);

    @Query(
            nativeQuery = true,
            value = """
                    select b.*
                      from bookings b
                      join items i on i.id = b.item_id
                     where i.owner_id = :ownerId
                       and b.end_date <= :dateTime
                    order by start_date desc
                    """
    )
    List<Booking> findByOwnerPast(long ownerId, LocalDateTime dateTime);

    @Query(
            nativeQuery = true,
            value = """
                    select b.*
                      from bookings b
                    join items i on i.id = b.item_id
                    where b.booker_id = :userId
                      and i.id = :itemId
                      and b.status = 1
                      and end_date < now()
                    """
    )
    Booking getApproved(long userId, long itemId);

    @Query(
            nativeQuery = true,
            value = """
                    select b.*
                      from bookings b
                     where b.item_id in :items
               --        and end_date = (select max(end_date) 
               --                          from bookings b1 
               --                         where b1.item_id = b.item_id
                 --                         and b1.end_date <= now())
                    order by start_date
                    """
    )
    List<Booking> getBokings(List<Long> items);
}
