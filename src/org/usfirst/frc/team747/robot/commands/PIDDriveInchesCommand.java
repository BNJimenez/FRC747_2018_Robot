package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDDriveInchesCommand extends Command {
    
    //execute is called every 20ms and isFinished is called right after execute
    //add a button to Ryan's joystick that will default the drive train back to DriveWithJoystickCommand
    
    private double driveTicks;
    private double driveP;
    private double driveI;
    private double driveD;
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;
    
    private final static double ENCODER_TICKS_PER_REVOLUTION = 4096;

    private static final double MAX_PERCENT_VOLTAGE = 1.0; //was 12 (volts previously, now the input is percent)
    private static final double MIN_PERCENT_VOLTAGE = 0.0; //was 1.9 (volts perviously, now the input is percent)

    //STOP_THRESHOLD_REAL was 3 inches and is now 8 inches in an attempt to cut back on time
    private final static double STOP_THRESHOLD_REAL = .5; //3.0;
    private final static double STOP_THRESHOLD_ADJUSTED = Robot.DRIVE_TRAIN.convertInchesToRevs(STOP_THRESHOLD_REAL * ENCODER_TICKS_PER_REVOLUTION);
    
    private final static int I_ZONE_IN_REVOLUTIONS = 50; //100;
    
    private final static int allowableCloseLoopError = 1;
    
    private int onTargetCount = 0;
    
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * 0.25;

    private double specificDistanceInches;
    
    private double specificDistanceP = 1.5;
    
    private double specificDistanceI = 0.08;
    
    private double specificDistanceD = 60;
    
    private double specificDistanceF = 0.1489;
    
    //the values used for motion magic (universal PID values for driving forward and back
    
//    private final static double FORWARD_TO_SHOOT_DISTANCE = 81.25;
//    private final static double FORWARD_TO_SHOOT_P = 1.5;
//    private final static double FORWARD_TO_SHOOT_I = 0.01;
//    private final static double FORWARD_TO_SHOOT_D = 60;
    

    
    public PIDDriveInchesCommand(double inches, boolean reverse) {
        requires(Robot.DRIVE_TRAIN);
          
//      this.driveTicks = inches / ENCODER_TICKS_PER_REVOLUTION;
    
        if (reverse) {
            this.driveTicks = -Robot.DRIVE_TRAIN.convertInchesToRevs(inches * ENCODER_TICKS_PER_REVOLUTION);//input now has to be ticks instead of revolutions which is why we multiply by 4096
        } else {
            this.driveTicks = Robot.DRIVE_TRAIN.convertInchesToRevs(inches * ENCODER_TICKS_PER_REVOLUTION);
        }
        this.driveP = specificDistanceP;
        this.driveI = specificDistanceI;
        this.driveD = specificDistanceD;
    }
    
        
    protected void initialize() {
        
//      SmartDashboard.putString("specificDistanceName:", specificDistanceName);
        
        onTargetCount = 0;
        
        Robot.DRIVE_TRAIN.resetBothEncoders();
//      Robot.resetNavXAngle();
        Robot.DRIVE_TRAIN.enablePositionControl();
        
        
        /*
         * April 20th: Brian - Comfortable PID values that we found are P = 3, I = 0, and D = 950.
         * Testing done with George and Corey, we dropped using I. Still need to test the use of
         * different drive distances. April 20th (end of the night): Brian - after testing shorter
         * distances, particularly 25 inches, we found that the drive train does not arrive at the
         * desired location.
         */
                
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.config_kP(pidIdx, specificDistanceP, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.config_kP(pidIdx, specificDistanceP, timeoutMs);
//        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.config_kI(pidIdx, specificDistanceI, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.config_kI(pidIdx, specificDistanceI, timeoutMs);
//        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.config_kD(pidIdx, specificDistanceD, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.config_kD(pidIdx, specificDistanceD, timeoutMs);
//        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.config_kF(pidIdx, specificDistanceF, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.config_kF(pidIdx, specificDistanceF, timeoutMs);
        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.ClearIaccum();
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.ClearIaccum();
        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setCloseLoopRampRate(rampRate);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setCloseLoopRampRate(rampRate);
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs); //was 6
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs); //was 6
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.config_IntegralZone(slotIdx, I_ZONE_IN_REVOLUTIONS, timeoutMs);

        Robot.DRIVE_TRAIN.setPID(driveTicks, driveTicks);
    }
    
    protected void execute() {
//      SmartDashboard.putNumber("STOP THRESHOLD:", Robot.DRIVE_TRAIN.convertRevsToInches(STOP_THRESHOLD_ADJUSTED));
//      SmartDashboard.putNumber("Closed-Loop Error Left:", Robot.DRIVE_TRAIN.talonDriveLeftPrimary.getClosedLoopError());
//      SmartDashboard.putNumber("Closed-Loop Error Right:", Robot.DRIVE_TRAIN.talonDriveRightPrimary.getClosedLoopError());
//      SmartDashboard.putNumber("I Accum Left:", Robot.DRIVE_TRAIN.talonDriveLeftPrimary.GetIaccum());
//      SmartDashboard.putNumber("I Accum Right:", Robot.DRIVE_TRAIN.talonDriveRightPrimary.GetIaccum());
        
//        IAccumDistanceTraveled = Robot.DRIVE_TRAIN.convertRevsToInches((Robot.DRIVE_TRAIN.getRightPosition() + Robot.DRIVE_TRAIN.getLeftPosition()) * 4);
//      
//      if ((Math.abs(IAccumDistanceTraveled) - Math.abs(IAccumDistanceCounter)) >= Math.abs(I_ACCUM_RESET_BENCHMARK_IN_INCHES)) {
//          IAccumDistanceCounter = IAccumDistanceTraveled;
//          Robot.DRIVE_TRAIN.talonDriveLeftPrimary.ClearIaccum();
//          Robot.DRIVE_TRAIN.talonDriveRightPrimary.ClearIaccum();
//      }
//      
//      if ((Math.abs(IAccumDistanceTraveled) >= Math.abs(driveTicks) && !firstPass)) {
//          firstPass = true;
//            Robot.DRIVE_TRAIN.talonDriveLeftPrimary.ClearIaccum();
//            Robot.DRIVE_TRAIN.talonDriveRightPrimary.ClearIaccum();
//      }
        
    }
    
    @Override
    protected boolean isFinished() {
        double leftPosition = Robot.DRIVE_TRAIN.getLeftPosition();
        double rightPosition = Robot.DRIVE_TRAIN.getRightPosition();
        
        if (leftPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && leftPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED) &&
            rightPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && rightPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED)) {
            onTargetCount++;
        } else {
            onTargetCount = 0;
        }
        
        return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
    }
    
    protected void end() {
        System.out.println("LEFT Drive Distance: Inches" + Robot.DRIVE_TRAIN.convertRevsToInches(Robot.DRIVE_TRAIN.getLeftPosition()));
        System.out.println("RIGHT Drive Distance: Inches" + Robot.DRIVE_TRAIN.convertRevsToInches(Robot.DRIVE_TRAIN.getRightPosition()));
        Robot.DRIVE_TRAIN.enableVBusControl();
        Robot.DRIVE_TRAIN.resetBothEncoders();
//      Robot.resetNavXAngle();
        Robot.DRIVE_TRAIN.stop();
    }
    
    protected void interrupted() {
        end();
    }

}
