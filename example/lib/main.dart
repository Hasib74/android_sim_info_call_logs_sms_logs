import 'package:call_log/call_log.dart';
import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';
import 'package:sim_sms_call_info/MonuSimInfo.dart';
import 'package:sim_sms_call_info/methodChannel/MonuSimInfo/Model/SimInfo.dart';
import 'package:sim_sms_call_info/methodChannel/MonuSimInfo/Model/SmsInfoModel.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();

    getSimInfo();
    getSmsLogs();
    getCallLogs();
  }

  @override
  Widget build(BuildContext context) {
    // MonuSimInfo.getSimInfo();
    // MonuSimInfo.getSMSInfo();

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }

  Future<SimInfo> getSimInfo() async {
    return MonuSimInfo.getSimInfo();
  }

  Future<SmsInfoModel> getSmsLogs() async {
    return MonuSimInfo.getSMSInfo();

  }

  Future<Iterable<CallLogEntry>> getCallLogs()  async{
    return  await MonuSimInfo.getCallLogs();
  }
}
