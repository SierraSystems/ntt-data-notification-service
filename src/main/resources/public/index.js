function getStarted() {
    console.log("hello there");

    var string = `{
        "splunkWebHookUrls": [
          {
          "chatApp": "TEAMS",
          "url": "https://outlook.office.com/webhook/45ab4e59-e51b-4d89-8a40-648375f19767@65e4e06f-f263-4c1f-becb-90deb8c2d9ff/IncomingWebhook/a01363ce862e48938c923e60110953eb/757dc14b-e969-480f-9023-75aa4c2e7f15"
          },
          {
          "chatApp": "ROCKET_CHAT",
          "url": "https://chat.pathfinder.gov.bc.ca/hooks/nRaa9Pa76mREwmMp2/ggavY8ub5MthKw2teuj6yRZfBb2SGLjW8vKi9kuX5jAfLQpn"
          }
        ]
    }`;
    console.log(btoa(string).replace('+', '-').replace('/', '_').replace(/=+$/, ''));

    // try getting token from yml
    // Get document, or throw exception on error
//    try {
//        const doc = jsyaml.safeLoad(fs.readFileSync('../application.yml', 'utf8'));
//        console.log(doc);
//    } catch (e) {
//        console.log(e);
//    }

    //http://192.168.56.1:8888/splunk/alert/3ac241ab-4448-4f1a-b925-29ac7fe6e37e/

    let webHookUrls = {
        "splunkWebHookUrls": []
    };

    for (i = 0; i < $('.teams-url').length; i++) {
        // Create object
        const chatAppUrl = {
            "chatApp": "TEAMS",
            "url": $('.teams-url').eq(i).val()
        };

        // Push object to the array
        webHookUrls.splunkWebHookUrls.push(chatAppUrl);
    }
    //print the array or use it for your further logic
    console.log(webHookUrls);
}
