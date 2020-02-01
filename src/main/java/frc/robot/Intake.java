package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {
    WPI_TalonSRX intakeMotor;

    public Intake() {
        intakeMotor = new WPI_TalonSRX(Variables.intakeMotorPort);
    }

    public void setSpeed(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }
}