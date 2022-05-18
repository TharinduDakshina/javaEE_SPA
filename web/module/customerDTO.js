function customerDTO(id,name,address,tp){
    var __id=id;
    var __name=name;
    var __address=address;
    var __tp=tp;

    this.getCustomerId=function (){
      return __id;
    };

    this.setCustomerID=function (value){
        __id=value;
    };

    this.getCustomerName=function (){
        return __name;
    };

    this.setCustomerName=function (value){
        __name=value;
    };

    this.getCustomerAddress=function (){
        return __address;
    };

    this.setCustomerAddress=function (value){
        __address=value;
    };

    this.getCustomerTp=function (){
        return __tp;
    };

    this.setCustomerTp=function (value){
        __tp=value;
    }
}