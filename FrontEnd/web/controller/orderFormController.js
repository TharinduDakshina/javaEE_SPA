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

    $.ajax({
        url:"http://localhost:8080/SPA_BackEnd/item?option=GetCustomerID",
        method:"GET",
        success:function (res){
            if (res.status == 200) {
                for (const itemIds of res.data) {
                    $("#orderFormItemId").append($("<option></option>").attr("value", itemIds).text(itemIds.id));
                }
            }
        }
    });


    /*itemDB.forEach(function (e) {
        $("#orderFormItemId").append($("<option></option>").attr("value", e).text(e.getItemId()));
    });*/
}

function loadCustomerId() {

    $("#orderFormCstId").empty();
    /*$("#orderFormCstId").append($("<option></option>").attr("value",e).text(--select Id--));*/
    $.ajax({
        url:"http://localhost:8080/SPA_BackEnd/customer?option=GetCustomerID",
        method:"GET",
        success:function (res){
            if (res.status == 200) {
                for (const customerId of res.data) {
                    $("#orderFormCstId").append($("<option></option>").attr("value", customerId).text(customerId.id));
                }
            }
        }
    });

    /*customerDB.forEach(function (e) {
        $("#orderFormCstId").append($("<option></option>").attr("value", e).text(e.getCustomerId()));
    });*/

}

$("#orderFormCstId").click(function () {
    var selectedId = $("#orderFormCstId option:selected").text();
    setCustomerData(selectedId);
});

function setCustomerData(id) {

    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/customer?option=SEARCH&searchId=" + id,
        method: "GET",
        success: function (res) {
            if (res.status == 200) {
                $("#orderFormCustomerName").val(res.data.name);
                $("#orderFormCustomerAddress").val(res.data.address);
                $("#orderFormCustomerTp").val(res.data.salary);
            } else if (res.status == 404) {
                alert(res.message);
            } else {
                alert(res.data);
            }
        }, error: function (ob, textStatus, error) {
            console.log(error);
        }

    });

}

$("#orderFormItemId").click(function () {
    var selectedId = $("#orderFormItemId option:selected").text();
    setItemData(selectedId);
});

function setItemData(id) {

    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/item?option=SEARCH&searchId=" + id,
        method: "GET",
        success: function (res) {
            if (res.status == 200) {
                $("#orderFormItemName").val(res.data.description);
                $("#orderFormQty").val(res.data.qty);
                $("#orderFormPrice").val(res.data.unitePrice);
            } else if (res.status == 404) {
                alert(res.message);
            } else {
                alert(res.data);
            }
        }, error: function (ob, textStatus, error) {
            console.log(error);
        }

    });

}


$("#btnAddItem").click(function () {
    countTotal();
    if ($("#orderFormItemId option:selected").text() == "" || $("#orderFormCstId option:selected").text() == "") {
        alert("Please select the Customer Id and Item Id");
        clearOrderItem();
    } else {
        updateItemDatabase();//-----------------------------------------
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

/*function getPreItemQTY(id) {
    $.ajax({
        url:"http://localhost:8080/SPA_BackEnd/item?option=SEARCH&searchId="+id,
        method:"GET",
        success:function (res){
            if (res.status==200){
                var w=parseInt(res.data.qty)
                console.log("AAAAAAAA____>"+w);
                console.log(typeof w);

                return res.data.qty;
            }else if (res.status==404){
                console.log(res.message);
                return -1;
            }else {
                console.log(res.data);
                return -1;
            }
        }

    });
}*/

function removeItem(orderId, id, qty,total) {
    /*update itemDB*/

    getPreItemQTY(id);

    function getPreItemQTY(id) {
        $.ajax({
            url:"http://localhost:8080/SPA_BackEnd/item?option=SEARCH&searchId="+id,
            method:"GET",
            success:function (res){
                if (res.status==200){
                    var w=parseInt(res.data.qty)

                    var updateObject={
                        itemId:id,
                        updateQty:w+qty,
                    }
                    $.ajax({
                        url:"http://localhost:8080/SPA_BackEnd/item?option=updateQTY",
                        method:"PUT",
                        contentType: "application/json",
                        data: JSON.stringify(updateObject),
                        success:function (resp){
                            if (resp.status==200){
                                console.log(resp.message);
                            }else  if (resp.status==400){
                                console.log(resp.message);
                            }else {
                                console.log(resp.data);
                                console.log(resp.message);
                            }
                        }
                    });

                }else if (res.status==404){
                    console.log(res.message);

                }else {
                    console.log(res.data);

                }
            }

        });
    }


    /*console.log("X-->"+x);
    console.log(typeof x);*/
   /* if (x != -1){

        var updateObject={
            itemId:id,
            updateQty:x+qty,
        }
        $.ajax({
            url:"http://localhost:8080/SPA_BackEnd/item?option=updateQTY",
            method:"PUT",
            contentType: "application/json",
            data: JSON.stringify(updateObject),
            success:function (res){
                if (res.status==200){
                    console.log(res.message);
                }else  if (res.status==400){
                    console.log(res.message);
                }else {
                    console.log(res.data);
                    console.log(res.message);
                }
            }
        });
    }else {
        console.log("item code not found or Exception error");
    }*/



    /*for (let i = 0; i < itemDB.length; i++) {
        if (id == itemDB[i].getItemId()) {
            let preQty = itemDB[i].getItemQty();
            preQty += qty;
            itemDB[i].setItemQty(preQty);
        }
    }*/

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
   /* var itemId = ;*/
    var preItemQty=$("#orderFormQty").val();
    var orderQry=$("#orderQty").val();
    console.log(preItemQty);

    var itemQtyUpdateObject={
        itemId:$("#orderFormItemId option:selected").text(),
        updateQty:preItemQty-orderQry,
    }

    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/item?option=updateQTY",
        method: "put",
        contentType: "application/json",
        data: JSON.stringify(itemQtyUpdateObject),
        success: function (res) {
            if (res.status==200){
                console.log(res.message);
            }else  if (res.status==400){
                console.log(res.message);
            }else {
                console.log(res.data);
                console.log(res.message);
            }
        }
    });

   /* var qty = parseInt($("#orderQty").val());
    for (let i = 0; i < itemDB.length; i++) {
        if (itemId == itemDB[i].getItemId()) {
            var x = parseInt(itemDB[i].getItemQty());
            x -= qty;
            itemDB[i].setItemQty(x);
        }
    }*/
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