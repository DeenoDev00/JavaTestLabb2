package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingSystemTest {

    @Mock
    private TimeProvider timeProvider;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BookingSystem bookingSystem;

    private LocalDateTime now;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String roomId;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        startTime = now.plusHours(1);
        endTime = now.plusHours(2);
        roomId = "room1";
    }

    @Test
    void bookRoom_shouldReturnTrue_whenRoomIsAvailable() throws NotificationException {
        Room mockRoom = mock(Room.class);
        when(timeProvider.getCurrentTime()).thenReturn(now);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(mockRoom));
        when(mockRoom.isAvailable(startTime, endTime)).thenReturn(true);

        boolean result = bookingSystem.bookRoom(roomId, startTime, endTime);

        assertThat(result).isTrue();
        verify(roomRepository).save(mockRoom);
        verify(notificationService).sendBookingConfirmation(any(Booking.class));
    }

    @Test
    void bookRoom_shouldReturnFalse_whenRoomIsNotAvailable() throws NotificationException {
        // Arrange
        Room mockRoom = mock(Room.class);
        when(timeProvider.getCurrentTime()).thenReturn(now);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(mockRoom));
        when(mockRoom.isAvailable(startTime, endTime)).thenReturn(false);

        boolean result = bookingSystem.bookRoom(roomId, startTime, endTime);

        assertThat(result).isFalse();
        verify(roomRepository, never()).save(mockRoom);
        verify(notificationService, never()).sendBookingConfirmation(any(Booking.class));
    }

    @Test
    void bookRoom_shouldThrowException_whenParametersAreInvalid() {
        LocalDateTime validStartTime = LocalDateTime.now();
        LocalDateTime validEndTime = validStartTime.plusHours(1);

        when(timeProvider.getCurrentTime()).thenReturn(validStartTime);
        assertThatThrownBy(() -> bookingSystem.bookRoom(null, validStartTime, validEndTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");

        assertThatThrownBy(() -> bookingSystem.bookRoom(roomId, validStartTime.minusHours(2), validEndTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Kan inte boka tid i dåtid");


        assertThatThrownBy(() -> bookingSystem.bookRoom(roomId, validEndTime, validStartTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Sluttid måste vara efter starttid");
    }

    @Test
    void getAvailableRooms_shouldReturnAvailableRooms() {
        Room mockRoom = mock(Room.class);
        when(roomRepository.findAll()).thenReturn(Collections.singletonList(mockRoom));
        when(mockRoom.isAvailable(startTime, endTime)).thenReturn(true);

        List<Room> availableRooms = bookingSystem.getAvailableRooms(startTime, endTime);

        assertThat(availableRooms).containsExactly(mockRoom);
    }

    @Test
    void getAvailableRooms_shouldThrowException_whenParametersAreInvalid() {
        assertThatThrownBy(() -> bookingSystem.getAvailableRooms(null, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Måste ange både start- och sluttid");

        assertThatThrownBy(() -> bookingSystem.getAvailableRooms(startTime, startTime.minusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Sluttid måste vara efter starttid");
    }


}