package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;

public interface AlertModel {

  Notification convertToAlert();

}
