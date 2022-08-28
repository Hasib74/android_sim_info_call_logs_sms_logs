import 'package:flutter/cupertino.dart';

class SmsInfoModel {
  List<SMS>? smsList;

  SmsInfoModel.fromJson(Map<String, dynamic> json) {
    debugPrint("SMS INFO : ${json}");
    // print("Sms info :: ${json}");

    if (json['sms'] != null) {
      // print("Sms info :: 1 ${json["sms"]}");
      smsList = <SMS>[];
      json['sms'].forEach((v) {
        if (v != null) {
          Map<String, dynamic> _data = v as Map<String, dynamic>;
          smsList!.add(SMS.fromJson(_data));
        }
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};

    data["sms"] = [];

    List<Map<String, dynamic>> _smsData = [];
    print(
        "smsList length from sim info model toJson function before loop: ${smsList!.length} ");

    for (int i = 0; i < smsList!.length; i++) {
      debugPrint("Sms list data check for index ${i} and value ${{
        "body": smsList![i].body ?? "",
        "isRead": smsList![i].isRead ?? "",
        "sim": smsList![i].sim ?? "",
        "date_sent": smsList![i].date_sent ?? "",
        "date": smsList![i].date ?? "",
        "service_center": smsList![i].service_center ?? "",
        "person": smsList![i].person ?? ""
      }}");

      try {
        _smsData.add({
          "body": smsList![i].body ?? "",
          "isRead": smsList![i].isRead ?? "",
          "sim": smsList![i].sim ?? "",
          "date_sent": smsList![i].date_sent ?? "",
          "date": smsList![i].date ?? "",
          "service_center": smsList![i].service_center ?? "",
          "person": smsList![i].person ?? ""
        });
      } catch (err) {
        print("error when sim data add to json ${err.toString()}");
      }
    }

    data["sms"] = _smsData;
    print(
        "Sms length from sim info model toJson function : ${data["sms"].length} ");
    print(
        "smsList length from sim info model toJson function : ${smsList!.length} ");
    return data;
  }

  String _printDuration(Duration duration) {
    String twoDigits(int n) => n.toString().padLeft(2, "0");
    String twoDigitMinutes = twoDigits(duration.inMinutes.remainder(60));
    String twoDigitSeconds = twoDigits(duration.inSeconds.remainder(60));
    return "${twoDigits(duration.inHours)}:$twoDigitMinutes:$twoDigitSeconds";
  }
}

class SMS {
  String? body;
  String? isRead;
  int? sim;
  String? date_sent;
  String? date;
  String? service_center;
  String? person;

  SMS(
      {this.body,
      this.isRead,
      this.sim,
      this.date_sent,
      this.date,
      this.service_center,
      this.person});

  SMS.fromJson(Map<String, dynamic> json) {
    // print("Sms info :: 3 ${json}");
    // print("Sms info :: 4 ${json["body"]}");
    body = json['body'] ?? "";
    isRead = json['isRead'] ?? "";
    sim = json["sim"] ?? "";
    date_sent = json["date_sent"] ?? "";
    date = json["date"] ?? "";
    service_center = json["service_center"] ?? "";
    person = json["person"] ?? "";
  }
}
