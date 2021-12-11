package com.example.movibes;

public class API
{
    public static String GetEventByPostedDateDescending = "http://movibestesting.af-south-1.elasticbeanstalk.com/Events/GetEventByPostedDateDescending";
    public static String AddEvent = "http://movibestesting.af-south-1.elasticbeanstalk.com/Events/AddEvent?_eventStartDateTime=";
    public static String DeleteEvent = "http://movibestesting.af-south-1.elasticbeanstalk.com/Events/DeleteEvent?_eventId=";
    public static String GetUserProfileByID = "http://movibestesting.af-south-1.elasticbeanstalk.com/ProfileManagement/GetUserProfileByID?_userId=";
    public static String UpdateProfile = "http://movibestesting.af-south-1.elasticbeanstalk.com/ProfileManagement/UpdateProfile?_name=";
    public static String FollowManagement = "http://movibestesting.af-south-1.elasticbeanstalk.com/api/Follow/FollowManagement?_profileId=";
    public static String GetEventByPostedDateProfileDescending = "http://movibestesting.af-south-1.elasticbeanstalk.com/Events/GetEventByPostedDateProfileDescending?_profileId=";
    public static String UserLogin = "http://movibestesting.af-south-1.elasticbeanstalk.com/Authentication/UserLogin?_username=";
    public static String GetOTP = "http://movibestesting.af-south-1.elasticbeanstalk.com/Authentication/GetOTP?_username=";
    public static String CheckIfUserExist = "http://movibestesting.af-south-1.elasticbeanstalk.com/Authentication/CheckIfUserExist?_sUsername=";
    public static String VibesManagement = "http://movibestesting.af-south-1.elasticbeanstalk.com/Vibes/VibesManagement?_profileId=";
    public static String Register = "http://movibestesting.af-south-1.elasticbeanstalk.com/Authentication/Register?_username=";
    public static String ResetPassword = "http://movibestesting.af-south-1.elasticbeanstalk.com/Authentication/ResetPassword?_username=";
    public static String DidUserVibe = "http://movibestesting.af-south-1.elasticbeanstalk.com/Vibes/DidUserVibe?_eventId=";

}
