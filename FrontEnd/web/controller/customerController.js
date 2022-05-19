$("#saveCustomer").prop("disabled", true);


$("#saveCustomer").click(function () {
    saveCustomer();
    clearAll();
    // $("#customerTableBody").empty();
    console.log("Save customer");
    loadAllCustomers();
});

function saveCustomer() {

    var cusObject = {
        id: $("#cstId").val(),
        name: $("#cstName").val(),
        address: $("#cstAddress").val(),
        salary: $("#cstSalary").val()
    }

    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/customer",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(cusObject),
        success: function (res) {
            if (res.status == 200) {
                alert(res.message);
            } else {
                alert(res.data);
            }
        }, error: function (ob, textStatus, error) {
            alert(textStatus);
        }
    });

}


function loadAllCustomers() {
    $("#customerTableBody").empty();
    //  console.log("empty-->" + empty)

    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/customer?option=GETALL",
        method: "GET",
        success: function (res) {
            // console.log("111111111111111111111111111");
            for (const customer of res.data) {
                let row = `<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`;
                $("#customerTableBody").append(row);
            }
            // console.log("---------------------------------------------------------------------");
            bindClickEvent();
        }
    });
}

function bindClickEvent() {
    $("#customerTableBody>tr").click(function () {
        //Get values from the selected row
        let id = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let address = $(this).children().eq(2).text();
        let salary = $(this).children().eq(3).text();

        //Set values to the text-fields
        $("#cstId").val(id);
        $("#cstName").val(name);
        $("#cstAddress").val(address);
        $("#cstSalary").val(salary);
    });
}

function clearAll() {
    $('#cstId,#cstName,#cstAddress,#cstSalary').val("");
    $('#cstId,#cstName,#cstAddress,#cstSalary').css('border', '2px solid #ced4da');
    $('#cstId').focus();
    $("#saveCustomer").attr('disabled', true);
    //loadAllCustomers();
    $("#errorId,#errorName,#errorAddress,#errorTp").text("");
}


$("#btnSearch").click(function () {
    var searchID = $("#txtCustomerSearch").val();

    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/customer?option=SEARCH&searchId=" + searchID,
        method: "GET",
        success: function (res) {
            if (res.status == 200) {
                $("#cstId").val(res.data.id);
                $("#cstName").val(res.data.name);
                $("#cstAddress").val(res.data.address);
                $("#cstSalary").val(res.data.salary);
            } else if (res.status == 404) {
                alert(res.message);
            } else {
                alert(res.data);
            }
        }, error: function (ob, textStatus, error) {
            console.log(error);
        }

    });

});

function searchCustomer(id) {
    for (let i = 0; i < customerDB.length; i++) {
        if (customerDB[i].getCustomerId() == id) {
            return customerDB[i];
        }
    }
}

$("#deleteCustomer").click(function () {
    var deleteId = $("#cstId").val();
    deleteCustomer(deleteId);
    clearAll();
});

function deleteCustomer(id) {
    var index = -1;

    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/customer?cstId=" + id,
        method: "delete",
        success: function (res) {
            if (res.status == 200) {
                alert(res.message);
                loadAllCustomers();
            } else if (res.status == 400) {
                alert(res.message);
            } else {
                alert(res.data);
            }
        }, error: function (ob, textMessage, error) {
            console.log(ob);
            console.log(textMessage);
            console.log(error);
        }
    });
    /*for (var i = 0; i < customerDB.length; i++) {
        if (customerDB[i].getCustomerId() == id) {
            index = i;
            alert(customerDB[i].getCustomerId() + " Deleted");
        }
    }

    customerDB.splice(index, 1);*/
}

$("#btnUpdateCustomer").click(function () {
    var cusObject = {
        id: $("#cstId").val(),
        name: $("#cstName").val(),
        address: $("#cstAddress").val(),
        salary: $("#cstSalary").val()
    }
    updateCustomer(cusObject);
    clearAll();
});

function updateCustomer(cusObject) {

    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/customer",
        method: "put",
        contentType: "application/json",
        data: JSON.stringify(cusObject),
        success: function (res) {
            if (res.status == 200) {
                alert(res.message);
                loadAllCustomers();
            } else if (res.status == 400) {
                alert(res.message);
            } else {
                alert(res.data);
                console.log(res.data);
            }
        }
    });

    /* for (let i = 0; i < customerDB.length; i++) {
         if (id == customerDB[i].getCustomerId()) {
             customerDB[i].setCustomerName(name);
             customerDB[i].setCustomerAddress(address);
             customerDB[i].setCustomerTp(tp);

             alert("Successfully Update ");
         }
     }*/
}

