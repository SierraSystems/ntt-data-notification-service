const { isEmpty, validateToken, errorsExist, checkForDuplicatesAndEmpty, validateUrl, populateSplunkWebHooks } = require("./index.js");

const { assert } = require("chai");

before(() => {
  $ = require("jquery");
  global.$ = $;
});

describe('isEmpty', () => {
  it('should return true when array is empty', () => {
    assert.equal(true, isEmpty([]));
  });

  it('should return false when array is not empty', () => {
    assert.equal(false, isEmpty([1, 2, 3]));
  });
});

describe('validateToken', () => {
    it('should return false when there is no token', () => {
        assert.equal(false, validateToken(""));
    });

    it('should return true when a token is provided', () => {
        assert.equal(true, validateToken("token"));
    });
});

describe('errorsExist', () => {
    const type = ".teams-error";

    it('should return false if no object within the array has errors', () => {
        const array = [{ chatApp: "TEAMS", url: "http://www.example.com", errorExists: false }];
        assert.equal(false, errorsExist(array, type));
    });

    it('should return true if any object within the array has errors', () => {
        const array = [
            { chatApp: "TEAMS", url: "http://www.example.com", errorExists: false },
            { chatApp: "TEAMS", url: "wwexample2m", errorExists: true }
        ];
        assert.equal(true, errorsExist(array, type));
    });
});

describe('checkForDuplicatesAndEmpty', () => {
    const arr = [];
    const obj = { chatApp: "TEAMS", url: "", errorExists: true };

    it('should return true when the object passed in has an empty url', () => {
        assert.equal(true, checkForDuplicatesAndEmpty(arr, obj));
    });

    it('should return false when the object being compared is not in the array', () => {
        const newObj = { ...obj, url: "http://www.example.com", errorExists: false };

        assert.equal(false, checkForDuplicatesAndEmpty(arr, newObj));
    });

    it('should return true when the object being compared is in the array', () => {
        const obj = { chatApp: "TEAMS", url: "http://www.example.com", errorExists: false };
        arr.push(
            { chatApp: "TEAMS", url: "http://www.example2.com", errorExists: false },
            { chatApp: "TEAMS", url: "http://www.example.com", errorExists: false }
        );

        assert.equal(true, checkForDuplicatesAndEmpty(arr, obj));
    });
});

describe('validateUrl', () => {
    it('should return false when url is empty', () => {
        assert.equal(false, validateUrl(""));
    });

    it('should return false when url is in an invalid format', () => {
        assert.equal(false, validateUrl("incorrecturl"));
    });

    it('should return true when url is in a valid format', () => {
        assert.equal(true, validateUrl("http://www.example.com"))
    });
});

describe('populateSplunkWebHooks', () => {
    const webhooksArray = [{ chatApp: "TEAMS", url: "http://www.example.com" }];

    it('should return the original webhooks array if the urlArray passed in is empty', () => {
        assert.equal(webhooksArray, populateSplunkWebHooks([], webhooksArray));
    });

    it('should push the values of the urlArray into the webhooksArray when the urlArray is not empty', () => {
        const urlArray = [{ chatApp: "ROCKET_CHAT", url: "http://www.rocket.url" }];

        assert.deepEqual(
            [
                { chatApp: "TEAMS", url: "http://www.example.com" },
                { chatApp: "ROCKET_CHAT", url: "http://www.rocket.url" }
            ],
            populateSplunkWebHooks(urlArray, webhooksArray)
        );
    });
});
