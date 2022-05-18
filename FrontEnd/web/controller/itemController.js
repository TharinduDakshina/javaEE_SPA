$("#saveItem").prop("disabled", true);

$("#saveItem").click(function () {
    saveItem();
    clearItemAll();
    loadAllItem();
});

function saveItem() {
    //gather Item information
    let itemId = $("#itemId").val();
    let itemName = $("#itemName").val();
    let itemQTY = $("#itemQty").val();
    let itemPrice = $("#itemPrice").val();

    //create Object
    var item= new itemDTO();
    item.setItemId(itemId);
    item.setItemName(itemName);
    item.setItemQty(itemQTY);
    item.setItemPrice(itemPrice);


    itemDB.push(item);
}

function checkAlreadyExitsItem() {
    for (let i = 0; i < itemDB.length; i++) {
        if ($("#itemId").val() == itemDB[i].getItemId()) {
            return -1;
        }
    }
    return 0;
}

function clearItemAll() {
    $('#itemId,#itemName,#itemQty,#itemPrice').val("");
    $('#itemId,#itemName,#itemQty,#itemPrice').css('border', '2px solid #ced4da');
    $('#itemId').focus();
    $("#saveItem").attr('disabled', true);
    loadAllItem();
    $("#errorItemId,#errorItemName,#errorQty,#errorPrice").text("");
}

function loadAllItem() {
    $("#itemTableBody").empty();
    for (var i of itemDB) {
        /*create a html row*/
        let itemRow = `<tr><td>${i.getItemId()}</td><td>${i.getItemName()}</td><td>${i.getItemQty()}</td><td>${i.getItemPrice()}</td></tr>`;
        /*select the table body and append the row */
        $("#itemTableBody").append(itemRow);
    }
    $("#itemTableBody>tr").click(function () {
        let id = $(this).children(":eq(0)").text();
        let name = $(this).children(":eq(1)").text();
        let QTY = $(this).children(":eq(2)").text();
        let iPrice = $(this).children(":eq(3)").text();

        $("#itemId").val(id);
        $("#itemName").val(name);
        $("#itemQty").val(QTY);
        $("#itemPrice").val(iPrice);
    });
}

$("#btnItemSearch").click(function () {
    var searchID = $("#txtItemSearch").val();

    var response = searchItem(searchID);
    if (response) {
        $("#itemId").val(response.getItemId());
        $("#itemName").val(response.getItemName());
        $("#itemQty").val(response.getItemQty());
        $("#itemPrice").val(response.getItemPrice());
    } else {
        clearItemAll();
        alert("No Such a Item");
    }
});

function searchItem(id) {
    for (let i = 0; i < itemDB.length; i++) {
        if (itemDB[i].getItemId() == id) {
            return itemDB[i];
        }
    }
}

$("#deleteItem").click(function () {
    var deleteId = $("#itemId").val();
    deleteItem(deleteId);
    clearItemAll();
});

function deleteItem(id) {
    var index = -1;
    for (var i = 0; i < itemDB.length; i++) {
        if (itemDB[i].getItemId() == id) {
            index = i;
            alert(itemDB[i].getItemId() + " Deleted");
        }
    }

    itemDB.splice(index, 1);
}

$("#btnUpdate").click(function (){
    var updateItemId=$("#itemId").val();
    var updateItemName=$("#itemName").val();
    var updateItemQty=$("#itemQty").val();
    var updateItemPrice=$("#itemPrice").val();
    updateItem(updateItemId,updateItemName,updateItemQty,updateItemPrice);
    clearItemAll();
});

function updateItem(id,name,qty,price){
    for (let j=0;j<itemDB.length;j++){
        if (id==itemDB[j].getItemId()){
            itemDB[j].setItemName(name);
            itemDB[j].setItemQty(qty);
            itemDB[j].setItemPrice(price);

            alert("Successfully Updated.");
        }
    }
}

/*------------------------Validation------------------------------*/

