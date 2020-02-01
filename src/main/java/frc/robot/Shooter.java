package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Shooter {

    WPI_TalonSRX shooterMotor1;
    WPI_TalonSRX shooterMotor2;

    public Shooter() {
    shooterMotor1 = new WPI_TalonSRX(9);    
    shooterMotor2 = new WPI_TalonSRX(6);
    }

    public void spinnyBoi2k(double speed){
        shooterMotor1.set(speed);
        shooterMotor2.set(-speed);
    }
}