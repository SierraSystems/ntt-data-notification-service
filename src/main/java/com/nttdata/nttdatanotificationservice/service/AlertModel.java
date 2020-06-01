package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.sources.generic.models.GenericAlert;

public interface AlertModel {

  GenericAlert convertToGeneric();

}
