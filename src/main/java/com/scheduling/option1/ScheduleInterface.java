/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
package com.scheduling.option1;

import java.util.List;

//Interface to put the  methods that the client needs
public interface ScheduleInterface {
    void startProcess();
    void showChart();
    String showWaitTime();
    String showTurnAroundTime();
    void add(Process p);
    void addState(String name, int i);
    public List<List<String>> getChart();
}
