package notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAlvE-j0k:APA91bFEXmFe7VaiCBM9WCxT2piO7E9stCd7NB80RFH1hFROlfwMsltMBMeqLO42Pe6vrzyxQWJumWwdRaFAW2o7FrT6VR-TRjK2W1ud8bD37sLAHIgGJNBA061NHNJ3RxZoEQ3TNitj" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}