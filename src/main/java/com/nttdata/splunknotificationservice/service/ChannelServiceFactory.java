package com.nttdata.splunknotificationservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceFactory {

  @Autowired
  List<ChannelService> channelServices;

  public Optional<ChannelService> getChanelService(ChatApp chatApp) {
    Optional<ChannelService> channelService = channelServices.stream().filter(x -> x.getChatApp() == chatApp).findFirst();

    return channelService;
  }
}
