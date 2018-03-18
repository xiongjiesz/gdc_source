package com.gdc.gdcmvp.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.manager
 * @file AppManager
 * @date 2018/3/10
 */

public class AppManager {
    /**
     * Activity堆栈列表
     */
    private static Stack<Activity> activityStack;

    private AppManager() {

    }

    private static class AppManagerHelper{
        private static final AppManager instance = new AppManager();
    }

    public static AppManager getInstance(){
        return AppManagerHelper.instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if(activityStack.size() > 0){
            activity = activityStack.lastElement();
        }
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing())
                activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定Activity后的所有Actiivty
     */
    public void finishActivityByPosition(Activity activity) {
        int position = activityStack.search(activity);
        position = (activityStack.size() - position);
        List<Activity> activities = new ArrayList<Activity>();
        for (int i = position + 1; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                Activity activity1 = activityStack.get(i);
                activity1.finish();
                activities.add(activity1);
            }
        }
        activityStack.removeAll(activities);
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        Stack<Activity> tempStack = new Stack<Activity>();
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                if (!activity.isFinishing())
                    activity.finish();
                tempStack.add(activity);
            }
        }
        activityStack.removeAll(tempStack);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        while (true) {
            if (activityStack.size() == 0) {
                break;
            }
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            finishActivity(activity);
        }
    }

    /**
     * @Description 杀死后台进程
     * @author llc
     * @param context
     */
    public static void killAll(Context context) {
        // 获取一个ActivityManager 对象
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取系统中所有正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 获取当前activity所在的进程
        String currentProcess = context.getApplicationInfo().processName;
        // 对系统中所有正在运行的进程进行迭代，如果进程名是当前进程，则Kill掉
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            String processName = appProcessInfo.processName;
            if (processName.equals(currentProcess)) {
                activityManager.killBackgroundProcesses(processName);
            }
        }
    }
    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        appExit(context,true);
    }

    /**
     * @Description 退出应用程序
     * @author xiongj
     * @param context
     * @param isTotal  true 跳转到launcher false 不跳转
     */
    public void appExit(Context context,boolean isTotal) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int currentVersion = android.os.Build.VERSION.SDK_INT;
            if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                if(isTotal){
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(startMain);
                }
                killAll(context);
            } else {// android2.1
                killAll(context);
                activityMgr.restartPackage(context.getPackageName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
