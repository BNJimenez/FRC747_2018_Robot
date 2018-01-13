/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team747.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team747.robot.commands.ExampleCommand;
import org.usfirst.frc.team747.robot.subsystems.ExampleSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static OI m_oi;
	Joystick rightDrive = new Joystick(1);
	Joystick leftDrive = new Joystick(0);
	public static TalonSRX leftFrontDrive = new TalonSRX(0);
	public static TalonSRX leftRearDrive = new TalonSRX(1);
	public static TalonSRX rightFrontDrive = new TalonSRX(2);
	public static TalonSRX rightRearDrive = new TalonSRX(3);
	public static int sleepTimer;
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
			double speedModifier;
		
		
			boolean leftTriggerPressed = leftDrive.getRawButton(1);
			boolean rightTriggerPressed = rightDrive.getRawButton(1);
			boolean leftBrakePressed = leftDrive.getRawButton(2);
			boolean rightBrakePressed = leftDrive.getRawButton(2);
			
			boolean brakePressed = false;
			
			if(leftBrakePressed || rightBrakePressed) {
				brakePressed = true;
			}
			if(leftTriggerPressed || rightTriggerPressed) {
				speedModifier = 1;
			} else {
				speedModifier = 0.5;
			}
			
			if(brakePressed) {
				speedModifier = 0;
				leftFrontDrive.set(ControlMode.);
				leftRearDrive.set(ControlMode.PercentOutput, leftJoystickValue);
				rightFrontDrive.set(ControlMode.PercentOutput, rightJoystickValue);
				rightRearDrive.set(ControlMode.PercentOutput, rightJoystickValue);
			}
	
			double rightJoystickValue = -rightDrive.getRawAxis(1)*speedModifier;
			double leftJoystickValue = leftDrive.getRawAxis(1)*speedModifier;
		
		
			
		
		//double leftJoystickValue = leftDrive.getRawAxis(1)/2;
		
		leftFrontDrive.set(ControlMode.PercentOutput, leftJoystickValue);
		leftRearDrive.set(ControlMode.PercentOutput, leftJoystickValue);
		rightFrontDrive.set(ControlMode.PercentOutput, rightJoystickValue);
		rightRearDrive.set(ControlMode.PercentOutput, rightJoystickValue);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
