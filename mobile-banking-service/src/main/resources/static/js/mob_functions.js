function submitWithValue(value) {
    document.querySelector('#amount-field').value = value;
    document.querySelector('#amount-form').submit();
}

function addNumberToPass(newValue) {
    var input = document.querySelector('#pin-field');
    var currentValue = input.value;
    var newValue = currentValue + newValue.toString();
    input.value = newValue;

    if(input.value.length >=4) {
        window.location.href = '/mobile/accounts';
    }
}

