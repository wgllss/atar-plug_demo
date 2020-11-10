/**
 *
 */
package com.atar.host.app.activity.web;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;


/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-6-2上午10:41:51
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class ImplWebChromeClient extends WebChromeClient {

    private WebViewActivity activity;

    public ImplWebChromeClient(WebViewActivity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
        WebView childView = new WebView(activity);
        final WebSettings settings = childView.getSettings();
        settings.setJavaScriptEnabled(true);
        childView.setWebChromeClient(this);
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(childView);
        resultMsg.sendToTarget();
        return true;
    }

    /**
     * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
     */
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("").setMessage(message).setPositiveButton("确定", null);
        // 不需要绑定按键事件
        // 屏蔽keycode等于84之类的按键
        builder.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        // 禁止响应按back键的事件
        builder.setCancelable(false);
        if (activity != null && (!activity.isFinishing() || !activity.isDestroyed())) {
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
        return true;
        // return super.onJsAlert(view, url, message, result);
    }

    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return super.onJsBeforeUnload(view, url, message, result);
    }

    /**
     * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
     */
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("").setMessage(message).setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }
        }).setNeutralButton("取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.cancel();
            }
        });
        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                result.cancel();
            }
        });

        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
        builder.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        // 禁止响应按back键的事件
        // builder.setCancelable(false);
        if (activity != null && (!activity.isFinishing() || !activity.isDestroyed())) {
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return true;
        // return super.onJsConfirm(view, url, message, result);
    }

    /**
     * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
     * window.prompt('请输入您的域名地址', '618119.com');
     */
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("").setMessage(message);
        final EditText et = new EditText(view.getContext());
        et.setSingleLine();
        et.setText(defaultValue);
        builder.setView(et).setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.confirm(et.getText().toString());
            }

        }).setNeutralButton("取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.cancel();
            }
        });

        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
        builder.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });

        // 禁止响应按back键的事件
        // builder.setCancelable(false);
        if (activity != null && (!activity.isFinishing() || !activity.isDestroyed())) {
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }
}
