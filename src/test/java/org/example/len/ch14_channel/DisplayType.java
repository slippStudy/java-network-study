package org.example.len.ch14_channel;

enum DisplayType {

    DISPLAY_1("1", ServiceType.SERVICE_A),
    DISPLAY_2("2", ServiceType.SERVICE_A),
    DISPLAY_3("3", ServiceType.SERVICE_B),
    DISPLAY_4("4", ServiceType.SERVICE_B);

    private final String name;
    private final ServiceType serviceType;


    DisplayType(String name, ServiceType serviceType) {
        this.name = name;
        this.serviceType = serviceType;
    }

    public String getName() {
        return name;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }
}
