class SimInfo {
  List<IccIds>? iccIds;
  List<SubscriptionIds>? subscriptionIds;

  SimInfo({this.iccIds, this.subscriptionIds});

  SimInfo.fromJson(Map<String, dynamic> json) {
    if (json['iccIds'] != null) {
      iccIds = <IccIds>[];
      json['iccIds'].forEach((v) {
        iccIds!.add(new IccIds.fromJson(v));
      });
    }
    if (json['subscriptionIds'] != null) {
      subscriptionIds = <SubscriptionIds>[];
      json['subscriptionIds'].forEach((v) {
        subscriptionIds!.add(new SubscriptionIds.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.iccIds != null) {
      data['iccIds'] = this.iccIds!.map((v) => v.toJson()).toList();
    }
    if (this.subscriptionIds != null) {
      data['subscriptionIds'] =
          this.subscriptionIds!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class IccIds {
  String? iccId;
  int? simSlot;

  IccIds({this.iccId, this.simSlot});

  IccIds.fromJson(Map<String, dynamic> json) {
    iccId = json['iccId'];
    simSlot = json['simSlot'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['iccId'] = this.iccId;
    data['simSlot'] = this.simSlot;
    return data;
  }
}

class SubscriptionIds {
  int? simSlot;
  int? subsCriptionId;

  SubscriptionIds({this.simSlot, this.subsCriptionId});

  SubscriptionIds.fromJson(Map<String, dynamic> json) {
    simSlot = json['simSlot'];
    subsCriptionId = json['subsCriptionId'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['simSlot'] = this.simSlot;
    data['subsCriptionId'] = this.subsCriptionId;
    return data;
  }
}