package org.usfirst.frc.team747.robot.autonomous;

import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.EjectTimedCommand;
import org.usfirst.frc.team747.robot.commands.PIDDriveInchesCommand;
import org.usfirst.frc.team747.robot.commands.PIDDriveRotateCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooserRight extends CommandGroup {
	public AutoChooserRight() {
		
		if(Robot.gameData.length() > 0) {
			if(Robot.gameData.charAt(0) == 'L') {
				addSequential(new PIDDriveInchesCommand(90-RobotMap.robotLength, true));
				addSequential(new PIDDriveRotateCommand(-90));
				addSequential(new PIDDriveInchesCommand(170, true));
				addSequential(new PIDDriveRotateCommand(90));
				addSequential(new PIDDriveInchesCommand(50, true));
				addSequential(new EjectTimedCommand(false, 0.5));
				
			    SmartDashboard.putString("GameDataValue", "LeftAuto");

				} else if(Robot.gameData.charAt(0) == 'R'){
					addSequential(new PIDDriveInchesCommand(164-(RobotMap.robotLength/2), true));
					addSequential(new PIDDriveRotateCommand(-90));
					addSequential(new PIDDriveInchesCommand(37.5, true));
					addSequential(new EjectTimedCommand(false, 0.5));
				    SmartDashboard.putString("GameDataValue", "RightAuto");

			}
		}
	}
	
	
}
