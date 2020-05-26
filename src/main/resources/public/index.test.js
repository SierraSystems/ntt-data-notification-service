const assert = chai.assert;

describe('isEmpty', function() {
  it('should return true when array is empty', function() {
    assert.equal(true, isEmpty([]));
  });
});
