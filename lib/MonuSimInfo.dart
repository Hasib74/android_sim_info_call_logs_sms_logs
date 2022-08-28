import 'dart:convert';

import 'package:call_log/call_log.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'methodChannel/MonuSimInfo/Model/SimInfo.dart';
import 'methodChannel/MonuSimInfo/Model/SmsInfoModel.dart';
import 'methodChannel/app_method_channel_helper.dart';

class MonuSimInfo {
  static Future<SimInfo> getSimInfo() async {
    SimInfo? simInfo;

    try {
      MethodChannel appPlatform =
          MethodChannel(AppMethodChannelHelper.CHANNEL_sim_sms_call_info);

      var _data = await appPlatform
          .invokeMethod(AppMethodChannelHelper.METHOD_sim_sms_call_info);
      print("Sim Info Response : ${_data}");
      simInfo = SimInfo.fromJson(jsonDecode(_data));
    } on Exception catch (e) {
      // TODO

      print("Sim info error : ${e.toString()}");
    }
    return simInfo!;
  }

  static Future<SmsInfoModel> getSMSInfo() async {
    MethodChannel appPlatform =
        MethodChannel(AppMethodChannelHelper.CHANNEL_sim_sms_call_info);

    var _data =
        await appPlatform.invokeMethod(AppMethodChannelHelper.METHOD_SMS_INFO);
    debugPrint("The SMS result : ${_data}");

    return SmsInfoModel.fromJson(jsonDecode(_data));
  }

  static Future<Iterable<CallLogEntry>> getCallLogs({
    int? dateFrom,
    int? dateTo,
    int? durationFrom,
    int? durationTo,
    DateTime? dateTimeFrom,
    DateTime? dateTimeTo,
    String? name,
    String? number,
    CallType? type,
    String? numbertype,
    String? numberlabel,
    String? cachedNumberType,
    String? cachedNumberLabel,
    String? cachedMatchedNumber,
    String? phoneAccountId,
  }) async {
    Iterable<CallLogEntry> entries = await CallLog.query(
      dateFrom: dateFrom ,
       dateTo : dateTo ,
      durationFrom : durationFrom ,
       durationTo: durationTo ,
       dateTimeFrom : dateTimeFrom,
        dateTimeTo : dateTimeTo ,
       name : name,
       number :number,
       type : type ,
       numbertype : numbertype,
       numberlabel : numberlabel ,
       cachedNumberType : cachedNumberType ,
       cachedNumberLabel : cachedNumberLabel,
       cachedMatchedNumber : cachedMatchedNumber ,
       phoneAccountId : phoneAccountId
    );

    return entries;
  }
}
