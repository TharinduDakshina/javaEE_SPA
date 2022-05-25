$("#homePageRow").css("display", "block");
$("#customer").css("display", "none");
$("#item").css("display", "none");
$("#placeOrder").css("display", "none");
loadAmounts();

$("#homeButton").click(function () {
    $("#homePageRow").css("display", "block");
    $("#customer").css("display", "none");
    $("#item").css("display", "none");
    $("#placeOrder").css("display", "none");
    loadAmounts();
});

$("#customerButton").click(function () {
    $("#homePageRow").css("display", "none");
    $("#customer").css("display", "block");
    $("#item").css("display", "none");
    $("#placeOrder").css("display", "none");
    console.log("loadForm");
    loadAllCustomers();
});

$("#itemButton").click(function () {
    $("#homePageRow").css("display", "none");
    $("#customer").css("display", "none");
    $("#item").css("display", "block");
    $("#placeOrder").css("display", "none");
    loadAllItem();
});

$("#placeOrderButton").click(function () {
    $("#homePageRow").css("display", "none");
    $("#customer").css("display", "none");
    $("#item").css("display", "none");
    $("#placeOrder").css("display", "block");
    loadItemId();
    loadCustomerId();
});

function loadAmounts(){
    $.ajax({
        url:"http://localhost:8080/SPA_BackEnd/order?option=GetOrderId",
        method:"GET",
        success:function (res){
            if (res.status==200){
                console.log(res.message);
                console.log(res.data)
                $("#homePageOrderAmount").text(res.message);
            }else if (res.status==404){
                $("#orderId").val(res.data);
            }else {
                console.log(res.data);
            }
        }
    });

    $.ajax({
        url: "http://localhost:8080/SPA_BackEnd/customer?option=GetCustomerID",
        method: "GET",
        success:function (res){
            if (res.status==200){
                console.log(res.data.length);
                $("#homePageCustomers").text(res.data.length)
            }
        }
    });

    $.ajax({
        url:"http://localhost:8080/SPA_BackEnd/item?option=GETALL",
        method:"GET",
        success:function (res){
            $("#homePageAmount").text(res.data.length);
        }
    });
}