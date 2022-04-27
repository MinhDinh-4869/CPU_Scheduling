/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
package com.scheduling.option1.drawAPI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class drawGanntChartAPI {
    JDialog chartDialog;
    List<String> process;
    List<Integer> duration;
    public void draw(List<List<String>> g_chart, String algorithm_name)
    {
        chartDialog = new JDialog();
        chartDialog.setTitle(algorithm_name);
        chartDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        chartDialog.setLayout(new GridLayout(g_chart.size(), 1));
        //draw cpu
        for (List<String> strings : g_chart) {
            separateProcessDuration(strings);
            chartDialog.add(createPanel());
        }
        //separation done
        //add panel done
        //show
        chartDialog.pack();
        chartDialog.setVisible(true);
    }

    public void separateProcessDuration(List<String> traversal){
        process = new ArrayList<>();
        duration = new ArrayList<>();

        process.add(traversal.get(0));
        duration.add(1);
        int max_index = 0;
        for(int i=1; i<traversal.size(); i++)
        {
            if(traversal.get(i).equals(process.get(max_index)))
            {
                duration.set(max_index, duration.get(max_index) + 1);
            }
            else
            {
                process.add(traversal.get(i));
                duration.add(1);
                max_index++;
            }
        }

    }
    public JLabel createPanel(){
        StringBuilder log = new StringBuilder("__0__");

        int current_time = 0;
        while(process.size() > 0) {
            int first_half = duration.get(0) / 2;
            int second_half = duration.get(0) - first_half;

            log.append(String.valueOf(String.format("%6s", "____")).repeat(Math.max(0, first_half - 1)));
            log.append(String.format("%6s", "_" +process.get(0) + "_"));
            log.append(String.valueOf(String.format("%6s", "____")).repeat(Math.max(0, second_half - 1)));

            current_time += duration.get(0);
            log.append(String.format("%6s", "_" + current_time + "_"));

            process.remove(0);
            duration.remove(0);
        }
        return new JLabel(String.valueOf(log));
    }

}