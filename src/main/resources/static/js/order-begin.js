function getCounter(element) {
    return element.parentElement.getElementsByClassName("counter")[0];
}
function getValue(element) {
    return parseInt(element.innerHTML);
}
function getMaxValue(element) {
    return parseInt(element.parentElement.getElementsByClassName("max-counter")[0].innerHTML);
}
function add(element) {
    var counter = getCounter(element);
    const value = getValue(counter);
    const maxValue = getMaxValue(element);
    if (value < maxValue) {
        counter.innerHTML = value + 1;
        updatePrice();
    }
}
function sub(element) {
    var counter = getCounter(element);
    const value = getValue(counter);
    const maxValue = getMaxValue(element);
    if (value > 1) {
        counter.innerHTML = value - 1;
        updatePrice();
    }
}
function updatePrice() {
    var products = document.getElementsByClassName("content")[0].getElementsByClassName("product");
    var totalPrice = 0;
    var counts = [];
    for (let product of products) {
        var price = product.getElementsByClassName("price")[0];
        var counter = product.getElementsByClassName("counter")[0];
        var count = parseInt(counter.innerHTML);
        totalPrice += parseInt(price.innerHTML) * count;
        counts.push(count);
    }
    document.getElementById("total_price").innerHTML = totalPrice;
    var next = document.getElementById("next");
    var indexOfParams = next.href.indexOf('?');

    if (indexOfParams !== -1) {
        next.href = next.href.substring(0, indexOfParams);
    }
    next.href += '?counts=' + counts.join('+');
}