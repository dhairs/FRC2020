package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
    // Motors
    WPI_TalonSRX intakeMotorOne;
    WPI_TalonSRX intakeMotorTwo;
    WPI_TalonSRX conveyorMotor;
    WPI_TalonSRX feedMotor;
    WPI_TalonSRX shooterOne;
    WPI_TalonSRX shooterTwo;
    WPI_TalonSRX colorFlap;

    // Beam Break Sensors for Indexing Power Cells
    DigitalInput frontStartBeam;
    DigitalInput frontEndBeam;
    DigitalInput conveyorBeam;
    DigitalInput feedBeam;

    Rev2mDistanceSensor dSenseHigh;
    Rev2mDistanceSensor dSenseLow;

    public Intake() {
        intakeMotorOne = new WPI_TalonSRX(Variables.intakeMotorOnePort);
        intakeMotorTwo = new WPI_TalonSRX(Variables.intakeMotorTwoPort);
        conveyorMotor = new WPI_TalonSRX(Variables.conveyorMotorPort);
        feedMotor = new WPI_TalonSRX(Variables.feedMotorPort);
        frontStartBeam = new DigitalInput(Variables.frontStartBeamPort);
        frontEndBeam = new DigitalInput(Variables.frontEndBeamPort);
        conveyorBeam = new DigitalInput(Variables.conveyorBeamPort);
        feedBeam = new DigitalInput(Variables.feedBeamPort);
        shooterOne = new WPI_TalonSRX(Variables.shooterMotorOnePort);
        shooterTwo = new WPI_TalonSRX(Variables.shooterMotorTwoPort);
        colorFlap = new WPI_TalonSRX(0);
        dSenseHigh = new Rev2mDistanceSensor(Port.kOnboard);
        dSenseLow = new Rev2mDistanceSensor(Port.kMXP);
    }

    public void setSpeed(double speed) {
        intakeMotorOne.set(ControlMode.PercentOutput, speed);
        //intakeMotorTwo.set(ControlMode.PercentOutput, speed);
    }

    public void checkIntake(boolean bPressed){
        SmartDashboard.putBoolean("FrontBeam", frontStartBeam.get());
        SmartDashboard.putBoolean("EndBeam", frontEndBeam.get());
        SmartDashboard.putBoolean("ConveyorBeam", conveyorBeam.get());
        SmartDashboard.putBoolean("FeederBeam", feedBeam.get());
        if (bPressed){
            // Set Intake to run
            setSpeed(Variables.intakeMotorSpeed);
            
            // Front beam sensors for indexing
            if ((!frontStartBeam.get() || !frontEndBeam.get())){ // && feedBeam.get()
                conveyorMotor.set(ControlMode.PercentOutput, Variables.conveyorIndexSpeed);
            } else {
                conveyorMotor.set(ControlMode.PercentOutput, 0);
            }

            // End beam break check for indexing
            if (!conveyorBeam.get()){ // && feedBeam.get()
                feedMotor.set(ControlMode.PercentOutput, Variables.feedIndexSpeed);
            } else {
                feedMotor.set(ControlMode.PercentOutput, 0);
            }

        } else {
            setSpeed(0);
            feedMotor.set(ControlMode.PercentOutput, 0);
            conveyorMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public void setIntakeOn(boolean bPressed){
        if (bPressed){
            intakeMotorOne.set(ControlMode.PercentOutput, -0.25);
            //intakeMotorTwo.set(ControlMode.PercentOutput, 0);
            conveyorMotor.set(ControlMode.PercentOutput, 1);
        }else{
            intakeMotorOne.set(ControlMode.PercentOutput, 0);
            //intakeMotorTwo.set(ControlMode.PercentOutput, 0);
            conveyorMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public void setFullShoot(boolean bPressed){
        if (bPressed){
            shooterOne.set(ControlMode.PercentOutput, Variables.shooterSpeed);
            shooterTwo.set(ControlMode.PercentOutput, -1 * Variables.shooterSpeed);
            intakeMotorOne.set(ControlMode.PercentOutput, Variables.intakeMotorSpeed);
            conveyorMotor.set(ControlMode.PercentOutput, Variables.conveyorIndexSpeed);
            feedMotor.set(ControlMode.PercentOutput, Variables.feedIndexSpeed);
        } else {
            shooterOne.set(ControlMode.PercentOutput, 0);
            shooterTwo.set(ControlMode.PercentOutput, 0);
            intakeMotorOne.set(ControlMode.PercentOutput, 0);
            conveyorMotor.set(ControlMode.PercentOutput, 0);
            feedMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public void setColorFlap(double speed){
        colorFlap.set(ControlMode.PercentOutput, speed);
    }

    public void setColorFlapUp() {

        if(dSenseHigh.getRange() < 10) {
            colorFlap.set(0);
        }
        else {
            colorFlap.set(-0.4);
        }
        
    }

    public void setColorFlapDown() {

        if(dSenseHigh.getRange() < 10) {
            colorFlap.set(0);
        }
        else {
            colorFlap.set(0.1);
        }
    }
    
}