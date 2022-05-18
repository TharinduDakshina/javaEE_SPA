$("#homePageRow").css("display", "block");
$("#customer").css("display", "none");
$("#item").css("display", "none");
$("#placeOrder").css("display", "none");

$("#homeButton").click(function () {
    $("#homePageRow").css("display", "block");
    $("#customer").css("display", "none");
    $("#item").css("display", "none");
    $("#placeOrder").css("display", "none");

   /* $("#homePageCustomers").text(customerDB.length);
    $("#homePageAmount").text(itemDB.length);*/
});

$("#customerButton").click(function () {
    $("#homePageRow").css("display", "none");
    $("#customer").css("display", "block");
    $("#item").css("display", "none");
    $("#placeOrder").css("display", "none");
});

$("#itemButton").click(function () {
    $("#homePageRow").css("display", "none");
    $("#customer").css("display", "none");
    $("#item").css("display", "block");
    $("#placeOrder").css("display", "none");
});

$("#placeOrderButton").click(function () {
    $("#homePageRow").css("display", "none");
    $("#customer").css("display", "none");
    $("#item").css("display", "none");
    $("#placeOrder").css("display", "block");
    loadItemId();
    loadCustomerId();
});
/*console.log("HomePageEnd 1");
$("#homePageCustomers").text(customerDB.length);
console.log("HomePageEnd 2 ");

$("#homePageAmount").text(itemDB.length);
console.log("HomePageEnd");
$("#homePageOrderAmount").text(orderDB.length);*/
