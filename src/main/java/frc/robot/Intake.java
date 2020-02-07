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
        frontGateBeam = new DigitalInput(Variables.frontBeamPort);
        endConveyerBeam = new DigitalInput(Variables.endBeamPort);
        shooterHoldBeam = new DigitalInput(Variables.feedBeamPort);
    }

    public void setSpeed(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    public void checkIntake(boolean bPressed){
        SmartDashboard.putBoolean("FrontBeam", frontGateBeam.get());
        SmartDashboard.putBoolean("EndBeam", endConveyerBeam.get());
        SmartDashboard.putBoolean("FeederBeam", shooterHoldBeam.get());
        System.out.println("FrontBeam: " + frontGateBeam.get());
        if (bPressed){
            // Set Intake to run
            setSpeed(Variables.intakeMotorSpeed);

            // Front beam break check for indexing
            if (!frontGateBeam.get()){
                conveyorMotor.set(ControlMode.PercentOutput, Variables.conveyorIndexSpeed);
            } else {
                conveyorMotor.set(ControlMode.PercentOutput, 0);
            }

            // End beam break check for indexing
            if (!endConveyerBeam.get()){ // && !shooterHoldBeam.get()
                SmartDashboard.putBoolean("FeedSpin", true);
                feedMotor.set(ControlMode.PercentOutput, Variables.feedIndexSpeed);
            } else {
                feedMotor.set(ControlMode.PercentOutput, 0);
                SmartDashboard.putBoolean("FeedSpin", false);
            }

        } else {
            setSpeed(0);
            feedMotor.set(ControlMode.PercentOutput, 0);
            conveyorMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public void setIntakeOn(boolean bPressed){
        if (bPressed){
            intakeMotor.set(ControlMode.PercentOutput, -0.5);
            conveyorMotor.set(ControlMode.PercentOutput, 1);
        }else{
            conveyorMotor.set(ControlMode.PercentOutput, 0);
            intakeMotor.set(ControlMode.PercentOutput, 0);
        }
    }
}