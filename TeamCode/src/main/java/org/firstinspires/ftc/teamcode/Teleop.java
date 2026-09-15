package org.firstinspires.ftc.teamcode;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Ri30HTeleop", group = "Iterative Opmode")
//@Disabled
public class Teleop extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Robot robot = new Robot();
     DigitalChannel sensor;

    double xVelocity;
    double yVelocity;
    double wVelocity;

    @Override
    public void init() {
        robot.init(hardwareMap, this);
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        // Drivetrain
        yVelocity = gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y);
        xVelocity = -gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
        wVelocity = gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) / 4;
        yVelocity = Range.clip(yVelocity, -1.0, 1.0);
        xVelocity = Range.clip(xVelocity, -1.0, 1.0);
        wVelocity = Range.clip(wVelocity, -1.0, 1.0);

        robot.mechanumDrive(xVelocity, yVelocity, wVelocity);

        // Intake
        if (gamepad1.left_trigger > 0.2) { // in
            robot.setIntakePower(-gamepad1.left_trigger);
        } else if (gamepad1.right_trigger > 0.2) { // out
            robot.setIntakePower(gamepad1.right_trigger);
        } else {
            robot.setIntakePower(0);
        }

        if (gamepad1.y) {
            robot.setPollenShooterPower(-0.7);
            robot.setNectarShooterPower(0.9);
        }
        else {
            robot.setPollenShooterPower(0);
            robot.setNectarShooterPower(0);
        }

        if (gamepad1.dpad_up) {
            robot.setNectarServoPosition(0.75);
            robot.setPollenServoPosition(0);
        }
        else if (gamepad1.dpad_down) {
            robot.setNectarServoPosition(0);
            robot.setPollenServoPosition(1);
        }

//        telemetry.addData("States", states);
//        telemetry.update();

    }
}