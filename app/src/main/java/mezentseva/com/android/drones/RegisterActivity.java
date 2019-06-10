package mezentseva.com.android.drones;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mezentseva.com.android.drones.Model.clientAggregate;
import mezentseva.com.android.drones.Remote.IMyAPI;
import mezentseva.com.android.drones.Remote.RetrofitClient;

public class RegisterActivity extends AppCompatActivity {

    IMyAPI iMyAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText edt_email, edt_password;
    Button btn_create;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        iMyAPI = RetrofitClient.getInstance().create(IMyAPI.class);

        edt_email=(EditText)findViewById(R.id.edt_email);
        edt_password=(EditText)findViewById(R.id.edt_password);
        btn_create=(Button)findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new SpotsDialog.Builder()
                        .setContext(RegisterActivity.this)
                        .build();
                dialog.show();

                clientAggregate user = new clientAggregate(edt_email.getText().toString()
                        ,edt_password.getText().toString());


                compositeDisposable.add(iMyAPI.registerUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                              //  if (s.equals("")){
                              //      finish();
                              //  }
                                Toast.makeText(RegisterActivity.this,s, Toast.LENGTH_SHORT).show();
                               // dialog.dismiss();

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dialog.dismiss();
                                Toast.makeText(RegisterActivity.this, throwable.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }));
            }
        });

    }


}
