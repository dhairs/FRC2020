package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Climbing {
    // Motors
    WPI_TalonSRX raiseMotor;
    WPI_TalonSRX climbMotor;

    public Climbing() {
        raiseMotor = new WPI_TalonSRX(Variables.raiseMotorPort);
        climbMotor = new WPI_TalonSRX(Variables.climbMotorPort);
    }

    public void checkClimb(int joystickThumbAxis){
        if (joystickThumbAxis == 0){
            raiseMotor.set(ControlMode.PercentOutput, Variables.raiseMotorSpeed);
            climbMotor.set(ControlMode.PercentOutput, 0);
        } else if (joystickThumbAxis == 180){
            raiseMotor.set(ControlMode.PercentOutput, 0);
            climbMotor.set(ControlMode.PercentOutput, Variables.climbMotorSpeed);
        } else {
            raiseMotor.set(ControlMode.PercentOutput, 0);
            climbMotor.set(ControlMode.PercentOutput, 0);
        }
    }

}