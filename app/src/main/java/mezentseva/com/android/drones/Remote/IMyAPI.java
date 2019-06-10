package mezentseva.com.android.drones.Remote;

import io.reactivex.Observable;
import mezentseva.com.android.drones.Model.clientAggregate;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IMyAPI {

    //http://around-dev1.azurewebsites.net
    @POST("api/Auth/register")
    Observable<String> registerUser(@Body clientAggregate user);

    @POST("api/Auth/authenticate")
    Observable<String> loginUser (@Body clientAggregate user);
}
