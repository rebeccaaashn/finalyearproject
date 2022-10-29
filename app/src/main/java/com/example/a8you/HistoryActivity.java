package com.example.a8you;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    CalendarView simpleCalendarView;
    ListView listview;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> mKeys = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    int dayCount =0 ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

//        try {try
//            dayCount = fitsensor.getInstance().getDayCount();
//        } catch (Exception e) {
////            Toast.makeText(Trophy.this, e + "" , Toast.LENGTH_SHORT).show();
//            System.out.println("Error" + e);
//        }

        databaseReference=FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Exercise");

        listview = (ListView)findViewById(R.id.listview);

        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this
        , android.R.layout.simple_list_item_1, arrayList);
        listview.setAdapter(arrayAdapter);
        for (int i =0 ;i <= 3; i++) {
            String iStr = Integer.toString(i);

            databaseReference.child("Day"+i).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String value = snapshot.getValue(String.class).toString();
                    arrayList.add(value);
//                    String key = snapshot.getKey();
//                    mKeys.add(key);
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

//                    String value = snapshot.getValue(User2.class).toString();
//                    String key = snapshot.getKey();
//                    int index = mKeys.indexOf(key);
//                    arrayList.set(index, value);
//                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.dashboard:
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
//
//        ArrayList<String> list = new ArrayList<>();
//        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list_item, list);
//        listview.setAdapter(adapter);
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                    list.add(snapshot1.getValue().toString());
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
//
   }
}