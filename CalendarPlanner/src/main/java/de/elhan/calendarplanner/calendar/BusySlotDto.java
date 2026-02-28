package de.elhan.calendarplanner.calendar;

import java.time.LocalDateTime;

public record BusySlotDto(LocalDateTime startTime, LocalDateTime endTime) {
}