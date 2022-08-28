package com.example.sim_info;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.content.Context.TELEPHONY_SUBSCRIPTION_SERVICE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.src.main.java.com.example.sim_info.ChannelName.ChannelName.CHANNEL_SIM_INFO;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.example.sim_info.Model.SMS;
import com.example.sim_info.Model.SmsModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SimInfoPlugin
 */
public class SimInfoPlugin extends Activity implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// context local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity

    private static final int PERMISSION_REQUEST_CODE = 204;
    private static final String TAG = "permission";
    // MonuSimInfo monuSimInfo;
    private static final Uri ICC_URI = Uri.parse("content://sms/icc");

    ContentResolver contentResolver;

    Activity activity;


    private ContentResolver mContentResolver;
    private android.database.Cursor mCursor = null;

    private android.content.AsyncQueryHandler mQueryHandler = null;


    private MethodChannel channel;

    Context context;


    SubscriptionManager subscriptionManager = null;


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        context = flutterPluginBinding.getApplicationContext();


//        if (context instanceof AppCompatActivity)
//            this.activity = (AppCompatActivity) context;

        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_SIM_INFO);
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            subscriptionManager = (SubscriptionManager) context.getSystemService(TELEPHONY_SUBSCRIPTION_SERVICE);
        }

        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals(android.src.main.java.com.example.sim_info.ChannelName.ChannelName.METHOD_SIM_INFO)) {


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    int subscriptionManager;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        subscriptionManager = SubscriptionManager.getActiveDataSubscriptionId();

                    }


                    String _idInfo = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        _idInfo = getSimInfo();
                    }


                    result.success(_idInfo);
                }
            }, 1000)

            ;

        } else if (call.method.equals(android.src.main.java.com.example.sim_info.ChannelName.ChannelName.METHOD_SMS_INFO)) {


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // yourMethod();

                    SmsModel smsModel = getSMS();
                    result.success(new Gson().toJson(smsModel));
                }
            }, 1000);


        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }


    private SmsModel getSMS() {


        if (ActivityCompat.checkSelfPermission(
                context,
                READ_SMS) != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS}, 200);

        }


        System.out.println("Get all sms");

        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms"), null, "datetime(date/1000, 'unixepoch') between date('now', '-4 day') and date('now')", null, null);
        // DatabaseUtils.dumpCursor(cursor);


        // int SERVICE_CENTER_ADDRESS = cursor.getColumnIndex("service_center_address") ;
        int ADDRESS = cursor.getColumnIndex("address");
        int SERVICE_CENTER = cursor.getColumnIndex("service_center");
        int SEEN = cursor.getColumnIndex("seen");
        int DATE_SENT = cursor.getColumnIndex("date_sent");
        int DATE = cursor.getColumnIndex("date");
        int PERSION = cursor.getColumnIndex("person");


        int SUB_ID = cursor.getColumnIndex("sub_id");

        //  int MESSAGE_CLASS = cursor.getColumnIndex("message_class") ;
        int BODY = cursor.getColumnIndex("body");

