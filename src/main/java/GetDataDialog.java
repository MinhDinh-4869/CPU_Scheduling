/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
import com.scheduling.option1.Process;
import com.scheduling.option1.*;
import com.scheduling.option1.drawAPI.drawGanntChartAPI;
import com.scheduling.option1.drawAPI.drawTimeAPI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetDataDialog extends JDialog {
    JPanel mainPanel = new JPanel();
    JPanel confirmPanel;
    JButton confirmButton;
    JButton cancelButton;
    List<List<JTextField>> infoTable;
    List<ScheduleInterface> schedules;
    String[] options = {"SJF", "PreEmpSJF", "RoundRobin", "FirstComeFirstServe"};
    JComboBox<String> selectScheduleBox;
    ScheduleInterface schedule;
    //JFrame parent;
    public GetDataDialog(int width, int height, int resource_num){
        //this.parent = parent;
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(new GridLayout(4,1));

        initSchedule(resource_num);

        infoTable = initTable(width,height);
        mainPanel.setLayout(new GridLayout(height + 1, 2));
        mainPanel.add(new JLabel("Processes"));
        mainPanel.add(getIdentifier(width));
        for(int i=0; i< infoTable.size(); i++)
        {
            JPanel temp = new JPanel();
            mainPanel.add(new JLabel("Process " + (i+1) + ":"));
            mainPanel.add(temp);
            for(int j=0; j<infoTable.get(i).size(); j++)
            {
                temp.add(infoTable.get(i).get(j));
            }
        }

        this.add(mainPanel, BorderLayout.CENTER);

        selectScheduleBox = new JComboBox<>(options);
        selectScheduleBox.setSelectedIndex(-1);
        this.add(selectScheduleBox, BorderLayout.AFTER_LAST_LINE);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Run");
        cancelButton = new JButton("Cancel");

        this.add(confirmPanel, BorderLayout.SOUTH);
        confirmPanel.add(confirmButton);
        confirmPanel.add(cancelButton);


        confirmButton.addActionListener(e -> onOk(resource_num));

        cancelButton.addActionListener(e -> onCancel());

        this.add(new JPanel().add(new JLabel("Please input the resource as follow: resource_id:burst. E.g: R1(3) = 1:3")));

        this.pack();
        this.setVisible(true);
    }

    List<List<JTextField>> initTable(int width, int height)
    {
        List<List<JTextField>> list_of_processes = new ArrayList<>();
        for(int i = 0; i< height; i++)
        {
            List<JTextField> process_i = new ArrayList<>();
            //process_i.setLayout(new BorderLayout(1, width));
            for(int j = 0; j< width; j++)
            {
                process_i.add(new JTextField(5) );
            }
            list_of_processes.add(process_i);
        }
        return list_of_processes;
    }

    JPanel getIdentifier(int width)
    {
        JPanel identifier = new JPanel();
        identifier.setLayout(new GridLayout(1, width));
        for(int i=0; i<width; i++)
        {
            JTextField tf = new JTextField(5);
            if(i == 0)
            {
                tf.setText("In System");
            }
            else if(i == 1)
            {
                tf.setText("In Ready");
            }
            else if(i % 2 == 0)
            {
                tf.setText("CPU " + (i/2));
            }
            else
            {
                tf.setText("Resource " + (i-1)/2);
            }
            identifier.add(tf);
        }
        return identifier;
    }

    void initSchedule(int resource_num)
    {
        this.schedules = new ArrayList<>();
        this.schedules.add(new NonPreEmptiveSJFSchedule(resource_num));
        this.schedules.add(new PreEmptiveSJFSchedule(resource_num));
        this.schedules.add(new RoundRobinSchedule(resource_num,3));
        this.schedules.add(new FCFSSchedule(resource_num));
    }
    void onOk(int resource_num)
    {
        initSchedule(resource_num);
        schedule = this.schedules.get(this.selectScheduleBox.getSelectedIndex());

        int p_count = 0;
        for(List<JTextField> process : infoTable)
        {
            p_count++;
            List<Integer> cpu = new ArrayList<>();
            List<Integer> resource = new ArrayList<>();
            List<Integer> resource_id = new ArrayList<>();
            int readyInTime = Integer.parseInt(process.get(1).getText());
            int systemInTime = Integer.parseInt(process.get(0).getText());
            for(int i=2; i<process.size(); i++)
            {
                if(i % 2 == 0 && !Objects.equals(process.get(i).getText(), ""))
                {
                    //System.out.print(process.get(i).getText());
                    cpu.add(Integer.parseInt(process.get(i).getText()));
                }
                else if(i % 2 == 1 && !Objects.equals(process.get(i).getText(), ""))
                {
                    String data = process.get(i).getText();
                    //System.out.print(data);
                    String[] split_data = data.split(":");
                    resource.add(Integer.parseInt(split_data[1]));
                    resource_id.add(Integer.parseInt(split_data[0]) - 1);
                }
            }
            Process p = new Process(schedule,String.format("P%d", p_count));
            p.in_time = readyInTime;
            p.in_system_time = systemInTime;
            p.setCpuBurst(cpu);
            p.setResourceBurst(resource, resource_id);
            //list_of_processes.add(p);
        }

        schedule.startProcess();
        //schedule.showTurnAroundTime();
        //System.out.println(schedule.showTurnAroundTime());
        //System.out.println(schedule.showWaitTime());
        //schedule.showChart();
        new drawTimeAPI(schedule);
        drawGanntChartAPI drawer = new drawGanntChartAPI();
        drawer.draw(schedule.getChart(), selectScheduleBox.getItemAt(selectScheduleBox.getSelectedIndex()));
        //dispose();
    }

    void onCancel()
    {
        dispose();
    }
}
