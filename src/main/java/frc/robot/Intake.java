package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
    // Motors
    WPI_TalonSRX intakeMotor;
    WPI_TalonSRX conveyorMotor;
    WPI_TalonSRX feedMotor;

    // Beam Break Sensors for Indexing Power Cells
    DigitalInput frontGateBeam;
    DigitalInput endConveyerBeam;
    DigitalInput shooterHoldBeam;

    public Intake() {
        intakeMotor = new WPI_TalonSRX(Variables.intakeMotorPort);
        conveyorMotor = new WPI_TalonSRX(Variables.conveyorMotorPort);
        feedMotor = new WPI_TalonSRX(Variables.feedMotorPort);
        frontGateBeam = new DigitalInput(0);
        endConveyerBeam = new DigitalInput(2);
        shooterHoldBeam = new DigitalInput(4);
    }

    public void setSpeed(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    public void checkIntake(boolean bPressed){
        SmartDashboard.putBoolean("Front", frontGateBeam.get());
        SmartDashboard.putBoolean("End", endConveyerBeam.get());
        SmartDashboard.putBoolean("Feeder", shooterHoldBeam.get());
        if (bPressed){
            // Set Intake to run
            setSpeed(Variables.intakeMotorSpeed);

            // Front beam break check for indexing
            if (frontGateBeam.get()){
                conveyorMotor.set(ControlMode.PercentOutput, Variables.conveyorIndexSpeed);
            } else {
                conveyorMotor.set(ControlMode.PercentOutput, 0);
            }

            // End beam break check for indexing
            if (endConveyerBeam.get() && !shooterHoldBeam.get()){
                feedMotor.set(ControlMode.PercentOutput, Variables.feedIndexSpeed);
            } else {
                feedMotor.set(ControlMode.PercentOutput, 0);
            }

        }
    }
}