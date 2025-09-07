package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    private DcMotor lfDrive, lbDrive, rfDrive, rbDrive, intake, catapult, maw;
//    private Servo extendServo, intakeServo, hopperServo;
    private DigitalChannel sensor;

    OpMode opMode;

    private static final double WIDTH = 13.75;
    private static final double LENGTH = 12.25;
    private static final double MAX_SPEED = 1.0;

    public void init(HardwareMap ahwMap, OpMode op) {
        // Save reference to Hardware map
        this.opMode = op;

//        this.extendServo = ahwMap.get(Servo.class, "extendServo");
//        this.intakeServo = ahwMap.get(Servo.class, "intakeServo");
//        this.hopperServo = ahwMap.get(Servo.class, "hopperServo");

        // Define and Initialize Motors
        this.lfDrive  = ahwMap.get(DcMotor.class, "lfDrive");
        this.lbDrive  = ahwMap.get(DcMotor.class, "lbDrive");
        this.rfDrive  = ahwMap.get(DcMotor.class, "rfDrive");
        this.rbDrive  = ahwMap.get(DcMotor.class, "rbDrive");
        lfDrive.setDirection(DcMotor.Direction.REVERSE);
        lbDrive.setDirection(DcMotor.Direction.REVERSE);
        rfDrive.setDirection(DcMotor.Direction.FORWARD);
        rbDrive.setDirection(DcMotor.Direction.FORWARD);

        this.intake = ahwMap.get(DcMotor.class, "intake");
        this.catapult  = ahwMap.get(DcMotor.class, "catapult");
        this.maw = ahwMap.get(DcMotor.class, "maw");
        this.sensor = ahwMap.get(DigitalChannel.class, "sensor");
        intake.setDirection(DcMotor.Direction.REVERSE);
        catapult.setDirection(DcMotor.Direction.REVERSE);
        maw.setDirection(DcMotorSimple.Direction.FORWARD);
        sensor.setMode(DigitalChannel.Mode.INPUT);


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

    public void setCatapultPower(double power) {
        this.catapult.setPower(power);
    }

    public void setMawPower(double power) {
        this.maw.setPower(power);
    }

    public boolean sensorState() {
        return !sensor.getState();
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