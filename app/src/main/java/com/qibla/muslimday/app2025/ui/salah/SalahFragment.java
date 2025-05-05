package com.qibla.muslimday.app2025.ui.salah;

import static com.qibla.muslimday.app2025.util.Global.intDay;
import static com.qibla.muslimday.app2025.util.Global.isCheckTime;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.qibla.muslimday.app2025.MainActivity;
import com.qibla.muslimday.app2025.R;
import com.qibla.muslimday.app2025.base.BaseFragment;
import com.qibla.muslimday.app2025.databinding.FragmentSalahBinding;
import com.qibla.muslimday.app2025.helper.PreferenceHelper;
import com.qibla.muslimday.app2025.receiver.PrayBroadcastReceiver;
import com.qibla.muslimday.app2025.ui.salah.calender.CalendarDayBinder;
import com.qibla.muslimday.app2025.ui.salah.calender.CalendarMonthBinder;
import com.qibla.muslimday.app2025.ui.salah.calender.model.AladhanDate;
import com.qibla.muslimday.app2025.ui.salah.calender.model.AladhanDateType;
import com.qibla.muslimday.app2025.ui.salah.calender.service.CalendarService;
import com.qibla.muslimday.app2025.ui.salah.calender.utils.UiUtils;
import com.qibla.muslimday.app2025.util.Const;
import com.qibla.muslimday.app2025.util.Global;
import com.qibla.muslimday.app2025.view.DialogConnectionWifi;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;

@AndroidEntryPoint
public class SalahFragment extends BaseFragment<FragmentSalahBinding> {
    private static final int MIN_MONTH_COUNT = 6;
    private static final int MAX_MONTH_COUNT = 12;
    private static final float OFF_SET = 100f;
    public static Boolean isCheck = false;
    private static LocalDate selectedDate;
    private static List<AladhanDate> hijriDates;
    @Inject
    CalendarService calendarService;
    @Inject
    PreferenceHelper preferenceHelper;
    ActivityResultLauncher<Intent> startActivityNativeAll = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadNativeAll();
//                        }
//                    }).start();
                }
            }
    );
    private float coordinateY = 0f;
    private SalahViewModel viewModel;
    private CalendarView calendarView;
    private CompositeDisposable compositeDisposable;
    private TextView calendarToolbarTitle;
    private TextView calendarToolbarSubTitle;
    private int count = 0;
    private Boolean isNotifyPrayFajr;
    private Boolean isNotifyPraySunrise;
    private Boolean isNotifyPrayDhuhr;
    private Boolean isNotifyPrayAsr;
    private Boolean isNotifyPrayMaghrib;
    private Boolean isNotifyPrayIsha;
    private AlarmManager alarmManager;

    private Long lastTimeCallApi = 0L;

    public static boolean hasNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                }
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

    public static LocalDate getSelectedDate() {
        return selectedDate;
    }

    public static List<AladhanDate> getHijriDates() {
        return hijriDates;
    }

