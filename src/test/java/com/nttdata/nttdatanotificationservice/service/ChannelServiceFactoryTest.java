package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.rocket.RocketChannelService;
import com.nttdata.nttdatanotificationservice.teams.TeamsChannelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ChannelServiceFactoryTest {
    @InjectMocks
    ChannelServiceFactory channelServiceFactory;

    @Mock
    List<ChannelService> channelServices;

    @BeforeEach
    void initialize() {
        MockitoAnnotations.initMocks(this);
        List<ChannelService> mockedList = Arrays.asList(new TeamsChannelService(), new RocketChannelService());
        Mockito.when(channelServices.stream()).thenReturn(mockedList.stream());
    }

    @DisplayName("Success - ChannelServiceFactory ROCKET")
    @Test
    void testRocketSuccess() {
        Optional<ChannelService> rocketService = channelServiceFactory.getChanelService(ChatApp.ROCKET_CHAT);

        RocketChannelService newService = (RocketChannelService)rocketService.get();
        Assertions.assertEquals(ChatApp.ROCKET_CHAT, newService.getChatApp());
    }

    @DisplayName("Success - ChannelServiceFactory TEAMS")
    @Test
    void testTeamsSuccess() {
        Optional<ChannelService> rocketService = channelServiceFactory.getChanelService(ChatApp.TEAMS);

        TeamsChannelService newService = (TeamsChannelService)rocketService.get();
        Assertions.assertEquals(ChatApp.TEAMS, newService.getChatApp());
    }
}
