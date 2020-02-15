package frc.robot;

public class AutoControl {

    double[][] two_ball_pickup_waypoints = new double[][]{
        {10, 24.5, 0},
        {22.5, 24.5, 0},
        {10, 19, 180}
    };

    double totalTime = 4; //max seconds we want to drive the path
    double timeStep = 0.02; //period of control loop on Rio, seconds
    double robotTrackWidth = 1.5; //distance between left and right wheels, feet
    double robotTrackLength = 1.5; //distance between front and rear wheels, feet

    MecanumPathPlanner path_two_ball_pickup;

    public AutoControl(){
        path_two_ball_pickup = new MecanumPathPlanner(two_ball_pickup_waypoints);
    }

    // public void followPath(MecanumPathPlanner path){
    //     for (path.front)
    // }

    public static void boringAutoSeek() {
        Robot.driveTrain.targetGoal();
    }
}