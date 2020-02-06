package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ShuffleboardWrapper {
    public void setWidget(String sbTab, String widgetTitle, Object defaultValue, BuiltInWidgets widgetType){
        Shuffleboard.getTab(sbTab).add(widgetTitle, defaultValue).withWidget(widgetType).getEntry();
    }

}