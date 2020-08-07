// populate these two fields based on your local dev environment
const url = window.location.origin;
const teamsUrlIdentifier = ".teams-url";
const rocketChatUrlIdentifier = ".rocket-chat-url";


// ######################## HTML Element Functions ########################### //
function deleteUrl(elementId) {
    const element = document.getElementById(elementId);
    element.parentNode.removeChild(element);
}

function addNewUrl(urlType) {
    const urlVal = $(`#${urlType}-id`).val();
    if (!urlVal) return;

    const id = Math.floor(Math.random() * 100000);
    let txtNewInputBox = document.createElement('div');
    txtNewInputBox.setAttribute("id", id);
    txtNewInputBox.setAttribute("class", "input-group");
    txtNewInputBox.innerHTML = "<input type='text' class='entered-urls mt-4 "+ urlType +"' value='" + urlVal + "' readonly><div class='input-group-addon mt-4'><button type='button' class='pointer' onclick='deleteUrl(" + id + ")'>x</button></div>";
    document.getElementById("add-" + urlType + "-here").appendChild(txtNewInputBox);
    $(`#${urlType}-id`).val("");
}


// ######################## URL Generation Functions ########################### //
function generateUrls(urlType, chatType) {
    // get url in non-submitted input field as well
    let webHooks = getInputFieldValues(chatType);

    // loops through urls
    for (let i = 0; i < $(urlType).length; i++) {
        let errorExists = false;
        const urlValue = $(urlType).eq(i).val();

        // if url incorrect or empty, skip this url
        if (!validateUrl(urlValue)) errorExists = true;

        // Create object
        const chatAppUrl = {
            "chatApp": chatType,
            "url": urlValue,
            "errorExists": errorExists
        };

        // Push object to the array if doesn't already exist
        if (!checkForDuplicatesAndEmpty(webHooks, chatAppUrl)) webHooks.push(chatAppUrl);
    }

    return webHooks;
}

function getInputFieldValues(chatType) {
    let webHooks = [];

    const urlVal = chatType === "TEAMS" ? $("#teams-url-id").val() : $("#rocket-chat-url-id").val();
    if (validateUrl(urlVal)) {
        const chatUrl = {
            "chatApp": chatType,
            "url": urlVal,
            "errorExists": false
        };

        // Push object to the array if doesn't already exist
        if (!checkForDuplicatesAndEmpty(webHooks, chatUrl)) webHooks.push(chatUrl);
    }

    return webHooks;
}

function generateWebHookUrlString() {
    let webHookUrls = {};
    let splunkWebHookUrls = [];
    const generatedTeamsUrl = generateUrls(teamsUrlIdentifier, "TEAMS");
    const generatedRocketUrl = generateUrls(rocketChatUrlIdentifier, "ROCKET_CHAT");

    const teamsErrorsExist = errorsExist(generatedTeamsUrl, ".url-error-teams");
    const rocketErrorsExist = errorsExist(generatedRocketUrl, ".url-error-rocket");

    if (!teamsErrorsExist && !rocketErrorsExist && (!isEmpty(generatedTeamsUrl) || !isEmpty(generatedRocketUrl))) {
        splunkWebHookUrls = populateSplunkWebHooks(generatedTeamsUrl, splunkWebHookUrls);
        splunkWebHookUrls = populateSplunkWebHooks(generatedRocketUrl, splunkWebHookUrls);

        webHookUrls.webHookUrls = splunkWebHookUrls;
        return JSON.stringify(webHookUrls);
    }

    $(".error").show();
    return false;
}

function generateFinalUrl() {
    const baseUrl = `${url}/notification/splunk/`;

    const token = $(".token").val();
    const webHookUrlString = generateWebHookUrlString();

    if (!validateToken(token)) return false;

    if ($(".url-error-teams").is(":visible") || $(".url-error-rocket").is(":visible") || !webHookUrlString) return false;

    const finalUrl = `${baseUrl}${token}/${base64EncodeString(webHookUrlString)}`;
    document.getElementById("generatedUrl").innerHTML = finalUrl;
}

function populateSplunkWebHooks(urlArray, webHooksArray) {
    if (!isEmpty(urlArray)) {
        urlArray.forEach(urlVal => {
            webHooksArray.push({ "chatApp": urlVal.chatApp, "url": urlVal.url });
        });
    }

    return webHooksArray;
}


// ######################## Helper Functions ########################### //
function base64EncodeString(string) {
    return btoa(string).replace('+', '-').replace('/', '_').replace(/=+$/, '');
}

function validateUrl(url) {
    if (!url) return false;

    return /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:[/?#]\S*)?$/i.test(url);
}

function checkForDuplicatesAndEmpty(array, obj) {
    if (!obj.url) return true;

    let duplicatesExist = false;

    array.forEach(element => {
        if (element.chatApp === obj.chatApp && element.url === obj.url) duplicatesExist = true;
    });

    return duplicatesExist;
}

function errorsExist(array, type) {
    let error = false;

    array.forEach(url => {
        if (url.errorExists) {
            $(type).show();
            error = true;
        }
    });

    return error;
}

function isEmpty(array) {
    return array.length === 0;
}

function validateToken(token) {
    if (!token) {
        $(".token-error").show();
        return false;
    }

    return true;
}

function copyToClipboard() {
    const textToCopy = document.getElementById("generatedUrl");

    textToCopy.select();
    textToCopy.setSelectionRange(0, 99999);

    document.execCommand("copy");

    $(".success-copy").show();
}

module.exports = { isEmpty, validateToken, errorsExist, checkForDuplicatesAndEmpty, validateUrl, populateSplunkWebHooks };
