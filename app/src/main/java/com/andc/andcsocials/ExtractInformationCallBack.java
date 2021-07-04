package com.andc.andcsocials;

public interface ExtractInformationCallBack {

    public void setPhoneNumber(String phoneNumber);
    public String getPhoneNumber();
    public boolean getIsEmailVerified();
    public void setIsEmailVerified(boolean isEmailVerified);
    public void setSocietyType(String societyType);
    public String getSocietyType();
    public void setIsPhoneVerified(boolean isPhoneVerified);
    public boolean getIsPhoneVerified();
}
