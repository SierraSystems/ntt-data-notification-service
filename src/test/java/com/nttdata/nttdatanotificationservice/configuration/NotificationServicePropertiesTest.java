package com.nttdata.nttdatanotificationservice.configuration;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class NotificationServicePropertiesTest {
    @DisplayName("Success - Properties")
    @Test
    void testSuccess() {
        NotificationServiceProperties notificationServiceProperties = new NotificationServiceProperties();

        notificationServiceProperties.setTokens(Arrays.asList("TOKEN", "TOKEN2"));
        notificationServiceProperties.setUpdateCardBase("TESTURL");
        Assert.assertEquals(2, notificationServiceProperties.getTokens().size());
        Assert.assertEquals("TOKEN", notificationServiceProperties.getTokens().get(0));
        Assert.assertEquals("TOKEN2", notificationServiceProperties.getTokens().get(1));
        Assert.assertEquals("TESTURL", notificationServiceProperties.getUpdateCardBase());
    }
}
