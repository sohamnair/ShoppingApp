package com.morningessetial.morningessentials.Modal;

public class Products {

    private String Image,Name,Price,Weight,Stock,Items,Total,Quantity,days15,days20,days30,Fprice,Discount,DeliveryFee,MinDeliveryVal,MinDiscountVal,Contact,Status,Id,DiscountVal,Address,Version,Msg;

    public Products(){

    }

    public Products(String image, String name, String price, String weight, String stock, String items,String total,String quantity,String days15, String days20, String days30, String fprice, String discount, String deliveryFee,String minDeliveryVal, String minDiscountVal, String contact, String status, String id, String discountVal, String address, String version, String msg) {
        this.Image = image;
        this.Name = name;
        this.Price = price;
        this.Weight = weight;
        this.Stock = stock;
        this.Items = items;
        this.Total = total;
        this.Quantity = quantity;
        this.days15 = days15;
        this.days20 = days20;
        this.days30 = days30;
        this.Fprice = fprice;
        this.Discount = discount;
        this.DeliveryFee = deliveryFee;
        this.MinDeliveryVal = minDeliveryVal;
        this.MinDiscountVal = minDiscountVal;
        this.Contact = contact;
        this.Status = status;
        this.Id = id;
        this.DiscountVal = discountVal;
        this.Address = address;
        this.Version = version;
        this.Msg = msg;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getWeight() {
        return Weight;
    }

    public String getStock() {
        return Stock;
    }

    public String getItems() {
        return Items;
    }

    public String getTotal() {
        return Total;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getDays15() {
        return days15;
    }

    public String getDays20() {
        return days20;
    }

    public String getDays30() {
        return days30;
    }

    public String getFprice() {
        return Fprice;
    }

    public String getDiscount() {
        return Discount;
    }

    public String getDeliveryFee() {
        return DeliveryFee;
    }

    public String getMinDeliveryVal() {
        return MinDeliveryVal;
    }

    public String getMinDiscountVal() {
        return MinDiscountVal;
    }

    public String getContact() {
        return Contact;
    }

    public String getStatus() {
        return Status;
    }

    public String getId() {
        return Id;
    }

    public String getDiscountVal() {
        return DiscountVal;
    }

    public String getAddress() {
        return Address;
    }

    public String getVersion() {
        return Version;
    }

    public String getMsg() {
        return Msg;
    }
}
