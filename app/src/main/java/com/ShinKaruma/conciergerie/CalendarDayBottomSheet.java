package com.ShinKaruma.conciergerie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ShinKaruma.conciergerie.cardAdapters.DayEventAdapter;
import com.ShinKaruma.conciergerie.pojo.CalendarEvent;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarDayBottomSheet
        extends BottomSheetDialogFragment {

    private static final String ARG_DATE = "date";
    private static final String ARG_EVENTS = "events";

    public static CalendarDayBottomSheet newInstance(
            LocalDate date,
            List<CalendarEvent> events
    ) {
        CalendarDayBottomSheet sheet =
                new CalendarDayBottomSheet();

        Bundle args = new Bundle();
        args.putString(ARG_DATE, date.toString());
        args.putSerializable(ARG_EVENTS,
                new ArrayList<>(events));

        sheet.setArguments(args);
        return sheet;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.bottom_sheet_day,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        RecyclerView recycler =
                view.findViewById(R.id.recyclerDayEvents);

        recycler.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        List<CalendarEvent> events =
                (List<CalendarEvent>)
                        getArguments().getSerializable(ARG_EVENTS);

        recycler.setAdapter(
                new DayEventAdapter(events)
        );
    }
}