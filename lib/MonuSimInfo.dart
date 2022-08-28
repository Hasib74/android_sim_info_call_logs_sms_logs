import 'dart:convert';

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
          MethodChannel(AppMethodChannelHelper.CHANNEL_SIM_INFO);

      var _data = await appPlatform
          .invokeMethod(AppMethodChannelHelper.METHOD_SIM_INFO);
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
        MethodChannel(AppMethodChannelHelper.CHANNEL_SIM_INFO);

    var _data =
        await appPlatform.invokeMethod(AppMethodChannelHelper.METHOD_SMS_INFO);
    debugPrint("The SMS result : ${_data}");

    return SmsInfoModel.fromJson(jsonDecode(_data));
  }


  //static Future<>
}
