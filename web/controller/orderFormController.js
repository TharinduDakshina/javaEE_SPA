{
    loadOrderId();
}

/*function loadOrderId() {
    if ($("#orderId").val() == "") {
        $("#orderId").val("0-001");
    } else {
        var tempID = parseInt($("#orderId").val().split("-")[1]);
        tempID++;
        if (tempID <=9){
            tempID="0-00"+tempID;
        }else if (tempID <= 99){
            tempID="0-0"+tempID;
        }else {
            tempID="0-"+tempID;
        }

        $("#orderId").val(tempID);
    }
}*/
function loadOrderId() {
    if ($("#orderId").val() == "") {
        $("#orderId").val("0-001");
    } else {
        var value = parseInt($("#orderId").val().split("-")[1]);
        value++;
        if (value <=9){
            value="0-00"+value;
        }else if (value <= 99){
            value="0-0"+value;
        }else {
            value="0-"+value;
        }

        $("#orderId").val(value);
    }
}

$("#btnPurchase").click(function () {
    if ($("#date").val() == "") {
        $("#date").css('border', '2px solid red');
    } else {
        loadOrderId();
        $("#orderFormCustomerName,#orderFormCustomerAddress,#orderFormCustomerTp,#date").val("");
        $("#orderFormTableBody").empty();
    }

});

function loadItemId() {
    $("#orderFormItemId").empty();
    itemDB.forEach(function (e) {
        $("#orderFormItemId").append($("<option></option>").attr("value", e).text(e.getItemId()));
    });
}

function loadCustomerId() {

    $("#orderFormCstId").empty();
    /*$("#orderFormCstId").append($("<option></option>").attr("value",e).text(--select Id--));*/
    customerDB.forEach(function (e) {
        $("#orderFormCstId").append($("<option></option>").attr("value", e).text(e.getCustomerId()));
    });

}

$("#orderFormCstId").click(function () {
    var selectedId = $("#orderFormCstId option:selected").text();
    setCustomerData(selectedId);
});

function setCustomerData(id) {
    for (var i = 0; i < customerDB.length; i++) {
        if (customerDB[i].getCustomerId() == id) {
            $("#orderFormCustomerName").val(customerDB[i].getCustomerName());
            $("#orderFormCustomerAddress").val(customerDB[i].getCustomerAddress());
            $("#orderFormCustomerTp").val(customerDB[i].getCustomerTp());
        }
    }
}

$("#orderFormItemId").click(function () {
    var selectedId = $("#orderFormItemId option:selected").text();
    setItemData(selectedId);
});

function setItemData(id) {
    for (var j = 0; j < itemDB.length; j++) {
        if (itemDB[j].getItemId() == id) {
            $("#orderFormItemName").val(itemDB[j].getItemName());
            $("#orderFormQty").val(itemDB[j].getItemQty());
            $("#orderFormPrice").val(itemDB[j].getItemPrice());
        }
    }
}


$("#btnAddItem").click(function () {
    countTotal();
    if ($("#orderFormItemId option:selected").text() == "" || $("#orderFormCstId option:selected").text() == "") {
        alert("Please select the Customer Id and Item Id");
        clearOrderItem();
    } else {
        updateItemDatabase();
        saveOrder();
        loadTable();

        $("#orderFormTableBody>tr>td>button").click(function () {
            let text = "Confirm the remove of this item..!"
            if (confirm(text)) {
                $(this).closest('tr').remove();
                let orderId = $("#orderId").val();
                let id = $(this).closest('tr').children(":eq(0)").text();
                let qty = parseInt($(this).closest('tr').children(":eq(3)").text());
                let total =parseInt($(this).closest('tr').children(":eq(4)").text());
                removeItem(orderId, id, qty,total);
            }

        });
    }

});

function removeItem(orderId, id, qty,total) {
    /*update itemDB*/
    for (let i = 0; i < itemDB.length; i++) {
        if (id == itemDB[i].getItemId()) {
            let preQty = itemDB[i].getItemQty();
            preQty += qty;
            itemDB[i].setItemQty(preQty);
        }
    }

    /*update orderDB*/
    for (let j = 0; j < orderDB.length; j++) {
        if (id == orderDB[j].getOrderItemId() && orderId == orderDB[j].getOrderId()) {
            orderDB.splice(j, 1);
        }
    }

    /*updateTotal*/

    let currentTotal =parseInt($('#total').text());
    console.log(currentTotal);
    let newTotal=currentTotal-total;
    console.log(newTotal);
    $('#total').text(newTotal+'.00/=');
    countTotal();

}

function countTotal() {
    var total;
    var displayTotal = parseInt($("#total").text());
    console.log(displayTotal);
    if (displayTotal == 0) {
        total = (parseInt($("#orderQty").val())) * (parseInt($("#orderFormPrice").val()));
        console.log(total);
        $("#total").text(total + ".00 /=");
    } else {
        var sum = displayTotal + (parseInt($("#orderQty").val())) * (parseInt($("#orderFormPrice").val()));
        $("#total").text(sum + ".00 /=");
    }

    displayTotal = parseInt($("#total").text());

    if (displayTotal > 100 || displayTotal < 1000) {
        $("#txtDiscount").val("5%");
        var subTotal = displayTotal - ((displayTotal * 5) / 100);
            $("#subTotal").text(subTotal + " /=");
    } else if (displayTotal > 1000) {
        $("#txtDiscount").val("10%");
        var subTotal = displayTotal - ((displayTotal * 10) / 100);
        $("#subTotal").text(subTotal + " /=");
    }
}

