package com.common.framework.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * *****************************************************************************************
 *
 * @className: 网络反射类
 * @author: Atar
 * @createTime:2014-5-18下午10:20:43
 * @modifyTime:
 * @version: 1.0.0
 * @description:对网络实现的方法进行反射 *****************************************************************************************
 */
public class Reflection {

    /**
     * @param className:   类名
     * @param methodName:  方法名
     * @param args:方法中传递参数
     * @throws Exception ：处理了反射的异常和操作网络时产生的异常重新获取作自定义处理
     * @methodName: 执行某类的静态操作网络方法
     * @atour: Atar
     * @createTime:2014-5-18下午10:31:38
     * @modifyTime:
     * @version: 1.0.0
     * @return:执行方法返回的结果
     * @description: 异常处理为重点
     */
    @SuppressWarnings({"rawtypes", "unchecked", "unused"})
    public static Object invokeStaticMethod(String className, String methodName, Object[] args) {

        Object objReturn = null;
        try {
            Class ownerClass = Class.forName(className);

            // Class[] argsClass = new Class[args.length];
            //
            // for (int i = 0, j = args.length; i < j; i++) {
            // argsClass[i] = args[i].getClass();
            // }
            Method[] methods = ownerClass.getMethods();
            if (methods.length == 0) {
                return null;
            }
            int flag = 0;
            for (int i = 0; i < methods.length; ++i) {
                if (methods[i].getName().equals(methodName)) {// 找到方法名
                    Class[] clzs = methods[i].getParameterTypes();
                    if (clzs.length == args.length) {// 参数相同
                        if (clzs.length != 0) {
                            flag = 1;
                            for (int j = 0; j < clzs.length; ++j) {
                                if (args[j] != null && !clzs[j].isAssignableFrom(args[j].getClass())) {// 参数类型不对应
                                    flag = 2;
                                    break;
                                }
                            }
                            if (flag == 1) {
                                objReturn = methods[i].invoke(null, args);
                            }
                        }
                        // 方法名 找到 并且 参数个数对应了 跳出循环
                        break;
                    }
                }
            }
            if (flag != 1) {// 没有找到对应的方法
                String err = ExceptionEnum.ReflectionNoSuchMethodErrorException.class.getSimpleName();
                throw (ExceptionEnum.RefelectException) new ExceptionEnum.ReflectionNoSuchMethodErrorException(err, new Throwable());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // 反射函数找不到类
            String err = ExceptionEnum.ReflectionClassNotFoundException.class.getSimpleName() + e.getMessage();
            throw (ExceptionEnum.RefelectException) new ExceptionEnum.ReflectionClassNotFoundException(err, e.getCause());
        } catch (SecurityException e) {
            e.printStackTrace();
            // 反射函数安全错误
            String err = ExceptionEnum.ReflectionSecurityException.class.getSimpleName() + e.getMessage();
            throw (ExceptionEnum.RefelectException) new ExceptionEnum.ReflectionSecurityException(err, e.getCause());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            // 反射指针引用错误
            String err = ExceptionEnum.ReflectionIllegalAccessException.class.getSimpleName() + e.getMessage();
            throw (ExceptionEnum.RefelectException) new ExceptionEnum.ReflectionIllegalAccessException(err, e.getCause());
        } catch (NullPointerException e) {
            // 反射方法中有传入为null的参数
            String err = ExceptionEnum.ReflectionParamHasNullException.class.getSimpleName();
            throw (ExceptionEnum.RefelectException) new ExceptionEnum.ReflectionParamHasNullException(err, e.getCause());
        } catch (NoSuchMethodError e) {
            // 反射类中调用的方法不存在
            String err = ExceptionEnum.ReflectionNoSuchMethodErrorException.class.getSimpleName();
            throw (ExceptionEnum.RefelectException) new ExceptionEnum.ReflectionNoSuchMethodErrorException(err, e.getCause());
        } catch (IllegalArgumentException e) {
            // 反射类中传递参数不合法
            String err = ExceptionEnum.ReflectionNoSuchMethodErrorException.class.getSimpleName();
            throw (ExceptionEnum.RefelectException) new ExceptionEnum.ReflectionNoSuchMethodErrorException(err, e.getCause());
        } catch (InvocationTargetException e) {
            if (e != null) {
                // 重新从里面获取异常再抛出，三思！！！
                throw (ExceptionEnum.RefelectException) e.getTargetException();
            } else {
                // 未知错误异常
                throw (ExceptionEnum.RefelectException) new ExceptionEnum.RefelectException(ExceptionEnum.RefelectException.class.getSimpleName(), new Throwable());
            }
        }
        return objReturn;
    }
}