//    private void loadNativeAll() {
//        if (AdsInter.Companion.isLoadNativeBackground() && AdsInter.Companion.getNativeAdsAll() == null) {
//            Admob.getInstance().loadNativeAd(
//                    requireActivity(),
//                    getString(R.string.native_all),
//                    new NativeCallback() {
//                        @Override
//                        public void onNativeAdLoaded(NativeAd nativeAd) {
//                            AdsInter.Companion.setNativeAdsAll(nativeAd);
//                        }
//
//                        @Override
//                        public void onAdFailedToLoad() {
//                            AdsInter.Companion.setNativeAdsAll(null);
//                        }
//
//                        @Override
//                        public void onAdImpression() {
//                            super.onAdImpression();
//                            AdsInter.Companion.setNativeAdsAll(null);
//
//                        }
//                    }
//            );
//        }
//    }

    @NonNull
    @Override
    public FragmentSalahBinding getViewBinding() {
        return FragmentSalahBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SalahViewModel.class);

        Global.count++;

        if (!isCheckTime && Global.count == 1) {
            showLoading();
        }
        initView();


        initData();
        setListeners();

        setUpAlarmFajr();

        setUpAlarmSunrise();

        setUpAlarmDhuhr();

        setUpAlarmAsr();

        setUpAlarmMaghrib();

        setUpAlarmIsha();

    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        if (!hasNetworkConnection(requireActivity())) {
            showDialogWifi(true);
            binding.llSalahShowTimePrayer.setVisibility(View.GONE);
        } else {
            binding.llSalahShowTimePrayer.setVisibility(View.VISIBLE);
        }

        binding.ivSalhPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.calendarView.scrollToMonth(binding.calendarView.findLastVisibleMonth().getYearMonth().minusMonths(1));
            }
        });

        binding.ivSalhNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.calendarView.scrollToMonth(binding.calendarView.findLastVisibleMonth().getYearMonth().plusMonths(1));
            }
        });

        binding.llSalahShowTimePrayer.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP -> {
                        coordinateY = event.getRawY();
                        return true;
                    }
                    case MotionEvent.ACTION_MOVE -> {
                        float value = event.getRawY() - coordinateY;
                        if (value > OFF_SET) {
                            binding.llSalahShowMore.setVisibility(View.GONE);
                        }

                        if (value < -OFF_SET) {
                            binding.llSalahShowMore.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }
                }
                return true;
            }
        });

        calendarToolbarTitle = binding.calendarToolbarTitle;
        calendarToolbarSubTitle = binding.calendarToolbarSubTitle;

        compositeDisposable = new CompositeDisposable();
        initCalendarView();


        calendarView.setMonthScrollListener(calendarMonth -> {
            if (System.currentTimeMillis() - lastTimeCallApi >= 0) {
                compositeDisposable.add(
                        calendarService.getHijriCalendar(calendarMonth.getMonth(), calendarMonth.getYear())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableSingleObserver<List<AladhanDate>>() {
                                    @Override
                                    public void onSuccess(@NotNull List<AladhanDate> dates) {
                                        hijriDates = dates;
                                        updateToolbarSubtitle();
                                        int currentMonth = 0;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            currentMonth = LocalDate.now().plusDays(intDay).getMonth().getValue();
                                        }
                                        LocalDate firstDateOfCurrentMonth = null;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            firstDateOfCurrentMonth = calendarMonth.getYearMonth().atDay(1);
                                        }

                                        if (calendarMonth.getMonth() == currentMonth) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                selectDate(LocalDate.now().plusDays(intDay));
                                            }
                                        } else {
                                            selectDate(firstDateOfCurrentMonth);
                                        }
                                        calendarView.notifyCalendarChanged();
                                        lastTimeCallApi = System.currentTimeMillis();
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                        Log.d("aaaaa", "Cannot retrieve calendar dates", e);
                                        lastTimeCallApi = System.currentTimeMillis();
                                    }
                                }));

                updateToolbarTitle(calendarMonth);
                return Unit.INSTANCE;
            } else {
                return Unit.INSTANCE;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hasNetworkConnection(requireActivity()) && isCheck) {
            showDialogWifi(true);
            isCheck = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SalahFragment.MessageEvent event) {
        selectDate(event.date);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        compositeDisposable.dispose();
    }

    private void setListeners() {
        alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        binding.imgPrayFajr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.setIsNotifyPrayTimeFajr(!isNotifyPrayFajr);
                initData();
                setUpAlarmFajr();
            }
        });

        binding.imgPraySunrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.setIsNotifyPrayTimeSunrise(!isNotifyPraySunrise);
                initData();
                setUpAlarmSunrise();
            }
        });

        binding.calendarToolbarSubTitle.setSelected(true);

        binding.imgPrayDhuhr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.setIsNotifyPrayTimeDhuhr(!isNotifyPrayDhuhr);
                initData();
                setUpAlarmDhuhr();
            }
        });

        binding.imgPrayAsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.setIsNotifyPrayTimeAsr(!isNotifyPrayAsr);
                initData();
                setUpAlarmAsr();
            }
        });

        binding.imgPrayMaghrib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.setIsNotifyPrayTimeMaghrib(!isNotifyPrayMaghrib);
                initData();
                setUpAlarmMaghrib();
            }
        });

        binding.imgPrayIsha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.setIsNotifyPrayTimeIsha(!isNotifyPrayIsha);
                initData();
                setUpAlarmIsha();
            }
        });

    }

    private void initData() {

        isNotifyPrayFajr = preferenceHelper.getIsNotifyPrayTimeFajr();
        isNotifyPraySunrise = preferenceHelper.getIsNotifyPrayTimeSunrise();
        isNotifyPrayDhuhr = preferenceHelper.getIsNotifyPrayTimeDhuhr();
        isNotifyPrayAsr = preferenceHelper.getIsNotifyPrayTimeAsr();
        isNotifyPrayMaghrib = preferenceHelper.getIsNotifyPrayTimeMaghrib();
        isNotifyPrayIsha = preferenceHelper.getIsNotifyPrayTimeIsha();

        if (preferenceHelper.getIsNotifyPrayTimeFajr()) {
            binding.imgPrayFajr.setBackgroundResource(R.drawable.ic_sound);
        } else {
            binding.imgPrayFajr.setBackgroundResource(R.drawable.ic_silent);
        }

        if (preferenceHelper.getIsNotifyPrayTimeSunrise()) {
            binding.imgPraySunrise.setBackgroundResource(R.drawable.ic_sound);
        } else {
            binding.imgPraySunrise.setBackgroundResource(R.drawable.ic_silent);
        }

        if (preferenceHelper.getIsNotifyPrayTimeDhuhr()) {
            binding.imgPrayDhuhr.setBackgroundResource(R.drawable.ic_sound);
        } else {
            binding.imgPrayDhuhr.setBackgroundResource(R.drawable.ic_silent);
        }

        if (preferenceHelper.getIsNotifyPrayTimeAsr()) {
            binding.imgPrayAsr.setBackgroundResource(R.drawable.ic_sound);
        } else {
            binding.imgPrayAsr.setBackgroundResource(R.drawable.ic_silent);
        }

        if (preferenceHelper.getIsNotifyPrayTimeMaghrib()) {
            binding.imgPrayMaghrib.setBackgroundResource(R.drawable.ic_sound);
        } else {
            binding.imgPrayMaghrib.setBackgroundResource(R.drawable.ic_silent);
        }

        if (preferenceHelper.getIsNotifyPrayTimeIsha()) {
            binding.imgPrayIsha.setBackgroundResource(R.drawable.ic_sound);
        } else {
            binding.imgPrayIsha.setBackgroundResource(R.drawable.ic_silent);
        }


        binding.tvTimePrayFajr.setText(Objects.requireNonNull(Const.PrayTimeModel.getData()).getTimings().getFajr());
        binding.tvTimePraySunrise.setText(Const.PrayTimeModel.getData().getTimings().getSunrise());
        binding.tvTimePrayDhuhr.setText(Const.PrayTimeModel.getData().getTimings().getDhuhr());
        binding.tvTimePrayAsr.setText(Const.PrayTimeModel.getData().getTimings().getAsr());
        binding.tvTimePrayMaghrib.setText(Const.PrayTimeModel.getData().getTimings().getMaghrib());
        binding.tvTimePrayIsha.setText(Const.PrayTimeModel.getData().getTimings().getIsha());

        assert Const.PrayTimeModel != null;

    }

    private String formatTimeWithAMPM(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(date);
    }

    private void setUpAlarmFajr() {
        if (preferenceHelper.getIsNotifyPrayTimeFajr()) {
            String timePrayFajr = Const.PrayTimeModel != null ? Objects.requireNonNull(Const.PrayTimeModel.getData()).getTimings().getFajr() : "";
            int timePreNotification = preferenceHelper.getTimePreNotificationFajr();
            String timePrayFajrNew = calculateTimeBeforeMinutes(timePrayFajr, timePreNotification);
            ArrayList<String> timeParts = new ArrayList<>(Arrays.asList(timePrayFajrNew.split(":")));
            if (timeParts.size() == 2) {
                Log.d("ntt", "Hour" + timeParts.get(0));
                Log.d("ntt", "Minutes" + timeParts.get(1));
                String hourString = timeParts.get(0); // Lấy giờ
                String minuteString = timeParts.get(1); // Lấy phút

                int hour = Integer.parseInt(hourString);
                int minute = Integer.parseInt(minuteString);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                // Đặt lịch thông báo
                Intent intentNew = new Intent(requireActivity(), PrayBroadcastReceiver.class);

                intentNew.putExtra("name_pray", "Fajr");
                intentNew.putExtra("time", timePrayFajr);

                Calendar calendarNew = Calendar.getInstance();
                int currentHour = calendarNew.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendarNew.get(Calendar.MINUTE);

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    Log.d("ntt", "if Time" + calendar.getTimeInMillis());

                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_FAJR,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    Log.d("ntt", "Calendar.DAY_OF_MONTH" + Calendar.DAY_OF_MONTH);
                    Log.d("ntt", "Calendar.HOUR_OF_DAY" + Calendar.HOUR_OF_DAY);
                    Log.d("ntt", "Calendar.MINUTE" + Calendar.MINUTE);
                    Log.d("ntt", "Calendar.SECOND" + Calendar.SECOND);

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                } else {

                    Log.d("ntt", "else Time Calendar.DAY_OF_MONTH" + Calendar.DAY_OF_MONTH);
                    Log.d("ntt", "else Time Calendar.HOUR_OF_DAY" + Calendar.HOUR_OF_DAY);
                    Log.d("ntt", "else Time Calendar.MINUTE" + Calendar.MINUTE);
//                    Log.d("ntt", "else Time Calendar.SECOND" + Calendar.SECOND);
                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_FAJR,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                }
            } else {
                Log.d("ntt", "Chuỗi không hợp lệ.");
            }
        } else {
            Intent intentNew = new Intent(requireActivity(), PrayBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    requireActivity(),
                    Const.REQUEST_CODE_ALARM_FAJR,
                    intentNew,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            alarmManager.cancel(pendingIntent);
        }
    }

    private void setUpAlarmSunrise() {
        if (preferenceHelper.getIsNotifyPrayTimeSunrise()) {
            String timePraySunrise = Const.PrayTimeModel != null ? Objects.requireNonNull(Const.PrayTimeModel.getData()).getTimings().getSunrise() : "";

            int timePreNotification = preferenceHelper.getTimePreNotificationSunrise();

            String timePraySunriseNew = calculateTimeBeforeMinutes(timePraySunrise, timePreNotification);

            ArrayList<String> timeParts = new ArrayList<>(Arrays.asList(timePraySunriseNew.split(":")));
            if (timeParts.size() == 2) {
                String hourString = timeParts.get(0); // Lấy giờ
                String minuteString = timeParts.get(1); // Lấy phút

                int hour = Integer.parseInt(hourString);
                int minute = Integer.parseInt(minuteString);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                // Đặt lịch thông báo
                Intent intentNew = new Intent(requireActivity(), PrayBroadcastReceiver.class);

                intentNew.putExtra("name_pray", "Sunrise");
                intentNew.putExtra("time", timePraySunrise);

                Calendar calendarNew = Calendar.getInstance();
                int currentHour = calendarNew.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendarNew.get(Calendar.MINUTE);

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_SUNRISE,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                } else {
                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_SUNRISE,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                }
            } else {
                System.out.println("Chuỗi không hợp lệ.");
            }
        } else {
            Intent intent = new Intent(requireActivity(), PrayBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    requireActivity(),
                    Const.REQUEST_CODE_ALARM_SUNRISE,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            alarmManager.cancel(pendingIntent);
        }
    }

    private void setUpAlarmDhuhr() {
        if (preferenceHelper.getIsNotifyPrayTimeDhuhr()) {
            String timePrayDhuhr = Const.PrayTimeModel != null ? Objects.requireNonNull(Const.PrayTimeModel.getData()).getTimings().getDhuhr() : "";

            int timePreNotification = preferenceHelper.getTimePreNotificationDhuhr();

            String timePrayDhuhrNew = calculateTimeBeforeMinutes(timePrayDhuhr, timePreNotification);

            ArrayList<String> timeParts = new ArrayList<>(Arrays.asList(timePrayDhuhrNew.split(":")));
            if (timeParts.size() == 2) {
                String hourString = timeParts.get(0); // Lấy giờ
                String minuteString = timeParts.get(1); // Lấy phút

                int hour = Integer.parseInt(hourString);
                int minute = Integer.parseInt(minuteString);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                // Đặt lịch thông báo
                Intent intentNew = new Intent(requireActivity(), PrayBroadcastReceiver.class);

                intentNew.putExtra("name_pray", "Dhuhr");
                intentNew.putExtra("time", timePrayDhuhr);

                Calendar calendarNew = Calendar.getInstance();
                int currentHour = calendarNew.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendarNew.get(Calendar.MINUTE);

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_DHUHR,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                } else {
                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_DHUHR,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                }
            } else {
                System.out.println("Chuỗi không hợp lệ.");
            }
        } else {
            Intent intent = new Intent(requireActivity(), PrayBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    requireActivity(),
                    Const.REQUEST_CODE_ALARM_DHUHR,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            alarmManager.cancel(pendingIntent);
        }
    }

    private void setUpAlarmAsr() {
        if (preferenceHelper.getIsNotifyPrayTimeAsr()) {
            String timePrayAsr = Const.PrayTimeModel != null ? Objects.requireNonNull(Const.PrayTimeModel.getData()).getTimings().getAsr() : "";

            int timePreNotification = preferenceHelper.getTimePreNotificationAsr();

            String timePrayAsrNew = calculateTimeBeforeMinutes(timePrayAsr, timePreNotification);

            ArrayList<String> timeParts = new ArrayList<>(Arrays.asList(timePrayAsrNew.split(":")));
            if (timeParts.size() == 2) {
                String hourString = timeParts.get(0); // Lấy giờ
                String minuteString = timeParts.get(1); // Lấy phút

                int hour = Integer.parseInt(hourString);
                int minute = Integer.parseInt(minuteString);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                // Đặt lịch thông báo
                Intent intentNew = new Intent(requireActivity(), PrayBroadcastReceiver.class);

                intentNew.putExtra("name_pray", "Asr");
                intentNew.putExtra("time", timePrayAsr);

                Calendar calendarNew = Calendar.getInstance();
                int currentHour = calendarNew.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendarNew.get(Calendar.MINUTE);

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_ASR,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                } else {
                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_ASR,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                }
            } else {
                System.out.println("Chuỗi không hợp lệ.");
            }
        } else {
            Intent intent = new Intent(requireActivity(), PrayBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    requireActivity(),
                    Const.REQUEST_CODE_ALARM_ASR,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            alarmManager.cancel(pendingIntent);
        }
    }

    private void setUpAlarmMaghrib() {
        if (preferenceHelper.getIsNotifyPrayTimeMaghrib()) {
            String timePrayMaghrib = Const.PrayTimeModel != null ? Objects.requireNonNull(Const.PrayTimeModel.getData()).getTimings().getMaghrib() : "";

            int timePreNotification = preferenceHelper.getTimePreNotificationMaghrib();

            String timePrayMaghribNew = calculateTimeBeforeMinutes(timePrayMaghrib, timePreNotification);

            ArrayList<String> timeParts = new ArrayList<>(Arrays.asList(timePrayMaghribNew.split(":")));
            if (timeParts.size() == 2) {
                Log.d("ntt", "Hour" + timeParts.get(0));
                Log.d("ntt", "Minutes" + timeParts.get(1));
                String hourString = timeParts.get(0); // Lấy giờ
                String minuteString = timeParts.get(1); // Lấy phút

                int hour = Integer.parseInt(hourString);
                int minute = Integer.parseInt(minuteString);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                // Đặt lịch thông báo
                Intent intentNew = new Intent(requireActivity(), PrayBroadcastReceiver.class);

                intentNew.putExtra("name_pray", "Maghrib");
                intentNew.putExtra("time", timePrayMaghrib);

                Calendar calendarNew = Calendar.getInstance();
                int currentHour = calendarNew.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendarNew.get(Calendar.MINUTE);

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_MAGHRIB,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                } else {
                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_MAGHRIB,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                }
            } else {
                System.out.println("Chuỗi không hợp lệ.");
            }
        } else {
            Intent intent = new Intent(requireActivity(), PrayBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    requireActivity(),
                    Const.REQUEST_CODE_ALARM_MAGHRIB,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            alarmManager.cancel(pendingIntent);
        }
    }

    private void setUpAlarmIsha() {
        if (preferenceHelper.getIsNotifyPrayTimeIsha()) {
            String timePrayIsha = Const.PrayTimeModel != null ? Objects.requireNonNull(Const.PrayTimeModel.getData()).getTimings().getIsha() : "";

            int timePreNotification = preferenceHelper.getTimePreNotificationIsha();

            String timePrayIshaNew = calculateTimeBeforeMinutes(timePrayIsha, timePreNotification);

            ArrayList<String> timeParts = new ArrayList<>(Arrays.asList(timePrayIshaNew.split(":")));
            if (timeParts.size() == 2) {
                String hourString = timeParts.get(0); // Lấy giờ
                String minuteString = timeParts.get(1); // Lấy phút

                int hour = Integer.parseInt(hourString);
                int minute = Integer.parseInt(minuteString);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                // Đặt lịch thông báo
                Intent intentNew = new Intent(requireActivity(), PrayBroadcastReceiver.class);

                intentNew.putExtra("name_pray", "Isha");
                intentNew.putExtra("time", timePrayIsha);

                Calendar calendarNew = Calendar.getInstance();
                int currentHour = calendarNew.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendarNew.get(Calendar.MINUTE);

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_ISHA,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                } else {
                    // Đặt lịch thông báo
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            requireActivity(),
                            Const.REQUEST_CODE_ALARM_ISHA,
                            intentNew,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    alarmManager.setAlarmClock(
                            new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent),
                            pendingIntent
                    );
                }
            } else {
                System.out.println("Chuỗi không hợp lệ.");
            }
        } else {
            Intent intent = new Intent(requireActivity(), PrayBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    requireActivity(),
                    Const.REQUEST_CODE_ALARM_ISHA,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            alarmManager.cancel(pendingIntent);
        }
    }

    private String calculateTimeBeforeMinutes(String inputTime, int minutesToSubtract) {
        String[] timeParts = inputTime.split(":");
        if (timeParts.length != 2) {
            return "Invalid time format";
        }

        int currentHour = Integer.parseInt(timeParts[0]);
        int currentMinute = Integer.parseInt(timeParts[1]);

        int totalMinutes = currentHour * 60 + currentMinute - minutesToSubtract;

        int newHour = totalMinutes / 60;
        int newMinute = totalMinutes % 60;

        if (newMinute < 0) {
            newHour -= 1;
            newMinute += 60;
        }

        String formattedHour = String.format("%02d", newHour);
        String formattedMinute = String.format("%02d", newMinute);

        return formattedHour + ":" + formattedMinute;
    }

    private void initCalendarView() {
        calendarView = binding.calendarView;
        calendarView.setDayBinder(new CalendarDayBinder());
        calendarView.setMonthHeaderBinder(new CalendarMonthBinder());

        YearMonth currentMonth = null;
        YearMonth currentMonth2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            currentMonth = YearMonth.now();
            int numberMonth = LocalDate.now().plusDays(intDay).getMonth().getValue() - LocalDate.now().getMonth().getValue();
            Log.d("nnnn", "" + intDay);
            Log.d("nnnn", "1 : " + LocalDate.now().plusMonths(intDay).getMonth().getValue());
            Log.d("nnnn", "2 : " + numberMonth);
            currentMonth2 = currentMonth.plusMonths(numberMonth);

        }
        YearMonth firstMonth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            firstMonth = currentMonth2.minusMonths(MIN_MONTH_COUNT);
        }
        YearMonth lastMonth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            lastMonth = currentMonth2.plusMonths(MAX_MONTH_COUNT);
        }
        DayOfWeek firstDayOfWeek = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        }
        Log.d("dddd", "firstMonth: " + firstMonth + "  lastMonth: " + lastMonth + ":::" + firstDayOfWeek);
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek);
        calendarView.scrollToMonth(currentMonth2);
    }

    private void updateToolbarSubtitle() {
        StringBuilder subTitleBuilder = new StringBuilder();

        List<String> hijriMonthsIngregorianMonth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            hijriMonthsIngregorianMonth = hijriDates.stream()
                    .map(date -> date.getHijri().getMonth().getNumber().trim())
                    .distinct()
                    .collect(Collectors.toList());
            Log.d("aaaaaa", "1" + hijriMonthsIngregorianMonth);
        }

        List<String> hijriYearsIngregorianMonth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            hijriYearsIngregorianMonth = hijriDates.stream()
                    .map(date -> date.getHijri().getYear())
                    .distinct()
                    .collect(Collectors.toList());

            Log.d("aaaaaa", "2" + hijriYearsIngregorianMonth);
        }

        assert hijriMonthsIngregorianMonth != null;
        Iterator<String> hijriMonthIterator = hijriMonthsIngregorianMonth.iterator();
        Iterator<String> hijriYearIterator = hijriYearsIngregorianMonth.iterator();

        Log.d("aaaaaa", "3" + hijriMonthIterator);
        Log.d("aaaaaa", "4" + hijriYearIterator);

        while (hijriMonthIterator.hasNext()) {
            @SuppressLint("DiscouragedApi") String hijriMonth = getResources().getString(
                    getResources().getIdentifier("hijri_month_" + hijriMonthIterator.next(), "string", requireActivity().getPackageName()));

            subTitleBuilder.append(hijriMonth);
            if (hijriMonthIterator.hasNext()) {
                subTitleBuilder.append(" / ");
            }
        }

        subTitleBuilder.append(" ");
        while (hijriYearIterator.hasNext()) {
            subTitleBuilder.append(hijriYearIterator.next());
            if (hijriYearIterator.hasNext()) {
                subTitleBuilder.append("/");
            }
        }

        calendarToolbarSubTitle.setText(subTitleBuilder.toString());
    }

    private void selectDate(LocalDate date) {
        count++;
        if (count == 2) {
            isCheckTime = true;
            Log.d("gggg", "" + isCheckTime);
        }
        if (selectedDate != date) {
            LocalDate oldDate = selectedDate;
            selectedDate = date;
            if (oldDate != null) {
                calendarView.notifyDateChanged(oldDate);
            }
            calendarView.notifyDateChanged(date);
            AladhanDateType hijriDate = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                hijriDate = hijriDates.get(selectedDate.getDayOfMonth() - 1).getHijri();
            }
            String hijriMonth = getResources().getString(
                    getResources().getIdentifier("hijri_month_" + hijriDate.getMonth().getNumber(), "string", requireActivity().getPackageName()));


            String readableHijriDate = UiUtils.formatHijriDate(Integer.parseInt(hijriDate.getDay()), hijriMonth, Integer.parseInt(hijriDate.getYear()));

            String title = null;
            String title2 = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                title = StringUtils.capitalize(UiUtils.formatReadableGregorianDate(selectedDate, FormatStyle.FULL));
                title2 = "" + readableHijriDate;
            }

            binding.tvIsiamicCalendar.setText(title2);
            binding.tvIsiamicCalendar.setSelected(true);
            hideLoading();

            if (hasNetworkConnection(requireActivity()) && Global.isCheckTime) {
                getPrayTime(selectedDate);
            }

        }
    }

    private void getPrayTime(LocalDate selectedDate) {
        viewModel.getPrayTimeNew(
                selectedDate.toString(),
                Const.latitude,
                Const.longitude,
                preferenceHelper.getCalculationMethod(),
                preferenceHelper.getJuristicMethod()
        );
        viewModel.getPrayTimeNew().observe(getViewLifecycleOwner(), prayTimeModel -> {
            binding.tvTimePrayFajr.setText(prayTimeModel.getData().getTimings().getFajr());
            binding.tvTimePraySunrise.setText(prayTimeModel.getData().getTimings().getSunrise());
            binding.tvTimePrayDhuhr.setText(prayTimeModel.getData().getTimings().getDhuhr());
            binding.tvTimePrayAsr.setText(prayTimeModel.getData().getTimings().getAsr());
            binding.tvTimePrayMaghrib.setText(prayTimeModel.getData().getTimings().getMaghrib());
            binding.tvTimePrayIsha.setText(prayTimeModel.getData().getTimings().getIsha());

//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
//
//            try {
//                binding.tvTimePrayFajr.setText(formatTimeWithAMPM(sdf.parse(prayTimeModel.getResults().getFajr())));
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//            try {
//                binding.tvTimePrayDhuhr.setText(formatTimeWithAMPM(sdf.parse(prayTimeModel.getResults().getDhuhr())));
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//            try {
//                binding.tvTimePraySunrise.setText(formatTimeWithAMPM(sdf.parse(prayTimeModel.getResults().getDuha())));
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//            try {
//                binding.tvTimePrayAsr.setText(formatTimeWithAMPM(sdf.parse(prayTimeModel.getResults().getAsr())));
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//            try {
//                binding.tvTimePrayMaghrib.setText(formatTimeWithAMPM(sdf.parse(prayTimeModel.getResults().getMaghrib())));
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//            try {
//                binding.tvTimePrayIsha.setText(formatTimeWithAMPM(sdf.parse(prayTimeModel.getResults().getIsha())));
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }

        });

    }

    private void updateToolbarTitle(CalendarMonth calendarMonth) {
        DateTimeFormatter toolbarTitleFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            toolbarTitleFormatter = DateTimeFormatter.ofPattern(UiUtils.GREGORIAN_MONTH_YEAR_FORMAT);
        }

        String title = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            title = toolbarTitleFormatter.format(calendarMonth.getYearMonth());
        }
        calendarToolbarTitle.setText(StringUtils.capitalize(title));
    }

    private void showDialogWifi(boolean b) {
        final DialogConnectionWifi dialogWifi = new DialogConnectionWifi(requireActivity(), b);
        dialogWifi.init(requireActivity(), new DialogConnectionWifi.OnPress() {
            @Override
            public void cancel() {
                if (!hasNetworkConnection(requireActivity())) {
                    showDialogWifi(true);
                }
                dialogWifi.dismiss();
            }

            @Override
            public void save() {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                if (getContext() != null) {
                    getContext().startActivity(intent);
                    isCheck = true;
                    dialogWifi.dismiss();
                }
            }
        });

        try {
            dialogWifi.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public void showLoading() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showLoading();
        }
    }

    public void hideLoading() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideLoading();
        }
    }

    public static class MessageEvent {
        public LocalDate date;

        public MessageEvent(LocalDate date) {
            this.date = date;
        }
    }
}


