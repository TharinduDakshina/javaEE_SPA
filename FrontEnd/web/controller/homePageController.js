$("#homePageRow").css("display", "block");
$("#customer").css("display", "none");
$("#item").css("display", "none");
$("#placeOrder").css("display", "none");

$("#homeButton").click(function () {
    $("#homePageRow").css("display", "block");
    $("#customer").css("display", "none");
    $("#item").css("display", "none");
    $("#placeOrder").css("display", "none");
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

