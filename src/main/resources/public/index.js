// populate these two fields based on your local dev environment
const localIP = "192.168.56.1";
const port = "8888";

function generateWebHookUrlString() {
    const teamsUrlIdentifier = ".teams-url";
    const rocketChatUrlIdentifier = ".rocket-chat-url";
    let webHookUrls = {};
    let splunkWebHookUrls = [];

    // loop through teams urls
    for (i = 0; i < $(teamsUrlIdentifier).length; i++) {
        // ensure url is valid
        let isTeamsUrlValid = true;
        let isRocketChatUrlValid = true;
        const teamsUrlValue = $(teamsUrlIdentifier).eq(i).val();
        const rocketChatUrlValue = $(rocketChatUrlIdentifier).eq(i).val();

        if (!teamsUrlValue && !rocketChatUrlValue) {
            $(".error").show();
            return false;
        }

        if (!validateUrl(teamsUrlValue) && teamsUrlValue) {
            $(".url-error-teams").show();
            isTeamsUrlValid = false;
        }

        if (!validateUrl(rocketChatUrlValue) && rocketChatUrlValue) {
            $(".url-error-rocket").show();
            isRocketChatUrlValid = false;
        }

        if (!isTeamsUrlValid && !isRocketChatUrlValid) return false;

        // Create object
        const chatAppUrl = {
            "chatApp": "TEAMS",
            "url": $(teamsUrlIdentifier).eq(i).val()
        };

        // Push object to the array
        splunkWebHookUrls.push(chatAppUrl);
    }

    // loop through rocket chat urls
    for (i = 0; i < $(rocketChatUrlIdentifier).length; i++) {
        // Create object
        const chatAppUrl = {
            "chatApp": "ROCKET_CHAT",
            "url": $(rocketChatUrlIdentifier).eq(i).val()
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
    const element = document.getElementById(elementId);
    element.parentNode.removeChild(element);
}

function validateUrl(url) {
  return /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:[/?#]\S*)?$/i.test(url);
}

function generateUrl() {
    // temporarily for local dev
    const baseUrl = `http://${localIP}:${port}/splunk/alert/`;
    const token = $(".token").val();
    const webHookUrlString = generateWebHookUrlString();

    if (!webHookUrlString) return false;

    if ($(".url-error-teams").is(":visible") || $(".url-error-rocket").is(":visible")) {
        return false;
    }

    const base64EncodedUrl = base64EncodeString(webHookUrlString);

    const finalUrl = `${baseUrl}${token}/${base64EncodedUrl}`;

    document.getElementById("final-url").innerHTML = finalUrl;
    // display the url
    $("#final-url-div").show();
}
