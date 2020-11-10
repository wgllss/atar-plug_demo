package com.common.framework.utils;

import android.app.Application;

import java.lang.reflect.Field;

public class AppBuildConfig {
    public static String APPLICATION = "";


    public static boolean isLog() {
        boolean isLog;
        try {
            Class<?> clazz = Class.forName(APPLICATION);
            Field field = clazz.getDeclaredField("isLog");
            isLog = (boolean) field.get(null);
            return isLog;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static Application sApplication;

    public static Application getApplication() {
        if (sApplication != null) {
            return sApplication;
        }
        try {
            Class<?> clazz = Class.forName(APPLICATION);
            Field field = clazz.getField("application");
            sApplication = (Application) field.get(null);
            return sApplication;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
