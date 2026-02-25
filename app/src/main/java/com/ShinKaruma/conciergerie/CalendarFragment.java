package com.ShinKaruma.conciergerie;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.ShinKaruma.conciergerie.cardAdapters.DayEventAdapter;
import com.ShinKaruma.conciergerie.network.APIClient;
import com.ShinKaruma.conciergerie.network.APIInterface;
import com.ShinKaruma.conciergerie.pojo.CalendarEvent;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.ViewContainer;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private RecyclerView recyclerDayEvents;
    private TextView tvDayEventsTitle;

    private final HashMap<LocalDate, List<CalendarEvent>> eventsByDate = new HashMap<>();
    private final List<CalendarEvent> selectedDayEvents = new ArrayList<>();
    private DayEventAdapter dayEventAdapter;

    private LocalDate selectedDate;

    private final DateTimeFormatter apiFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.calendarView);

        View root = view; // racine du fragment

        root.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        recyclerDayEvents = view.findViewById(R.id.recyclerDayEvents);
        tvDayEventsTitle = view.findViewById(R.id.tvDayEventsTitle);

        recyclerDayEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        dayEventAdapter = new DayEventAdapter(selectedDayEvents);
        recyclerDayEvents.setAdapter(dayEventAdapter);

        fetchEvents();
    }

    private void fetchEvents() {
        APIInterface api = APIClient.createApi(getContext());
        api.getCalendar(
                LocalDate.now().format(apiFormatter),
                LocalDate.now().plus(3, ChronoUnit.MONTHS).format(apiFormatter)
        ).enqueue(new Callback<List<CalendarEvent>>() {
            @Override
            public void onResponse(Call<List<CalendarEvent>> call, Response<List<CalendarEvent>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (CalendarEvent event : response.body()) {

                        LocalDate start = LocalDate.parse(event.start);
                        LocalDate end = LocalDate.parse(event.end);


                        LocalDate date = start;
                        while (!date.isAfter(end)) {
                            eventsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(event);
                            date = date.plusDays(1);
                        }
                    }

                    setupCalendar();
                }
            }

            @Override
            public void onFailure(Call<List<CalendarEvent>> call, Throwable t) {
                Log.e("CalendarFragment", "Erreur lors de la récupération des événements", t);
            }
        });
    }

    private void setupCalendar() {
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        YearMonth firstMonth = YearMonth.from(currentMonth.minusMonths(3));
        YearMonth lastMonth = YearMonth.from(currentMonth.plusMonths(3));

        calendarView.setup(firstMonth, lastMonth, java.time.DayOfWeek.MONDAY);

        calendarView.scrollToMonth(YearMonth.from(currentMonth));

        calendarView.setDayBinder(new MonthDayBinder<DayViewContainer>() {
            @Override
            public void bind(@NonNull DayViewContainer container, CalendarDay calendarDay) {
                container.bind(calendarDay);
            }

            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }
        });

        // Sélection par défaut sur aujourd'hui
        selectDate(LocalDate.now());
    }

    private void selectDate(LocalDate date) {
        selectedDate = date;
        selectedDayEvents.clear();
        if (eventsByDate.containsKey(date)) {
            selectedDayEvents.addAll(eventsByDate.get(date));
        }
        dayEventAdapter.notifyDataSetChanged();
        tvDayEventsTitle.setText("Événements du " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    class DayViewContainer extends ViewContainer {

        TextView textView;
        LinearLayout layoutEvents;
        CalendarDay day;

        DayViewContainer(View view) {
            super(view);
            textView = view.findViewById(R.id.tvDay);
            layoutEvents = view.findViewById(R.id.layoutEvents);

            view.setOnClickListener(v -> {
                if (day != null) {
                    selectDate(day.getDate());
                }
            });
        }

        void bind(CalendarDay day) {
            this.day = day;
            textView.setText(String.valueOf(day.getDate().getDayOfMonth()));

            layoutEvents.removeAllViews();

            List<CalendarEvent> events = eventsByDate.get(day.getDate());

            if (events != null) {
                for (CalendarEvent event : events) {

                    View bar = new View(layoutEvents.getContext());

                    LinearLayout.LayoutParams params =
                            new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    6
                            );

                    params.setMargins(0, 2, 0, 0);
                    bar.setLayoutParams(params);

                    bar.setBackgroundColor(
                            Color.parseColor(event.proprietaireColor)
                    );

                    layoutEvents.addView(bar);
                }
            }
        }
    }


}