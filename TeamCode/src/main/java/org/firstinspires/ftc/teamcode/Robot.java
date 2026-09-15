package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    private DcMotor lfDrive, lbDrive, rfDrive, rbDrive, intake, pollenShooter, nectarShooter;
    private Servo pollenServo, nectarServo;
    OpMode opMode;

    private static final double WIDTH = 15.00;
    private static final double LENGTH = 13.7;
    private static final double MAX_SPEED = 1.0;

    public void init(HardwareMap ahwMap, OpMode op) {
        // Save reference to Hardware map
        this.opMode = op;

        this.pollenServo = ahwMap.get(Servo.class, "pollenServo");
        this.nectarServo = ahwMap.get(Servo.class, "nectarServo");

        // Define and Initialize Motors
        this.lfDrive  = ahwMap.get(DcMotor.class, "lfDrive");
        this.lbDrive  = ahwMap.get(DcMotor.class, "lbDrive");
        this.rfDrive  = ahwMap.get(DcMotor.class, "rfDrive");
        this.rbDrive  = ahwMap.get(DcMotor.class, "rbDrive");
        rfDrive.setDirection(DcMotor.Direction.REVERSE);
        rbDrive.setDirection(DcMotor.Direction.REVERSE);
        lfDrive.setDirection(DcMotor.Direction.FORWARD);
        lbDrive.setDirection(DcMotor.Direction.FORWARD);

        this.intake = ahwMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotor.Direction.REVERSE);

        this.nectarShooter = ahwMap.get(DcMotor.class, "nectarShooter");
        this.pollenShooter = ahwMap.get(DcMotor.class, "pollenShooter");

        nectarShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pollenShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        stop();

        setDriveMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lfDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lbDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rfDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rbDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void skidSteerDrive(double lPower, double rPower){
        lfDrive.setPower(lPower);
        lbDrive.setPower(lPower);
        rfDrive.setPower(rPower);
        rbDrive.setPower(rPower);
    }

    // Moves the drive train using the given x, y, and rotational velocities
    public void mechanumDrive(double xVelocity, double yVelocity, double wVelocity) {
        double speedScale = 1.0;

        double rfVelocity = yVelocity - xVelocity + wVelocity * (WIDTH / 2 + LENGTH / 2);
        double lfVelocity = yVelocity + xVelocity - wVelocity * (WIDTH / 2 + LENGTH / 2);
        double lbVelocity = yVelocity - xVelocity - wVelocity * (WIDTH / 2 + LENGTH / 2);
        double rbVelocity = yVelocity + xVelocity + wVelocity * (WIDTH / 2 + LENGTH / 2);

        double maxVelocity = findMax(lfVelocity, rfVelocity, lbVelocity, rbVelocity);

        if (maxVelocity > MAX_SPEED) {
            speedScale = MAX_SPEED / maxVelocity;
        }

        lfDrive.setPower(lfVelocity * speedScale);
        rfDrive.setPower(rfVelocity * speedScale);
        lbDrive.setPower(lbVelocity * speedScale);
        rbDrive.setPower(rbVelocity * speedScale);

    }

    // Helper function for power scaling in the drive method
    private double findMax(double... vals) {
        double max = Double.NEGATIVE_INFINITY;
        for (double d : vals) {
            if (d > max) max = d;
        }
        return max;
    }

    public void setIntakePower(double power) {
        this.intake.setPower(power);
    }

    public void setPollenShooterPower(double power) {
        this.pollenShooter.setPower(power);
    }

    public void setNectarShooterPower(double power) {
        this.nectarShooter.setPower(power);
    }

    public void setNectarServoPosition(double position) {
        this.nectarServo.setPosition(position);
    }

    public void setPollenServoPosition(double position) {
        this.pollenServo.setPosition(position);
    }

    public void stop(){
        lfDrive.setPower(0);
        lbDrive.setPower(0);
        rfDrive.setPower(0);
        rbDrive.setPower(0);
    }

    public void setDriveMotorMode(DcMotor.RunMode mode) {
        rfDrive.setMode(mode);
        rbDrive.setMode(mode);
        lfDrive.setMode(mode);
        lbDrive.setMode(mode);
    }
}