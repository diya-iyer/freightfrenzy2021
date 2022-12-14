package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")


    public class MacOpMode extends LinearOpMode {
    MacHardwarePushbot robot = new MacHardwarePushbot();
    private ElapsedTime runtime = new ElapsedTime();

    double leftForwardPower;
    double rightForwardPower;
    double leftBackwardPower;
    double rightBackwardPower;
    double DJPower;
    final double CLAWINCREMENT = 0.2;

    boolean startWheel = false;
    boolean stopWheel = true;

    double powerMultiplier = 0.5;
    double DJPowerMultiplier = 0.8;
    double MAX_POWER = 1.0;

    // private Servo grabber = null;
    @Override
    public void runOpMode() {


        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;

        telemetry.addData("Status", "Init Done");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        //double tgtPower = 0;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Setup a variable for each drive wheel to save power level for telemetry

            driveMacChasis();
            DJMove();
            armMove();
            telemetry.update();

        }


    }

    public void driveMacChasis() {
        // POV Mode uses left stick to go forward, backward, and turn
        // - This uses basic math to combine motions and is easier to drive straight.

        double driveForward = gamepad1.left_stick_y;
        double driveBackward = gamepad1.left_stick_y;
        double turnRight = gamepad1.right_stick_x;
        double turnLeft = gamepad1.right_stick_x;
        double strafeRight = gamepad1.left_stick_x;
        double strafeLeft = gamepad1.left_stick_x;
        double powerMultiplier = 0.7;

        boolean driveStop = false;


        if ((gamepad1.left_stick_y == 0) && (gamepad1.right_stick_y == 0) && (gamepad1.left_stick_x == 0) && (gamepad1.right_stick_x == 0))
            driveStop = true;

        //Mecanum wheels work well with full power
        if (turnRight < 0) {
            telemetry.addData("Status", "Moving right");
            telemetry.update();

            robot.leftDrive1.setPower(powerMultiplier);
            robot.rightDrive1.setPower(-powerMultiplier);
            robot.leftDrive2.setPower(powerMultiplier);
            robot.rightDrive2.setPower(-powerMultiplier);

        } else if (turnLeft > 0) {

            telemetry.addData("Status", "Moving left");
            telemetry.update();

            robot.leftDrive1.setPower(-powerMultiplier);
            robot.rightDrive1.setPower(powerMultiplier);
            robot.leftDrive2.setPower(-powerMultiplier);
            robot.rightDrive2.setPower(powerMultiplier);

        } else if (driveForward > 0) {

            telemetry.addData("Status", "Moving forward");
            telemetry.update();

            robot.leftDrive1.setPower(powerMultiplier);
            robot.rightDrive1.setPower(powerMultiplier);
            robot.leftDrive2.setPower(powerMultiplier);
            robot.rightDrive2.setPower(powerMultiplier);
        } else if (driveBackward < 0) {

            telemetry.addData("Status", "Moving backward");
            telemetry.update();

            robot.leftDrive1.setPower(-powerMultiplier);
            robot.rightDrive1.setPower(-powerMultiplier);
            robot.leftDrive2.setPower(-powerMultiplier);
            robot.rightDrive2.setPower(-powerMultiplier);
        } else if (driveStop) {
            telemetry.addData("Status", "Stopping");
            telemetry.update();

            robot.leftDrive1.setPower(0);
            robot.rightDrive1.setPower(0);
            robot.leftDrive2.setPower(0);
            robot.rightDrive2.setPower(0);
        } else if (strafeRight > 0) {
            telemetry.addData("Status", "Moving Right");
            telemetry.update();

            robot.leftDrive1.setPower(powerMultiplier);
            robot.rightDrive1.setPower(-powerMultiplier);
            robot.leftDrive2.setPower(-powerMultiplier);
            robot.rightDrive2.setPower(powerMultiplier);

        } else if (strafeLeft < 0) {
            telemetry.addData("Status", "Moving Left");
            telemetry.update();

            robot.leftDrive1.setPower(-powerMultiplier);
            robot.rightDrive1.setPower(powerMultiplier);
            robot.leftDrive2.setPower(powerMultiplier);
            robot.rightDrive2.setPower(-powerMultiplier);
        }

        leftForwardPower = this.robot.leftDrive1.getPower();
        rightForwardPower = this.robot.rightDrive1.getPower();
        leftBackwardPower = this.robot.leftDrive1.getPower();
        rightBackwardPower = this.robot.rightDrive1.getPower();
        // Show the elapsed game time and wheel power.

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Inputs received", "Drive Forward: " + driveForward + " Drive Backward: " + driveBackward + " Drive Right: " + turnRight + " Drive Left: " + turnLeft);
        telemetry.addData("Motors Forward", "left (%.2f), right (%.2f)", leftForwardPower, rightForwardPower);
        telemetry.addData("Motors Backward", "left (%.2f), right (%.2f)", leftBackwardPower, rightBackwardPower);

    }

    public void DJMove() {

        boolean toggleIntakePressed = gamepad1.y;

        if (toggleIntakePressed) {

            if (startWheel) { //Intake is already running. So Sto
                stopWheel = true;
                startWheel = false;

            } else { //the Intake is not running. So Start it.
                startWheel = true;
                stopWheel = false;
            }

            DJPower = this.robot.DJ.getPower();

            if (stopWheel) { //checking the power of the motors
                robot.DJ.setPower(0);
                telemetry.addData("Status", "Stopping spin..");
            }
            if (startWheel) {
                robot.DJ.setPower(powerMultiplier);
                telemetry.addData("Status", "Starting spin..");
            }

            telemetry.update();

        }

    }

    public void armMove() {
        float craneArmUp = gamepad2.left_trigger;
        float craneArmDown = gamepad2.right_trigger;
        double clawIn = gamepad2.left_stick_y;
        double clawOut = gamepad2.left_stick_y;
        boolean driveStop = false;

        if (craneArmUp > 0.5) {
            robot.craneArm.setPower(powerMultiplier);
        } else if (craneArmUp == 0) {
            robot.craneArm.setPower(0);
        }

        if (craneArmDown > 0.5) {
            robot.craneArm.setPower(-powerMultiplier);
        } else if (craneArmDown == 0) {
            robot.craneArm.setPower(0);
        }

        if (gamepad2.left_stick_y == 0)
            driveStop = true;

        if (clawIn < 0) {

            telemetry.addData("Status", "Moving forward");
            telemetry.update();
            robot.claw.setPower(powerMultiplier);
            driveStop = false;

        }  else if (clawOut > 0) {

            telemetry.addData("Status", "Moving forward");
            telemetry.update();
            robot.claw.setPower(-powerMultiplier);
            driveStop = false;

        }    else if (driveStop == true) {

    robot.claw.setPower(0);

}


        }
    }




