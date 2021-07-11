package com.andc.andcsocials;

public class SocietyInformation {

    int societyImageID;
    String societyName;
    String description;
    String studentCoordinatorName;
    String societyContactInformation;

    SocietyInformation() {}

    public SocietyInformation(int societyImageID, String societyName, String description,
                              String studentCoordinatorName, String societyContactInformation) {
        this.societyImageID = societyImageID;
        this.societyName = societyName;
        this.description = description;
        this.studentCoordinatorName=studentCoordinatorName;
        this.societyContactInformation=societyContactInformation;
    }
}