//         int INDEX_OF_ICC= cursor.getColumnIndex("index_on_icc") ;

        System.out.println("Get all sms ::" + cursor.toString());


        //String _body = cursor.getString(BODY);

        System.out.println("Get all sms bodyyy  :: " + cursor.getCount());

        List<SMS> sms_list = new ArrayList<>();


        try {
            while (cursor.moveToNext()) {

                //System.out.println("Get all sms 22");

                // String serviceCenterAddress = cursor.getString(SERVICE_CENTER_ADDRESS);
                String address = cursor.getString(ADDRESS);
                //   String message_class = cursor.getString(MESSAGE_CLASS);
                String body = cursor.getString(BODY);
                String sub_id = cursor.getString(SUB_ID);
                String service_center = cursor.getString(SERVICE_CENTER);
                String seen = cursor.getString(SEEN);
                String date_sent = cursor.getString(DATE_SENT);
                String date = cursor.getString(DATE);
                String persion = cursor.getString(PERSION);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    sms_list.add(new SMS(
                            body,
                            seen, findSlotFromSubId(Integer.parseInt(sub_id)), date_sent, date, service_center, persion

                    ));
                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    Log.v("Sim Message info\n", "\n \nGet all sms Message Info \n  " + "\nSub ID :" + sub_id + "\nAddress : " + address + "\nBody : " + body + "\nSim Slot :" + findSlotFromSubId(Integer.parseInt(sub_id)) + "\nService center : " + service_center + "\nSeen : " + seen + "\nDate sent : " + date_sent + "\nDate :" + date + "\n **************   End   ***********");
                }


            }
        } catch (Exception err) {

            System.out.println("Get all sms error :: " + err.toString());

            cursor.close();

        }


        //cursor.close();


        return new SmsModel(sms_list);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    int findSlotFromSubId(int subId) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                //   ActivityCompat.requestPermissions(context.getApplicationContext(), new String[]{Manifest.permission.READ_PHONE_STATE}, 500);
            } else {
                Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show();
            }

            // return -1;
        }
        for (SubscriptionInfo subscriptionInfo : SubscriptionManager.from(context).getActiveSubscriptionInfoList()) {


            if (subscriptionInfo.getSubscriptionId() == subId) {

                return subscriptionInfo.getSimSlotIndex();

            }

        }

        return -2;
    }


    // @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String getSimInfo() {

        //requestPermission();
        com.example.sim_info.SimInfo simInfo = new com.example.sim_info.SimInfo();


        List<SubscriptionInfo> _subscriptionInfo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            _subscriptionInfo = subscriptionManager.getActiveSubscriptionInfoList();
        }


        List<com.example.sim_info.SubscriptionId> subscriptionIdsList = new ArrayList<>();

        for (SubscriptionInfo subscriptionInfo : _subscriptionInfo) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                subscriptionIdsList.add(new com.example.sim_info.SubscriptionId(_subscriptionInfo.indexOf(subscriptionInfo), subscriptionInfo.getSubscriptionId()));
            }
        }

        simInfo.setSubscriptionIds(subscriptionIdsList);

        TelecomManager tm2 = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);

        List<com.example.sim_info.IccId> _iccIdList = new ArrayList<>();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < tm2.getCallCapablePhoneAccounts().size(); i++) {
                _iccIdList.add(new com.example.sim_info.IccId(i, tm2.getCallCapablePhoneAccounts().get(i).getId()));
            }
        }

        simInfo.setIccIds(_iccIdList);

        return new Gson().toJson(simInfo);
    }


    private void app_requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                System.out.println("Activity info : " + activity);
                //ActivityCompat.requestPermissions(,new String[]{READ_SMS, READ_PHONE_NUMBERS, READ_PHONE_STATE}, 100);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_NUMBERS}, 100);

            }

            //ActivityCompat.requestPermissions(context,);
        }
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100:
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(context, READ_SMS) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                        READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                String phoneNumber = telephonyManager.getLine1Number();
                //phone_number.setText(phoneNumber);

                System.out.println("Phone number ... " + phoneNumber);

                break;


            case 200:

                if (ActivityCompat.checkSelfPermission(context, READ_SMS) !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Read phone state permission guranted", Toast.LENGTH_SHORT).show();


                } else {

                    return;
                }

            case 500:
                //TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                        READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {

                    Toast.makeText(context, "Read phone state permission guranted", Toast.LENGTH_SHORT).show();

                }
                //String phoneNumber = telephonyManager.getLine1Number();
                //phone_number.setText(phoneNumber);

                // System.out.println("Phone number ... " + phoneNumber);

                break;

            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {


        activity = binding.getActivity();


        if (ActivityCompat.checkSelfPermission(
                context,
                READ_PHONE_STATE
        ) != PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                READ_SMS
        ) != PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                READ_PHONE_NUMBERS
        ) != PERMISSION_GRANTED

        ) {
            System.out.println("Permission Not Granted -> Manifest.permission.READ_PHONE_STATE");

            app_requestPermission();

            // throw  Exception("Permission Not Granted -> Manifest.permission.READ_PHONE_STATE");
        }


    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivity() {
        activity = null;
    }


//  @Override
//  public void onPointerCaptureChanged(boolean hasCapture) {
//    super.onPointerCaptureChanged(hasCapture);
//  }
}
