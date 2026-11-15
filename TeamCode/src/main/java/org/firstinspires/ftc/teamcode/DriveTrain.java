package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class DriveTrain extends LinearOpMode {
    private Servo PositionalRampServo;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        DcMotor intakeWheel = hardwareMap.dcMotor.get("left_IntakeMotor");
        DcMotor flywheel = hardwareMap.dcMotor.get("Flywheel");
        double servo_position = 0.5;
        Servo Kicker = hardwareMap.get(Servo.class, "Fliper");
        Servo LinearServo = hardwareMap.get(Servo.class, "LinearServo");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double rx = -gamepad1.left_stick_y;
            double y = gamepad1.left_stick_x * 1.1;
            double x = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            if (gamepad1.dpad_down) {
                servo_position -= (10.0 / 180.0);
                sleep(300); // Debouncing delay
            }
            if (gamepad1.dpad_up) {
                servo_position += (10.0 / 180.0);
                sleep(300); // Debouncing delay
            }
            servo_position = Range.clip(servo_position, 0.0, 1.0);
            LinearServo.setPosition(servo_position);

            if (gamepad1.left_bumper)
                intakeWheel.setPower(1);
            else
                intakeWheel.setPower(0);

            if (gamepad1.right_trigger_pressed)
                flywheel.setPower(1);
            else
                flywheel.setPower(0);

            if (gamepad1.square)
                LinearServo.setPosition(0.32);
            else
                LinearServo.setPosition(0.2);
        }
    }
}