package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class DriveTrain {

    double ty, tx, tv, ta, ts, zAdjust, xAdjust, yAdjust, integralZ, priorI, derivZ, priorEZ;
    double zTargetingAdjust, yTargetingAdjust; // Used for targeting humans
    final double kP_z = 0.0175;
    final double kF_z = 0.1;
    final double kI_z = 0.005;
    final double kD_z = 0.003;

    final double kP_x = 0.0175;
    final double kF_x = 0.1;
    final double kI_x = 0.005;
    final double kD_x = 0.003;
    
    final double kP_y = 0.0175;
    final double kF_y = 0.1;
    final double kI_y = 0.005;
    final double kD_y = 0.003;

    final double speed = 0.5;

    WPI_TalonSRX backLeft;
    WPI_TalonSRX backRight;
    WPI_TalonSRX frontLeft;
    WPI_TalonSRX frontRight;

    Encoder encoder;

    MecanumDrive mDrive;

    NetworkTable limeTable;

    public DriveTrain() {
        zAdjust = 0; xAdjust = 0; yAdjust = 0; integralZ = 0; priorI = 0; derivZ=0; priorEZ=0;

        frontLeft = new WPI_TalonSRX(Variables.frontLeftMotorPort);
        frontRight = new WPI_TalonSRX(Variables.frontRightMotorPort);
        backRight = new WPI_TalonSRX(Variables.backRightMotorPort);
        backLeft = new WPI_TalonSRX(Variables.backLeftMotorPort);

       //backLeft.setInverted(true);
       //backRight.setInverted(true);
       
        mDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
        mDrive.setSafetyEnabled(false);

        limeTable = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public void mecDrive(Joystick j) {
        mDrive.driveCartesian(0.6 * j.getX(), -0.6* j.getY(), 0.6 * j.getZ());

    }

    public void fullStop() {
        mDrive.driveCartesian(0, 0, 0);
    }

    public void targetGoal() {
        tv = limeTable.getEntry("tv").getDouble(0);
        tx = limeTable.getEntry("tx").getDouble(0);
        ty = limeTable.getEntry("ty").getDouble(0);
        ta = limeTable.getEntry("ta").getDouble(0);
        ts = limeTable.getEntry("ts").getDouble(0);

        if(tv == 1) {
            if(xIsAcceptable(tx)) {
                zAdjust = 0;
            }
            else { // Do the PID calculations for the spin-value
                integralZ += (tx*0.027);
                derivZ = (tx - priorEZ) / 0.027;
                zAdjust = (kP_z * tx) + (kI_z * integralZ) + (kD_z * derivZ);
                priorEZ = tx;

                // Add feed-forward value
                if(zAdjust > 0) {
                    zAdjust += kF_z;
                }
                else {
                    zAdjust -= kF_z;
                }
        
                // Check max/min bounds
                if(zAdjust > speed) { 
                    zAdjust = speed;
                }
                else if(zAdjust < -speed) {
                    zAdjust = -speed;
                }
            }

            if(yIsAcceptable(ty)) {
                yAdjust = 0;
            }
            else { // Do the PID calculations for the drive-value
                yAdjust = -(kP_y * ty);

                // Add feed-forward value
                if(yAdjust > 0) {
                    yAdjust += kF_y;
                }
                else {
                    yAdjust -= kF_y;
                }

                // Check max/min bounds
                if(yAdjust > speed) {
                    yAdjust = speed;
                }
                else if(zAdjust < -speed) {
                    zAdjust = -speed;
                }
            }
        } 
        else { //If no target is in sight, spin until one is found
            zAdjust = speed;
            yAdjust = 0;
            xAdjust = 0;
        }
        
        // Set drivetrain to the calculated values ////NOTE: xAdjust is not currently being used, it is always zero
        mDrive.driveCartesian(xAdjust, yAdjust, zAdjust);
    }

    private boolean xIsAcceptable(double value) {
        return (value > -1) && (value < 1);
    }

    private boolean yIsAcceptable(double value) {
        return (value > -3) && (value < 3);
    }

    public void resetErrors() {
        priorI = 0; 
        priorEZ = 0;
    }
}