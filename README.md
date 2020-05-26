[![Test Coverage](https://api.codeclimate.com/v1/badges/a1719884328523a5b49d/test_coverage)](https://codeclimate.com/github/akroon3r/splunk-notification-service/test_coverage) [![Maintainability](https://api.codeclimate.com/v1/badges/a1719884328523a5b49d/maintainability)](https://codeclimate.com/github/akroon3r/splunk-notification-service/maintainability)

# splunk-notification-service

A Service that pushes notification to different channels.

Teams notification screenshot:

![teams-message](docs/images/teams-notification.png)

## Running Locally

```bash
docker-compose up --build splunk-notification-service
```

This should spin up the splunk-notification-service locally.

To run the latest version of splunk, run the following command to spin up a container locally:

```bash
docker run -d -p <LOCAL_SPLUNK_PORT>:8000 -p 8088:8088  -e "SPLUNK_START_ARGS=--accept-license" -e "SPLUNK_PASSWORD=<LOCAL_SPLUNK_PASSWORD>" --name <SPLUNK_CONTAINER_NAME> splunk/splunk:latest
```

Optional: You can set a property in `application.properties` if you would like to customize the port your app runs on:
```bash
server.port=${PORT:<>}
```

### Frontend

Once the application is running, the frontend page should be accessible at `http://localhost:<PORT>/splunk`. If not altered, this should be port 6060 on your local machine.

<img width="578" alt="ui" src="https://user-images.githubusercontent.com/28017034/82940900-4f985100-9f4a-11ea-868a-6f126879ab9d.PNG">

In order to see the mocha frontend unit test results locally, navigate to `http://localhost:<PORT>/splunk/testrunner.html`.

<img width="941" alt="mocha" src="https://user-images.githubusercontent.com/28017034/82940892-4dce8d80-9f4a-11ea-9ce1-23f517c4f4f2.PNG">

### Splunk WebHook Setup

Once you have splunk and the notification service running locally, you'll need to setup the actual webhook to send notifications to your favourite teams/rocketchat channels. Follow the following steps to set this up:

1. Navigate to your local splunk instance at `http://localhost:<LOCAL_SPLUNK_PORT>`.

2. To try out an example log, type the following as a search in splunk:
```bash
index="_audit"
| eval message="Some message about something"
| table message source
```

3. Click `Save As` and select `Alert`.

4. Provide a title, set it to run on a cron schedule and provide a valid cron expression as per your frequency needs.

5. Add actions:
```
Webhook
Add to Triggered Alerts
```

6. In order to generate the Webhook URL, navigate to the application frontend, type in at least one valid teams/rocketchat URL and a valid token (present in the `application.yml` file) and copy the generated base64encoded URL. Paste in this value into splunk.

7. Press `Save`.

8. Click `Enable`.

<b>NOTE:</b> Make sure to disable this alert once done testing if it is only for testing purposes.
