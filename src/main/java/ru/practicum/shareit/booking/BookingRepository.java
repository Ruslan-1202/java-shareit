package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.enumeration.BookingStatus;

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

    List<Booking> findAllByItem_Owner_IdOrderByStartDesc(long owner);

    List<Booking> findAllByItem_Owner_IdAndStatusOrderByStartDesc(long bookerId, BookingStatus status);
}
