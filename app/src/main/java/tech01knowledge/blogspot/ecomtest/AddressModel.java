package tech01knowledge.blogspot.ecomtest;

public class AddressModel {
    private String fullname;
    private String addrress;
    private String pincode;
    private Boolean selected;

    public AddressModel(String fullname, String addrress, String pincode, Boolean selected) {
        this.fullname = fullname;
        this.addrress = addrress;
        this.pincode = pincode;
        this.selected = selected;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddrress() {
        return addrress;
    }

    public void setAddrress(String addrress) {
        this.addrress = addrress;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
