package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class MacHardwarePushbot
{
    /* Public OpMode members. */
    public DcMotor  leftDrive1   = null;
    public DcMotor  leftDrive2  = null;
    public DcMotor  rightDrive1     = null;
    public DcMotor   rightDrive2   = null;
    public DcMotor   DJ   = null;
    public DcMotor craneArm = null;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public MacHardwarePushbot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDrive1  = hwMap.get(DcMotor.class, "left_drive1");
        leftDrive2 = hwMap.get(DcMotor.class, "left_drive2");
        rightDrive1  = hwMap.get(DcMotor.class, "right_drive1");
        rightDrive2  = hwMap.get(DcMotor.class, "right_drive2");
        DJ = hwMap.get(DcMotor.class, "dj");
        craneArm = hwMap.get(DcMotor.class, "crane_arm");

        leftDrive1.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        leftDrive2.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        rightDrive1.setDirection(DcMotor.Direction.FORWARD);
        rightDrive2.setDirection(DcMotor.Direction.FORWARD);
        DJ.setDirection(DcMotor.Direction.FORWARD);
        craneArm.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        leftDrive1.setPower(0);
        leftDrive2.setPower(0);
        rightDrive1.setPower(0);
        rightDrive2.setPower(0);
        DJ.setPower(0);
        craneArm.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDrive2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DJ.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        craneArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }
}

