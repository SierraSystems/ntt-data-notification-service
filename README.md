[![Test Coverage](https://api.codeclimate.com/v1/badges/b6bb35624e04cdfb7364/test_coverage)](https://codeclimate.com/github/SierraSystems/ntt-data-notification-service/test_coverage) [![Maintainability](https://api.codeclimate.com/v1/badges/b6bb35624e04cdfb7364/maintainability)](https://codeclimate.com/github/SierraSystems/ntt-data-notification-service/maintainability)

# ntt-data-notification-service

A Service that pushes notification to different channels.

Teams notification screenshot:

![teams-message](docs/images/teams-notification.png)

## Running Locally

```bash
docker-compose up --build ntt-data-notification-service
```

This should spin up the ntt-data-notification-service and the latest docker image of Splunk locally.

To modify the ports the app is running on, simply change the port mappings in the docker-compose file.

Optional: If not running the application through docker, you can set a property in `application.properties` if you would like to customize the port your app runs on:
```bash
server.port=${PORT:<PORT>}
```

### Frontend

Once the application is running, the frontend page should be accessible at `http://localhost:<PORT>/alert`. If not altered, this should be port 6060 on your local machine.

![ntt](https://user-images.githubusercontent.com/28017034/83478036-a8378480-a449-11ea-96fb-83a4e58c5004.PNG)

In order to see the mocha frontend unit test results locally, navigate to `http://localhost:<PORT>/alert/testrunner.html`.

### Splunk WebHook Setup

Once you have Splunk and the notification service running locally, you'll need to setup the actual webhook to send notifications to your favourite Teams/RocketChat channels. Follow the following steps to set this up:

1. Navigate to your local Splunk instance at `http://localhost:<LOCAL_SPLUNK_PORT>`. Via docker this port is defaulted to 7000.

2. To try out an example log, type the following as a search in Splunk:
```bash
index="_audit"
| eval message="Some message about something"
| table message source
```

3. Click <b>Save As</b> and select <b>Alert</b>.

4. Provide a title, set it to run on a cron schedule and provide a valid cron expression as per your frequency needs.

<img width="540" alt="alertsettings1" src="https://user-images.githubusercontent.com/28017034/82948601-e965fb00-9f56-11ea-93cd-7c81dd0bfc9a.PNG">

5. Add actions:
```
Webhook
Add to Triggered Alerts
```

<img width="498" alt="settings2" src="https://user-images.githubusercontent.com/28017034/82948608-ec60eb80-9f56-11ea-90e8-b1bbf4a17190.PNG">

6. In order to generate the Webhook URL, navigate to the application frontend, type in at least one valid Teams/RocketChat URL and a valid token (present in the `application.yml` file) and copy the generated base64encoded URL.

7. Paste this value into Splunk. If running the application via docker, this url will need to be modified. Replace
```
localhost:6060
```
with
```
172.20.0.6:8080
```

8. Press <b>Save</b>.

9. Click <b>View Alert</b>.

<b>NOTE:</b> Make sure to disable this alert once done testing if it is only for testing purposes.
