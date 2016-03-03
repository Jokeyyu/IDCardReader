package com.yishu.idcarder;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by Administrator on 2016/2/26.
 */
public class AppManager
{
    private ActivityManager activityManager;
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    public AppManager(){}
/**
 * 获取AppManager实例
 */
    public static AppManager getAppManager()
    {
        if (instance == null)
        {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity)
    {
        if (activityStack == null)
        {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity(堆栈中最后一个加入)
     */
    public Activity getCurrentActivity()
    {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 关闭指定Activity
     */
    public void finish(Activity activity)
    {
        if (activity != null)
        {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 关闭指定类名Activity
     */
    public void finish(Class<?> cls)
    {
        for (Activity activity : activityStack)
        {
            if (activity.getClass().equals(cls))
            {
                finish(activity);
            }
        }
    }

    /**
     * 关闭所有Activity
     */
    public void finishAll()
    {
        for (int i = 0, size = activityStack.size(); i < size; i ++)
        {
            if (activityStack.get(i) != null)
            {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     *退出App
     */
    public void ExitApp(Context context)
    {
        try
        {
            finishAll();
            activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        }catch (Exception e){}
    }
}