/*---------------------Validation--------------------------*/

let regxCstId = /^(C00-)[0-9]{3,4}$/;

let regxCstName = /^[A-z ]{3,20}$/;
let regxCstAddress = /^[A-z 0-9/,]{3,50}$/;
let regxSalary = /^[0-9]{3,10}$/;
$('#cstId,#cstName,#cstAddress,#cstTp').on('keydown', function (eventOb) {
    if (eventOb.key == "Tab") {
        eventOb.preventDefault();
    }
});

$('#cstId,#cstName,#cstAddress,#cstTp').on('blur', function () {
    formValid();
});

function checkAlreadyExits(cusID) {

    var index = 0;
    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/customer?option=SEARCH&searchId=" + cusID,
        method: "GET",
        success: function (res) {
            //console.log("11111111111111111111111111");
            if (res.status == 200) {
                //console.log("22222222222222222222");
                //index=-1;
            } else if (res.status == 404) {
                //console.log("3333333333333333333333333");
                //index=0;
            } else {
                //console.log("44444444444444444444444444444");
                //index=0;
            }
        }, error: function (ob, textStatus, error) {
            //console.log("555555555555555555555555555");
            //console.log(error);
            index = 0;
        }
    });
    return index;

    /*for (let i = 0; i < customerDB.length; i++) {
        if ($("#cstId").val() == customerDB[i].getCustomerId()) {
            return -1;
        }
    }*/
}

function formValid() {
    var cusID = $("#cstId").val();
    let result = checkAlreadyExits(cusID);
    console.log(result);
    if (result == -1) {
        console.log(result)
        $("#cstId").css('border', '2px solid red');
        $("#errorId").text("This id was already exits.");
        return false;
    }
    $("#cstId").css('border', '2px solid green');
    $("#errorId").text("");
    if (regxCstId.test(cusID)) {
        var cusName = $("#cstName").val();
        if (regxCstName.test(cusName)) {
            $("#cstName").css('border', '2px solid green');
            $("#errorName").text("");
            var cusAddress = $("#cstAddress").val();
            if (regxCstAddress.test(cusAddress)) {
                var cusTp = $("#cstSalary").val();
                var resp = regxSalary.test(cusTp);
                $("#cstAddress").css('border', '2px solid green');
                $("#errorAddress").text("");
                if (resp) {
                    $("#cstSalary").css('border', '2px solid green');
                    $("#errorTp").text("");
                    return true;
                } else {
                    $("#cstSalary").css('border', '2px solid red');
                    $("#errorTp").text("Cus Salary is a required field : Pattern 100.00 or 100");
                    return false;
                }
            } else {
                $("#cstAddress").css('border', '2px solid red');
                $("#errorAddress").text("Cus Address is a required field : Mimum 7");
                return false;
            }
        } else {
            $("#cstName").css('border', '2px solid red');
            $("#errorName").text("Cus Name is a required field : Mimimum 5, Max 20, Spaces Allowed");
            return false;
        }
    } else {
        $("#cstId").css('border', '2px solid red');
        $("#errorId").text("Cus ID is a required field : Pattern C00-000");
        return false;
    }
}

$("#cstId").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});

$("#cstName").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});

$("#cstAddress").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});

$("#cstSalary").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});

function checkIfValid() {
    var cusID = $("#cstId").val();
    if (regxCstId.test(cusID)) {
        $("#cstName").focus();
        var cusName = $("#cstName").val();
        if (regxCstName.test(cusName)) {
            $("#cstAddress").focus();
            var cusAddress = $("#cstAddress").val();
            if (regxCstAddress.test(cusAddress)) {
                $("#cstSalary").focus();
                var cstTp = $("#cstSalary").val();
                var resp = regxSalary.test(cstTp);
                if (resp) {
                    let res = confirm("Do you really need to add this Customer..?");
                    if (res) {
                        saveCustomer();
                        clearAll();
                    }
                } else {
                    $("#cstSalary").focus();
                }
            } else {
                $("#cstAddress").focus();
            }
        } else {
            $("#cstName").focus();
        }
    } else {
        $("#cstId").focus();
    }
}

function setButton() {
    let b = formValid();
    if (b) {
        $("#saveCustomer").attr('disabled', false);
    } else {
        $("#saveCustomer").attr('disabled', true);
    }
}

$('#saveCustomer').click(function () {
    checkIfValid();
});