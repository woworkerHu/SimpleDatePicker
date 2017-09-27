package com.example.k4170.simpledatepicker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> months = new ArrayList<>();
    ArrayList<String> years = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    private Button cancel;
    private Button confirm;
    private ListView yearList;
    private ListView monthList;
    private ListView dayList;
    private int year = 2017;
    private int month = 1;
    private int day = 1;
    private ArrayAdapter<String> monthAdapter;
    private ArrayAdapter<String> yearAdapter;
    private ArrayAdapter<String> dayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        setOnclick();
        initData();
    }

    private void setOnclick() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, makeDateString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String makeDateString() {
        String month = null;
        String day = null;
        if (this.month < 10) {
            month = "0" + this.month;
        } else {
            month = this.month + "";
        }
        if (this.day < 10) {
            day = "0" + this.day;
        } else {
            day = this.day + "";
        }
        return "" + year + month + day;
    }

    private void initData() {
        for (int i = 0; i < 12; i++) {
            months.add(i + 1 + " 月");
        }
        for (int i = 0; i < 50; i++) {
            years.add(2000 + i + " 年");
        }
        for (int i = 0; i < 31; i++) {
            days.add(i + 1 + " 日");
        }
    }

    private void initUI() {
        cancel = (Button) findViewById(R.id.btn_date_picker_cancel);
        confirm = (Button) findViewById(R.id.btn_date_picker_confirm);

        yearList = (ListView) findViewById(R.id.listview_year_item);
        monthList = (ListView) findViewById(R.id.listview_month_item);
        dayList = (ListView) findViewById(R.id.listview_day_item);

        monthAdapter = new ArrayAdapter<>(this, R.layout.datepicker_listview_item, months);
        yearAdapter = new ArrayAdapter<>(this, R.layout.datepicker_listview_item, years);
        dayAdapter = new ArrayAdapter<>(this, R.layout.datepicker_listview_item, days);

        monthList.setAdapter(monthAdapter);
        yearList.setAdapter(yearAdapter);
        dayList.setAdapter(dayAdapter);
        yearList.setDivider(null);
        monthList.setDivider(null);
        dayList.setDivider(null);
        setScrollListener();

        setInitYear();
    }

    private void addVoidItem() {
        //添加空项，使得最末尾的项可选到
        int lastVisiblePosition = yearList.getLastVisiblePosition();
        for (int i = 0; i < lastVisiblePosition; i++) {
            months.add("");
            years.add("");
            days.add("");
        }
        monthAdapter.notifyDataSetChanged();
        yearAdapter.notifyDataSetChanged();
        dayAdapter.notifyDataSetChanged();
    }

    private void setInitYear() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        yearList.setSelection(16);
                        addVoidItem();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 300);
    }

    private void setScrollListener() {
        yearList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    int firstVisiblePosition = yearList.getFirstVisiblePosition();
                    yearList.smoothScrollToPosition(firstVisiblePosition);
                    year = firstVisiblePosition + 2000;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        monthList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    int firstVisiblePosition = monthList.getFirstVisiblePosition();
                    monthList.smoothScrollToPosition(firstVisiblePosition);
                    month = firstVisiblePosition + 1;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        dayList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    int firstVisiblePosition = dayList.getFirstVisiblePosition();
                    dayList.smoothScrollToPosition(firstVisiblePosition);
                    day = firstVisiblePosition + 1;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

}
