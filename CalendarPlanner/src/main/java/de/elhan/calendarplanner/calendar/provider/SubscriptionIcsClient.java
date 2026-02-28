package de.elhan.calendarplanner.calendar.provider;

import de.elhan.calendarplanner.calendar.BusySlotDto;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionIcsClient implements CalendarProvider {

    @Value("${calendar.subscription.urls}")
    private String icsUrl;

    @Override
    public List<BusySlotDto> getBusySlots() {
        List<BusySlotDto> slots = new ArrayList<>();
        try {
            URL url = new URL(icsUrl);
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(url.openStream());

            for (Object component : calendar.getComponents(Component.VEVENT)) {
                VEvent event = (VEvent) component;

                // Convert iCal times to Java LocalDateTime
                LocalDateTime start = event.getStartDate().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime end = event.getEndDate().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                slots.add(new BusySlotDto(start, end));
            }
        } catch (Exception e) {
            System.err.println("Error loading calendar: " + e.getMessage());
        }
        return slots;
    }
}