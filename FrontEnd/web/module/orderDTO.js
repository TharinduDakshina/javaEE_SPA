function orderDTO(oId,date,cId,iId,iName,iPrice,oQty,total,subTotal){
    var __oId=oId;
    var __oDate=date;
    var __cId=cId;
    var __iId=iId;
    var __iName=iName;
    var __iPrice=iPrice;
    var __oQty=oQty;
    var __total=total;

    this.getOrderId=function (){
        return __oId;
    }

    this.setOrderId=function (value){
        __oId=value;
    }

    this.getOrderDate=function (){
        return __oDate;
    }

    this.setOrderDate=function (value){
        __oDate=value;
    }

    this.getOrderCustomerId=function (){
        return __cId;
    }

    this.setOrderCustomerId=function (value){
        __cId=value;
    }

    this.getOrderItemId=function (){
        return __iId;
    }

    this.setOrderItemId=function (value){
        __iId=value;
    }

    this.getOrderItemName=function (){
        return __iName;
    }

    this.setOrderItemName=function (value){
        __iName=value;
    }

    this.getOrderItemPrice=function (){
        return __iPrice;
    }

    this.setOrderItemPrice=function (value){
        __iPrice=value;
    }

    this.getOrderQty=function (){
        return __oQty;
    }

    this.setOrderQty=function (value){
        __oQty=value;
    }

    this.getTotal=function (){
        return __total;
    }

    this.setTotal=function (value){
        __total=value;
    }

}