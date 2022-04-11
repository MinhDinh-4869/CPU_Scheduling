package com.scheduling.option1;

//Interface to put the  methods that the client needs
public interface ScheduleInterface {
    void startProcess();
    void showChart();
    void showWaitTime();
    void showTurnAroundTime();
    void add(Process p);
    void addState(String name, int i);
}
