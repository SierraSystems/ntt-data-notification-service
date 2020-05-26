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
docker run -d -p <LOCAL_PORT>:8000 -p 8088:8088  -e "SPLUNK_START_ARGS=--accept-license" -e "SPLUNK_PASSWORD=<LOCAL_SPLUNK_PASSWORD>" --name <SPLUNK_CONTAINER_NAME> splunk/splunk:latest
```

Optional: You can set a property in `application.properties` if you would like to customize the port your app runs on:
```bash
server.port=${PORT:<>}
```

### Frontend

Once the application is running, the frontend page should be accessible at `http://localhost:<PORT>/splunk`. If not altered, this should be port 6060 on your local machine.
