package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Conveyor {
    WPI_TalonSRX convMotor;
    WPI_TalonSRX feedMotor;
    DigitalInput intakeSwitch;

    public Conveyor() {
        convMotor = new WPI_TalonSRX(Variables.conveyorMotorPort);
        feedMotor = new WPI_TalonSRX(Variables.feedMotorPort);
        intakeSwitch = new DigitalInput(9);
    }

    public void convey(double speed) {
        convMotor.set(ControlMode.PercentOutput, speed);
    }

    public void feed(double speed) {
        feedMotor.set(ControlMode.PercentOutput, -speed);
    }

    public void checkIntakeSwitch() {
        if(intakeSwitch.get()) {
            convMotor.set(ControlMode.PercentOutput, 1);
            feedMotor.set(ControlMode.PercentOutput, -1);
        }
        else {
            convMotor.set(ControlMode.PercentOutput, 0);
            feedMotor.set(ControlMode.PercentOutput, 0);
        }
    }
}