let regxId = /^(I00-)[0-9]{3,4}$/;
let regxItemName = /^[A-z ]{3,20}$/;
let regxItemQty = /^[0-9]{1,3}$/;
let regxItemPrice = /^[0-9](.){1,6}$/;

$('#itemId,#itemName,#itemQty,#itemPrice').on('keydown', function (eventOb) {
    if (eventOb.key == "Tab") {
        eventOb.preventDefault();
    }
});

$('#itemId,#itemName,#itemQty,#itemPrice').on('blur', function () {
    formValidItem();
});

function formValidItem() {
    var itemId = $("#itemId").val();
    let result = checkAlreadyExitsItem();
    if (result == -1) {
        $("#itemId").css('border', '2px solid red');
        $("#errorItemId").text("This id was already exits.");
        return false;
    }
    $("#itemId").css('border', '2px solid green');
    $("#errorItemId").text("");
    if (regxId.test(itemId)) {
        var itemName = $("#itemName").val();
        if (regxItemName.test(itemName)) {
            $("#itemName").css('border', '2px solid green');
            $("#errorItemName").text("");
            var itemQty = $("#itemQty").val();
            if (regxItemQty.test(itemQty)) {
                var itemPrice = $("#itemPrice").val();
                var resp = regxItemPrice.test(itemPrice);
                $("#itemQty").css('border', '2px solid green');
                $("#errorQty").text("");
                if (resp) {
                    $("#itemPrice").css('border', '2px solid green');
                    $("#errorPrice").text("");
                    return true;
                } else {
                    $("#itemPrice").css('border', '2px solid red');
                    $("#errorPrice").text("Item Price is a required field : Pattern 100.00 or 100");
                    return false;
                }
            } else {
                $("#itemQty").css('border', '2px solid red');
                $("#errorQty").text("Item Qty is a required field : Mimum 7");
                return false;
            }
        } else {
            $("#itemName").css('border', '2px solid red');
            $("#errorItemName").text("Item Name is a required field : Mimimum 5, Max 20, Spaces Allowed");
            return false;
        }
    } else {
        $("#itemId").css('border', '2px solid red');
        $("#errorItemId").text("Item ID is a required field : Pattern C00-000");
        return false;
    }
}

$("#itemId").on('keyup', function (eventOb) {
    setItemButton();
    if (eventOb.key == "Enter") {
        checkIfValidItem();
    }
});

$("#itemName").on('keyup', function (eventOb) {
    setItemButton();
    if (eventOb.key == "Enter") {
        checkIfValidItem();
    }
});

$("#itemQty").on('keyup', function (eventOb) {
    setItemButton();
    if (eventOb.key == "Enter") {
        checkIfValidItem();
    }
});

$("#itemPrice").on('keyup', function (eventOb) {
    setItemButton();
    if (eventOb.key == "Enter") {
        checkIfValidItem();
    }
});

function checkIfValidItem() {
    var itemId = $("#itemId").val();
    if (regxId.test(itemId)) {
        $("#itemName").focus();
        var itemName = $("#itemName").val();
        if (regxItemName.test(itemName)) {
            $("#itemQty").focus();
            var itemQty = $("#itemQty").val();
            if (regxItemQty.test(itemQty)) {
                $("#itemPrice").focus();
                var itemPrice = $("#itemPrice").val();
                var resp = regxItemPrice.test(itemPrice);
                if (resp) {
                    let res = confirm("Do you really need to add this Item..?");
                    if (res) {
                        saveItem();
                        clearItemAll();
                    }
                } else {
                    $("#itemPrice").focus();
                }
            } else {
                $("#itemQty").focus();
            }
        } else {
            $("#itemName").focus();
        }
    } else {
        $("#itemId").focus();
    }
}

function setItemButton() {
    let b = formValidItem();
    if (b) {
        $("#saveItem").attr('disabled', false);
    } else {
        $("#saveItem").attr('disabled', true);
    }
}