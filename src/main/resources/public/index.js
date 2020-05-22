// populate these two fields based on your local dev environment
const localIP = "192.168.56.1";
const port = "8888";

function generateWebHookUrlString() {
    const teamsUrlIdentifier = ".teams-url";
    const rocketCharUrlIdentifier = ".rocket-chat-url";
    let webHookUrls = {};
    let splunkWebHookUrls = [];

    // loop through teams urls
    for (i = 0; i < $(teamsUrlIdentifier).length; i++) {
        // Create object
        const chatAppUrl = {
            "chatApp": "TEAMS",
            "url": $(teamsUrlIdentifier).eq(i).val()
        };

        // Push object to the array
        splunkWebHookUrls.push(chatAppUrl);
    }

    // loop through rocket chat urls
    for (i = 0; i < $(rocketCharUrlIdentifier).length; i++) {
        // Create object
        const chatAppUrl = {
            "chatApp": "ROCKET_CHAT",
            "url": $(rocketCharUrlIdentifier).eq(i).val()
        };

        // Push object to the array
        splunkWebHookUrls.push(chatAppUrl);
    }

    webHookUrls.splunkWebHookUrls = splunkWebHookUrls;
    return JSON.stringify(webHookUrls);
}

// url safe UTF-8 base 64 encoding
function base64EncodeString(string) {
    return btoa(string).replace('+', '-').replace('/', '_').replace(/=+$/, '');
}

function createNewUrlInput(urlType) {
    const id = Math.floor(Math.random() * 100000);
    let txtNewInputBox = document.createElement('div');
    txtNewInputBox.setAttribute("id", id);
    txtNewInputBox.innerHTML = "<br/><input type='text' style='margin-right: 4px' class='" + urlType + "'><button type='button' onclick='deleteUrl(" + id + ")'>Remove URL</button>";
    document.getElementById("add-" + urlType + "-here").appendChild(txtNewInputBox);
}

function deleteUrl(elementId) {
    // Removes an element from the document
    var element = document.getElementById(elementId);
    element.parentNode.removeChild(element);
}

function generateUrl() {
    // temporarily for local dev
    const baseUrl = `http://${localIP}:${port}/splunk/alert/`;
    const token = $(".token").val();
    const base64EncodedUrl = base64EncodeString(generateWebHookUrlString());

    const finalUrl = `${baseUrl}${token}/${base64EncodedUrl}`;

    document.getElementById("final-url").innerHTML = finalUrl;
    // display the url
    $("#final-url-div").show();
}
