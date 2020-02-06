/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "Main Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  DriveTrain driveTrain;
  Intake intake;
  Climbing climbing;
  ShuffleboardWrapper sb;

  Joystick joy;

  NetworkTable limeTable;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    driveTrain = new DriveTrain();
    intake = new Intake();
    climbing = new Climbing();
    sb = new ShuffleboardWrapper();

    joy = new Joystick(0);

    //PIDSetup();

    limeTable = NetworkTableInstance.getDefault().getTable("limelight");

    // Setting Camera view for Shuffleboard
    limeTable.getEntry("stream").setNumber(2);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // Run intake if side-button is pressed
    intake.checkIntake(joy.getRawButton(3));

    // Drivetrain and Vision targeting buttons
    if (joy.getTrigger()){
      limeTable.getEntry("ledMode").setNumber(3);
      driveTrain.targetGoal();
    } else {
      driveTrain.mecDrive(joy);
      driveTrain.resetErrors();
      limeTable.getEntry("ledMode").setNumber(1);
    }

    // Checking for climb input
    climbing.checkClimb(joy.getPOV());

    // Transfer to color wheel later
    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0)
    {
      sb.setWidget("Main", "Stage 3 Active", true, BuiltInWidgets.kBooleanBox);
      switch (gameData.charAt(0))
      {
        case 'B' :
          sb.setWidget("Main", "Target Color", "Blue", BuiltInWidgets.kTextView);
          break;
        case 'G' :
          sb.setWidget("Main", "Target Color", "Green", BuiltInWidgets.kTextView);
          break;
        case 'R' :
          sb.setWidget("Main", "Target Color", "Red", BuiltInWidgets.kTextView);
          break;
        case 'Y' :
          sb.setWidget("Main", "Target Color", "Yellow", BuiltInWidgets.kTextView);
          break;
        default :
          sb.setWidget("Main", "Target Color", "Error", BuiltInWidgets.kTextView);
          break;
      }
    } else {
      sb.setWidget("Main", "Stage 3 Active", false, BuiltInWidgets.kBooleanBox);
      sb.setWidget("Main", "Target Color", "N/A", BuiltInWidgets.kTextView);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    
  }

  // private void PIDSetup() {
  //   //t.set(ControlMode.Position, 5000);

  //   t.configFactoryDefault();

  //   t.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);

  //   t.setSensorPhase(true);

  //   t.setInverted(false);
    
  //   t.configNominalOutputForward(0, 30);
  //   t.configNominalOutputReverse(0, 30);
  //   t.configPeakOutputForward(1, 30);
  //   t.configPeakOutputReverse(-1, 30);

  //   t.configMotionAcceleration(0, 500);
  //   t.configMotionCruiseVelocity(0, 500);
    
  //   t.configAllowableClosedloopError(0, 0, 30);

  //   //First parameter is PID_loop_id
  //   t.config_kF(0, 0.094);
  //   t.config_kP(0, .35);
  //   t.config_kI(0, 0.001);
  //   t.config_kD(0, 3);

  //   t.setSelectedSensorPosition(0, 0, 30);
  //   //t.set(ControlMode.Position, 100);

  //   //t.clearMotionProfileTrajectories();
  // }
}