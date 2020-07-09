package com.example.projectgdsync;

public class CustomerMaster {

    private String custCode;
    private String custName;
    private String custaddress;
    private String custBalance;
    private String custSaleamt;
    private String custRecievedamt;

    public CustomerMaster() {
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustaddress() {
        return custaddress;
    }

    public void setCustaddress(String custaddress) {
        this.custaddress = custaddress;
    }

    public String getCustBalance() {
        return custBalance;
    }

    public void setCustBalance(String custBalance) {
        this.custBalance = custBalance;
    }

    public String getCustSaleamt() {
        return custSaleamt;
    }

    public void setCustSaleamt(String custSaleamt) {
        this.custSaleamt = custSaleamt;
    }

    public String getCustRecievedamt() {
        return custRecievedamt;
    }

    public void setCustRecievedamt(String custRecievedamt) {
        this.custRecievedamt = custRecievedamt;
    }
}
