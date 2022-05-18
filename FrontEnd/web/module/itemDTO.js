function itemDTO(id,name,qty,price){
    var __id =id;
    var __name=name;
    var __qty=qty;
    var __price=price;

    this.getItemId=function (){
        return __id;
    };

    this.setItemId=function (value){
        __id=value;
    };

    this.getItemName=function (){
        return __name;
    };

    this.setItemName=function (value){
        __name=value;
    };

    this.getItemQty=function (){
        return __qty;
    };

    this.setItemQty=function (value){
        __qty=value;
    };

    this.getItemPrice=function (){
        return __price;
    };

    this.setItemPrice=function (value){
        __price=value;
    };
}