function saveOrder() {
    var orderID = $("#orderId").val();
    var date = $("#date").val();
    var customerId = $("#orderFormCstId option:selected").text();
    var itemId = $("#orderFormItemId option:selected").text();
    var itemName = $("#orderFormItemName").val();
    var itemPrice = parseInt($("#orderFormPrice").val());
    var qty = parseInt($("#orderQty").val());
    var total = itemPrice * qty;

    var orderDetails = new orderDTO();
    orderDetails.setOrderId(orderID);
    orderDetails.setOrderDate(date);
    orderDetails.setOrderCustomerId(customerId);
    orderDetails.setOrderItemId(itemId);
    orderDetails.setOrderItemName(itemName);
    orderDetails.setOrderItemPrice(itemPrice);
    orderDetails.setOrderQty(qty);
    orderDetails.setTotal(total);

    var checked = false;

    function idExits() {
        for (var i = 0; i < orderDB.length; i++) {
            if (itemId == orderDB[i].getOrderItemId()) {
                orderDB[i].setOrderQty(orderDB[i].getOrderQty() + qty);
                orderDB[i].setTotal(orderDB[i].getTotal() + total);
                return true;
            } else {

            }
        }
        return false;
    }

    if (orderDB.length == 0) {
        checked = false;
    } else {
        checked = idExits();
    }

    if (checked) {
        clearOrderItem();
    } else {
        orderDB.push(orderDetails);
        clearOrderItem();
    }
}

function loadTable() {
    $("#orderFormTableBody").empty();
    orderDB.forEach(function (a) {
        let orderRow = `<tr><td>${a.getOrderItemId()}</td><td>${a.getOrderItemName()}</td><td>${a.getOrderItemPrice()}</td><td>${a.getOrderQty()}</td><td>${a.getTotal()}</td><td><button id="delete" type="button" class="btn btn-danger ">Remove</button></td></tr>`;
        $("#orderFormTableBody").append(orderRow);
    });
}

function clearOrderItem() {
    $("#orderFormItemName,#orderQty,#orderFormPrice,#orderFormQty").val("");
    $("#btnAddItem").attr("disabled", true);
    validateOrderForm();
}

function updateItemDatabase() {
    var itemId = $("#orderFormItemId option:selected").text();
    var qty = parseInt($("#orderQty").val());
    for (let i = 0; i < itemDB.length; i++) {
        if (itemId == itemDB[i].getItemId()) {
            var x = parseInt(itemDB[i].getItemQty());
            x -= qty;
            itemDB[i].setItemQty(x);
        }
    }
}

/*========================= validation =====================================*/

let regxQty = /^[0-9]{1,3}$/;
let regxCash = /^[0-9](.){1,6}$/;

$("#btnAddItem").attr("disabled", true);
$("#btnPurchase").attr("disabled", true);

$("#orderQty,#txtCash").on('keyup', function () {
    validateOrderForm();
});

function validateOrderForm() {
    var qty = $("#orderQty").val();

    if (regxQty.test(qty)) {
        $("#orderQty").css('border', '2px solid green');
    } else {
        $("#orderQty").css('border', '2px solid red');
        $("#btnAddItem").attr("disabled", true);
    }

    var cash = $("#txtCash").val();
    if (regxCash.test(cash)) {
        $("#txtCash").css('border', '2px solid green');
    } else {
        $("#txtCash").css('border', '2px solid red');
        $("#btnAddItem").attr("disabled", true);
    }
}

$("#orderQty").on('keyup', function (e) {
    if (e.key == "Enter") {
        if (parseInt($("#orderQty").val()) > parseInt($("#orderFormQty").val())) {
            alert("Quantity is invalid");
            /*swal("Good job!", "You clicked the button!", "success");*/
        } else {
            checkValidation();
        }
    }
});

$("#txtCash").on('keyup', function (e) {
    if (e.key == "Enter") {
        if (parseInt($("#total").text()) <= parseInt($("#txtCash").val())) {
            setBalance();
            checkValidation();
        } else {
            alert("Wrong Amount..! ");
        }
    }
});

function checkValidation() {
    var qty = $("#orderQty").val();
    if (regxQty.test(qty)) {
        $("#btnAddItem").attr("disabled", false);
    } else {
        $("#orderQty").focus();
    }

    var cash = $("#txtCash").val();
    if (regxCash.test(cash)) {
        $("#btnPurchase").attr("disabled", false);
    } else {
        $("#txtCash").focus();
    }
}

function setBalance() {
    var balance = parseInt($("#txtCash").val()) - parseInt($("#subTotal").text());
    $("#txtBalance").val(balance + ".00");
}