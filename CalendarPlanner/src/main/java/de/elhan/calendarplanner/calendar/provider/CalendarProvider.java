package de.elhan.calendarplanner.calendar.provider;

import de.elhan.calendarplanner.calendar.BusySlotDto;
import java.util.List;

public interface CalendarProvider {
    List<BusySlotDto> getBusySlots();
}