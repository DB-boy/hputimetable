package com.zhuangfei.hputimetable.appwidget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.ScrollView;
import android.widget.Toast;

import com.zhuangfei.hputimetable.AddTimetableActivity;
import com.zhuangfei.hputimetable.MainActivity;
import com.zhuangfei.hputimetable.R;
import com.zhuangfei.hputimetable.TimetableDetailActivity;
import com.zhuangfei.hputimetable.api.model.TimetableModel;
import com.zhuangfei.hputimetable.constants.ShareConstants;
import com.zhuangfei.hputimetable.model.ScheduleDao;
import com.zhuangfei.hputimetable.tools.TimetableTools;
import com.zhuangfei.hputimetable.tools.WidgetConfig;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.OnDateBuildAapter;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleSupport;
import com.zhuangfei.timetable.operater.SimpleOperater;
import com.zhuangfei.timetable.utils.ColorUtils;
import com.zhuangfei.timetable.utils.ScreenUtils;
import com.zhuangfei.toolkit.model.BundleModel;
import com.zhuangfei.toolkit.tools.ActivityTools;
import com.zhuangfei.toolkit.tools.ShareTools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;

/**
 * Created by Liu ZhuangFei on 2018/8/14.
 */

public class ScheduleService extends RemoteViewsService {
    private static final String TAG = "ScheduleService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ScheduleRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    private class ScheduleRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Intent intent;
        Context context;

        List<Schedule> data;

        public ScheduleRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }

        @Override
        public void onCreate() {
            data = new ArrayList<>();
            data.addAll(findTodayData(context));
        }

        @Override
        public void onDataSetChanged() {
            data.clear();
            data.addAll(findTodayData(context));
        }

        @Override
        public void onDestroy() {
            data.clear();
        }

        @Override
        public int getCount() {
            if(data==null||data.size()==0) return 1;
            return data.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            if(data!=null&&data.size()!=0){
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.schedule_app_widget_item);
                Schedule schedule = data.get(i);
                if (schedule == null) return views;

                views.setTextViewText(R.id.widget_tv_start, schedule.getStart() + "-" + (schedule.getStart() + schedule.getStep() - 1) + "节");
                views.setTextViewText(R.id.widget_tv_name, "" + schedule.getName());
                views.setTextViewText(R.id.widget_tv_room, "" + schedule.getRoom());
                if (data.size() > 1) {
                    views.setTextViewText(R.id.widget_tv_count, (i + 1) + "/" + data.size());
                } else {
                    views.setTextViewText(R.id.widget_tv_count, "");
                }

                Intent fillInIntent = new Intent();
                views.setOnClickFillInIntent(R.id.widget_clicklayout, fillInIntent);

//            TimetableView timetableView = new TimetableView(context, null);
//            int curWeek = TimetableTools.getCurWeek(context);
//            if (data == null) data = new ArrayList<>();
//            data.clear();
//            data.addAll(findData(context));
//
//            boolean max15 = WidgetConfig.get(context, WidgetConfig.CONFIG_MAX_ITEM);
//            int maxItem = 10;
//            if (max15) maxItem = 15;
//
//            CustomDateBuildAdapter dateAdapter = new CustomDateBuildAdapter();
//            OnSlideBuildAdapter slideAdapter = (OnSlideBuildAdapter) timetableView.onSlideBuildListener();
//            slideAdapter.setTextSize(15);
//            dateAdapter.setTextSize(15);
//
//            slideAdapter.setTextColor(Color.BLACK);
//            dateAdapter.setColor(Color.BLACK);
//
//            boolean hideWeeks = WidgetConfig.get(context, WidgetConfig.CONFIG_HIDE_WEEKS);
//            boolean hideDate = WidgetConfig.get(context, WidgetConfig.CONFIG_HIDE_DATE);
//            if(hideDate) timetableView.hideDateView();
//            if(hideWeeks) timetableView.isShowWeekends(false);
//
//            timetableView.data(data)
//                    .curWeek(curWeek)
//                    .maxSlideItem(maxItem)
//                    .isShowNotCurWeek(false)
//                    .alpha(0f, 0f, 0.6f)
//                    .marLeft(ScreenUtils.dip2px(context, 3))
//                    .marTop(ScreenUtils.dip2px(context, 3))
//                    .itemHeight(ScreenUtils.dip2px(context, 45))
//                    .callback(dateAdapter)
//                    .showView();

//            layoutView(timetableView, ScreenUtils.dip2px(context, 375f), ScreenUtils.dip2px(context, 50) + timetableView.itemHeight() * timetableView.maxSlideItem()+timetableView.marTop()*(timetableView.maxSlideItem()));
//            views.setBitmap(R.id.iv_imgview, "setImageBitmap", getViewBitmap(timetableView));
//            Schedule schedule = data.get(i);
//            views.setTextViewText(R.id.id_widget_item_name, schedule.getName());
//            views.setTextViewText(R.id.id_widget_item_room, schedule.getRoom());
//            views.setTextViewText(R.id.id_widget_item_start, schedule.getStart() + "-" + (schedule.getStart() + schedule.getStep() - 1));

                return views;
            }else{
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.schedule_app_widget_empty);
                return views;
            }
        }

        public void layoutView(View v, int width, int height) {
            // validate view.width and view.height
            v.layout(0, 0, width, height);
            int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

            // validate view.measurewidth and view.measureheight
            v.measure(measuredWidth, measuredHeight);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }

        public Bitmap getViewBitmap(ViewGroup viewGroup) {
            int h = viewGroup.getHeight();
            ;
            Bitmap bitmap;

            // 创建对应大小的bitmap
            bitmap = Bitmap.createBitmap(viewGroup.getWidth(), h,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            viewGroup.draw(canvas);
            return bitmap;
        }

        @Override
        public RemoteViews getLoadingView() {
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.schedule_app_widget_empty);
//            return views;
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return (long) i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<Schedule> findData(Context context) {
        if (context == null) return null;
        int id = ScheduleDao.getApplyScheduleId(this);
        List<TimetableModel> dataModels = ScheduleDao.getAllWithScheduleId(id);
        if (dataModels == null) return null;
        return ScheduleSupport.transform(dataModels);
    }

    public List<Schedule> findTodayData(Context context) {
        List<Schedule> allModels = findData(context);
        if (allModels == null) return new ArrayList<>();
        int curWeek = TimetableTools.getCurWeek(context);
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 2;
        if (dayOfWeek == -1) dayOfWeek = 6;
        List<Schedule> list = ScheduleSupport.getHaveSubjectsWithDay(allModels, curWeek, dayOfWeek);
        if (list == null) return new ArrayList<>();
        return list;
    }
}
