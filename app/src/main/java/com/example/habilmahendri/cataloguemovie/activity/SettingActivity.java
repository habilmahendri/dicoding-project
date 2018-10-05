package com.example.habilmahendri.cataloguemovie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.habilmahendri.cataloguemovie.AppPreference;
import com.example.habilmahendri.cataloguemovie.BuildConfig;
import com.example.habilmahendri.cataloguemovie.R;
import com.example.habilmahendri.cataloguemovie.adapter.NowPlayingAdapter;
import com.example.habilmahendri.cataloguemovie.adapter.UpComingAdapter;
import com.example.habilmahendri.cataloguemovie.api.ApiClient;
import com.example.habilmahendri.cataloguemovie.model.DataCatalog;
import com.example.habilmahendri.cataloguemovie.model.JSONResponse;
import com.example.habilmahendri.cataloguemovie.reminder.DailyAlarmReceiver;
import com.example.habilmahendri.cataloguemovie.reminder.UpComingReminderMovie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.switch_daily)
    Switch dailySwitch;
    @BindView(R.id.switch_upcoming)
    Switch upcomingSwitch;
    private Call<JSONResponse> apiCall;
    private ApiClient apiClient = new ApiClient();
    private ArrayList<DataCatalog> data;


    private DailyAlarmReceiver dailyAlarmReceiver;
    private UpComingReminderMovie upComingReminderMovie;
    UpComingReminderMovie alarmReleaseReceiver = new UpComingReminderMovie();
    private boolean isUpcoming, isDaily;
    private AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);


        dailyAlarmReceiver = new DailyAlarmReceiver();
        upComingReminderMovie = new UpComingReminderMovie();

        appPreference = new AppPreference(this);

        setEnabledisableNotif();

    }

    void setEnabledisableNotif() {
        if (appPreference.isDaily()) {
            dailySwitch.setChecked(true);
        } else {
            dailySwitch.setChecked(false);
        }
        if (appPreference.isUpcoming()) {
            upcomingSwitch.setChecked(true);
        } else {
            upcomingSwitch.setChecked(false);
        }


    }

    @OnClick({R.id.switch_daily, R.id.switch_upcoming})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_daily:
                isDaily = dailySwitch.isChecked();
                if (isDaily) {
                    dailySwitch.setEnabled(true);
                    appPreference.setDaily(isDaily);
                    dailyAlarmReceiver.setRepeatingAlarm(this);

                } else {
                    dailySwitch.setChecked(false);
                    appPreference.setDaily(isDaily);
                    dailyAlarmReceiver.cancelAlarm(this);
                }
                break;
            case R.id.switch_upcoming:
                isUpcoming = upcomingSwitch.isChecked();
                if (isUpcoming) {
                    upcomingSwitch.setEnabled(true);
                    appPreference.setUpcoming(isUpcoming);
                    release();
                } else {
                    upcomingSwitch.setChecked(false);
                    appPreference.setUpcoming(isUpcoming);
                    alarmReleaseReceiver.cancelAlarm(this);
                }
                break;
        }
    }




    public void release() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = dateFormat.format(date);


        apiCall = apiClient.getService().getNowPlaying();
        apiCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));

                //for (DataCatalog resultMovie : data) {
//                    if (resultMovie.getRelease_date().equals(now)) {
//                        data.add(resultMovie);
//                        Log.d("onSuccess", "" + data.size());
//                    }

                    for (int i = 0; i < data.size(); i++) {
                        DataCatalog p = data.get(i);
                        if (p.getRelease_date().equals(now)) {

                            alarmReleaseReceiver.setReleaseReminderAlarm(getApplicationContext(), p.getTitle());

                            Log.d("onSuccess", "" + data.size());
                        }
                        //alarmReleaseReceiver.setReleaseReminderAlarm(getApplicationContext(), p.getTitle());

                        Log.i("notif", "get Now Playing : " + p.getTitle());
                    }
               // }
                //alarmReleaseReceiver.setReleaseReminderAlarm(getApplicationContext(), data);
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }


}
